package rv32i

import chisel3._
import chisel3.util._
import chisel3.experimental.BundleLiterals._

object Consts {
  val PC_START = 0x200
  val PC_EVEC = 0x100
}

class IF_ID extends Bundle{
    val inst = chiselTypeOf(Instructions.NOP)
    val pc = UInt(32.W)
    val flush_id = Bool()
}

class ID_EX extends Bundle{
    val pc   = UInt(32.W)
    val inst = UInt(32.W)
    val pc_sel = UInt(1.W)
    val A_sel = UInt(1.W)
    val B_sel = UInt(1.W)
 //   val imm_sel = UInt(3.W)
    val alu_op = UInt(4.W)
    val br_type = UInt(3.W)
    val st_type = UInt(2.W)
    val ld_type = UInt(3.W)
    val wb_sel = UInt(2.W)
    val wb_en = Bool()
    val mem_ren = Bool()
    val mem_wen = Bool()
    val addr1 = UInt(5.W)
    val addr2 = UInt(5.W)
    val rdata1 = UInt(32.W)
    val rdata2 = UInt(32.W)
    val waddr = UInt(5.W)
    val imm_o = UInt(32.W)
}

class EX_MEM extends Bundle{
    val pc = UInt(32.W)
    val wb_sel = UInt(2.W)
    val wb_en = Bool()
    val st_type = UInt(2.W)
    val ld_type = UInt(3.W)
    val mem_ren = Bool()
    val mem_wen = Bool() 
    val waddr = UInt(5.W)
    val imm_o = UInt(32.W)
    val alu_o = UInt(32.W)
    val rdata2 = UInt(32.W)
}

class MEM_WB extends Bundle{
    val pc = UInt(32.W)
    val wb_sel = UInt(2.W)
    val wb_en = Bool()
    val ld_type = UInt(2.W)
    val waddr = UInt(5.W)
    val imm_o = UInt(32.W)
    val alu_o = UInt(32.W)
    val mem_data = UInt(32.W)
}

class Datapath(val conf: CoreConfig)extends Module{
    val regFile = Module(new RegFile(conf.xlen))
    val alu = Module(conf.makeAlu(conf.xlen))
    val immGen = Module(conf.makeImmGen(conf.xlen))
    val brCond = Module(conf.makeBrCond(conf.xlen))
    val control = Module(new Control)
    val instructionmem = Module(new InstructionMemory(65535))
    val datamem = Module(new DataMemory(65535))


    import Control._

    val pc = RegInit(Consts.PC_START.U(conf.xlen.W) - 4.U(conf.xlen.W))

    val IF_ID_reg  = RegInit(0.U.asTypeOf(new IF_ID))
    val ID_EX_reg  = RegInit(0.U.asTypeOf(new ID_EX))
    val EX_MEM_reg = RegInit(0.U.asTypeOf(new EX_MEM))
    val MEM_WB     = RegInit(0.U.asTypeOf(new MEM_WB))
    
    val wb = Module(new Wb)

    IF_ID_reg.inst := instructionmem.io.inst
    IF_ID_reg.pc := pc
    instructionmem.io.addr := pc
    

    control.io.inst := IF_ID_reg.inst
    immGem.io.inst := IF_ID_reg.inst

    val addr1 = IF_ID_reg.inst(19,15)
    val addr2 = IF_ID_reg.inst(24,20)
    
    regFile.io.raddr1 := raddr1
    regFile.io.raddr2 := raddr2 

    control.io <> immGem.io

    ID_EX_reg.pc := IF_ID_reg.pc
    ID_EX_reg.inst := IF_ID_reg.inst
    ID_EX_reg.pc_sel := control.io.pc_sel
    ID_EX_reg.A_sel := control.io.A_sel
    ID_EX_reg.B_sel := control.io.B_sel
 //   ID_EX_reg.imm_sel := control.io.imm_sel
    ID_EX_reg.alu_op := control.io.alu_op
    ID_EX_reg.br_type := control.io.br_type
    ID_EX_reg.st_type := control.io.st_type
    ID_EX_reg.ld_type := control.io.ld_type
    ID_EX_reg.wb_sel := control.io.wb_sel
    ID_EX_reg.wb_en := control.io.wb_en
    ID_EX_reg.mem_ren := control.io.mem_ren
    ID_EX_reg.mem_wen := control.io.mem_wen
    ID_EX_reg.addr1 := addr1
    ID_EX_reg.addr2 := addr2
    ID_EX_reg.rdata1 := regFile.io.rdata1
    ID_EX_reg.rdata2 := regFile.io.rdata2
    ID_EX_reg.waddr := IF_ID_reg.inst(11,7)

    val forwardA = Mux(EX_MEM_reg.waddr =/= 0.U && EX_MEM_reg.waddr === ID_EX_reg.addr1 && EX_MEM_reg.wb_en, EX_MEM_reg.alu_o,
                Mux(MEM_WB_reg.waddr =/= 0.U && MEM_WB_reg.waddr === ID_EX_reg.addr1 && MEM_WB_reg.wb_en, MEM_WB_reg.alu_o, ID_EX_reg.rdata1))

    val forwardB = Mux(EX_MEM_reg.waddr =/= 0.U && EX_MEM_reg.waddr === ID_EX_reg.addr2 && EX_MEM_reg.wb_en, EX_MEM_reg.alu_o,
                    Mux(MEM_WB_reg.waddr =/= 0.U && MEM_WB_reg.waddr === ID_EX_reg.addr2 && MEM_WB_reg.wb_en, MEM_WB_reg.alu_o, ID_EX_reg.rdata2))


    alu.io.A = MuxLookup(ID_EX_reg.A_sel,forwardA)(
        Seq(
            A_XXX -> forwardA,
            A_RS1 -> forwardA,
            A_PC  -> ID_EX_reg.pc
        )
    )

    alu.io.A = MuxLookup(ID_EX_reg.B_sel,forwardB)(
        Seq(
            B_XXX -> forwardB,
            B_IMM -> ID_EX_reg.imm_o,
            B_RS2  -> forwardB
        )
    )

    val stall = (EX_MEM_reg.waddr === ID_EX_reg.addr1 || EX_MEM_reg.waddr === ID_EX_reg.addr2) && EX_MEM_reg.mem_ren

    control.io.stall := stall
    
    brCond.io.RS1 := forwardA
    brCond.io.RS2 := forwardB
    brCond.io.br_type := ID_EX_reg.br_type

    control.io.flush := brCond.flush
    IF_ID_reg.flush_id := brCond.flush
    control.io.flush_id := IF_ID_reg.flush_id
    val pc_4 = pc + 4
    val nxt_pc = MuxLookup(ID_EX_reg.pc_sel,pc + 4)(
        Seq(
            PC_4 -> pc_4,
            PC_ALU -> Mux(brCond.io.out,alu.io.out,pc_4)
        )
    )
    pc := Mux(stall,pc,nxt_pc)
    
    EX_MEM_reg.pc := ID_EX_reg.pc
    EX_MEM_reg.wb_sel := ID_EX_reg.wb_sel
    EX_MEM_reg.wb_en := ID_EX_reg.wb_en 
    EX_MEM_reg.st_type := ID_EX_reg.st_type
    EX_MEM_reg.ld_type := ID_EX_reg.ld_type
    EX_MEM_reg.mem_ren := ID_EX_reg.mem_ren
    EX_MEM_reg.mem_wen := ID_EX_reg.mem_wen
    EX_MEM_reg.waddr := ID_EX_reg.waddr
    EX_MEM_reg.imm_o := ID_EX_reg.imm_o
    EX_MEM_reg.alu_o := alu.io.out
    EX_MEM_reg.rdata2 := ID_EX_reg.rdata2
    
    val dataToMem = Mux(EX_MEM_reg.raddr2 =/= 0.U && EX_MEM_reg.raddr2 === MEM_WB_reg.waddr,wb.io.wb_data,EX_MEM_reg.rdata2)

    datamem.io.dataIn := EX_MEM_reg.rdata2
    datamem.io.wen := MuxLookup(EX_MEM_reg.st_type,0)(
        Seq(
            ST_XXX -> "b0000".U(4.W),
            ST_SB  -> "b0001".U(4.W),
            ST_SH  -> "b0011".U(4.W),
            ST_SW  -> "b1111".U(4.W)
        )
    )
    datamem.io.addr = EX_MEM_reg.alu_o
    val mem_data = datamem.io.dataOut

    MEM_WB_reg.pc = EX_MEM_reg.pc
    MEM_WB_reg.wb_sel = EX_MEM_reg.wb_sel
    MEM_WB_reg.wb_en = EX_MEM_reg.wb_en 
    MEM_WB_reg.ld_type = EX_MEM_reg.ld_type
    MEM_WB_reg.waddr = EX_MEM_reg.waddr
    MEM_WB_reg.imm_o = EX_MEM_reg.imm_o
    MEM_WB_reg.alu_o = EX_MEM_reg.alu_o 
    MEM_WB_reg.mem_data = mem_data
    
    Wb.io.wb_sel := MEM_WB_reg.wb_sel
    Wb.io.ld_type := MEM_WB_reg.ld_type
    Wb.io.imm_o := MEM_WB_reg.imm_o
    Wb.io.alu_o := MEM_WB_reg.alu_o
    Wb.io.mem_data := MEM_WB_reg.mem_data
    Wb.io.pc := MEM_WB_reg.pc
    
    regFile.io.wem := MEM_WB_reg.wb_en
    regFile.io.waddr := MEM_WB_reg.waddr
    regFile.io.wdata := Wb.io.wb_data
}