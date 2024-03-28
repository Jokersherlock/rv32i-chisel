package rv32i

import chisel3._ 
import chisel3.util._

object Control{
    val A_XXX = 0.U(1.W)
    val A_PC  = 0.U(1.W)
    val A_RS1 = 1.U(1.W)
    val B_XXX = 0.U(1.W)
    val B_IMM = 0.U(1.W)
    val B_RS2 = 1.U(1.W)

    import BrCond._
    import Alu._
    import ImmGen._

    val PC_4  = 0.U(1.W)
    val PC_ALU= 1.U(1.W)

    val ST_XX = 0.U(2.W)
    val ST_SB = 1.U(2.W)
    val ST_SH = 2.U(2.W)
    val ST_SW = 3.U(2.W)

    
    val LD_XXX = 0.U(3.W)
    val LD_LW = 1.U(3.W)
    val LD_LH = 2.U(3.W)
    val LD_LB = 3.U(3.W)
    val LD_LHU = 4.U(3.W)
    val LD_LBU = 5.U(3.W)

    // wb_sel
    val WB_ALU = 0.U(2.W)
    val WB_MEM = 1.U(2.W)
    val WB_PC4 = 2.U(2.W)
    val WB_IMM = 3.U(2.W)

    import Instructions._

    val default =
  //                                                                                          wb_en     mem_wen illegal?
  //                 pc_sel  A_sel   B_sel  imm_sel   alu_op   br_type  st_type ld_type wb_sel  | mem_ren |       |
  //                   |       |       |     |          |          |       |       |       |    |  |      |       |
                 List(PC_4  , A_XXX,  B_XXX, IMM_X, ALU_XXX   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      Y)
    val map = Array(
        ADD   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_ADD   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SUB   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SUB   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        XOR   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_XOR   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)    
        OR    -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_OR    , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        AND   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_AND   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLL   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SLL   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SRL   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SRL   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SRA   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SRA   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLT   -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SLT   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLTU  -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_SLTU  , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        
        ADDI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        XORI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_XOR   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        ORI   -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_OR    , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        ANDI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_AND   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLLI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_SLL   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SRLI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_SRL   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SRAI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_SRA   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLTI  -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_SLT   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        SLTIU -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_SLTU  , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
        
        LB    -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_LB , WB_MEM, Y, Y,     N,      N)
        LH    -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_LH , WB_MEM, Y, Y,     N,      N)
        LW    -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_LW , WB_MEM, Y, Y,     N,      N)
        LBU   -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_LBU, WB_MEM, Y, Y,     N,      N)
        LHU   -> List(PC_4  , A_RS1,  B_IMM, IMM_I, ALU_ADD   , BR_XXX, ST_XXX, LD_LHU, WB_MEM, Y, Y,     N,      N)
        
        SB    -> List(PC_4  , A_RS1,  B_IMM, IMM_S, ALU_ADD   , BR_XXX, ST_SB , LD_XXX, WB_ALU, N, N,     Y,      N)
        SH    -> List(PC_4  , A_RS1,  B_IMM, IMM_S, ALU_ADD   , BR_XXX, ST_SH , LD_XXX, WB_ALU, N, N,     Y,      N)
        SW    -> List(PC_4  , A_RS1,  B_IMM, IMM_S, ALU_ADD   , BR_XXX, ST_SW , LD_XXX, WB_ALU, N, N,     Y,      N)
        
        BEQ   -> List(PC_ALU, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BEQ, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        BNE   -> List(PC_ALU, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BNE, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        BLT   -> List(PC_ALU, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BLT, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        BGE   -> List(PC_ALU, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BGE, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        BLTU  -> List(PC_ALI, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BLTU,ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        BGEU  -> List(PC_ALU, A_PC ,  B_IMM, IMM_B, ALU_ADD   , BR_BGEU,ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
        
        JAL   -> List(PC_ALU, A_PC,   B_IMM, IMM_J, ALU_ADD   , BR_JAL, ST_XXX, LD_XXX, WB_PC4, Y, N,     N,      N)
        JALR  -> List(PC_ALU, A_RS1,  B_IMM, IMM_J, ALU_ADD   , BR_JAL, ST_XXX, LD_XXX, WB_PC4, Y, N,     N,      N)
        
        LUI   -> List(PC_4  , A_XXX,  B_XXX, IMM_U, ALU_XXX   , BR_XXX, ST_XXX, LD_XXX, WB_IMM, N, N,     N,      N)
        AUIPC -> List(PC_4  , A_PC ,  B_IMM, IMM_U, ALU_ADD   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, Y, N,     N,      N)
      //  ECALL -> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_ADD   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)
      //  EBREAK-> List(PC_4  , A_RS1,  B_RS2, IMM_X, ALU_ADD   , BR_XXX, ST_XXX, LD_XXX, WB_ALU, N, N,     N,      N)

    
    )    
}

class ControlSignals extends Bundle{
    val flush = Input(Bool())
    val flush_id = Input(Bool())
    val stall = Input(Bool())
    val inst = Input(UInt(32.W))
    val pc_sel = Output(UInt(1.W))
    val A_sel = Output(UInt(1.W))
    val B_sel = Output(UInt(1.W))
    val imm_sel = Output(UInt(3.W))
    val alu_op = Output(UInt(4.W))
    val br_type = Output(UInt(3.W))
    val st_type = Output(UInt(2.W))
    val ld_type = Output(UInt(3.W))
    val wb_sel = Output(UInt(2.W))
    val wb_en = Output(Bool())
    val mem_ren = Output(Bool())
    val mem_wen = Output(Bool())
    val illegal = Output(Bool())
    val flush   = Input(Bool())
    val flush_id= Input(Bool())
}

class Control extends Module{
    val io = IO(new ControlSignals)
    val ctrlSignals = ListLookup(io.inst,Control.default,Control.map)
    val clr = io.flush | io.flush_id | io.stall 
    io.pc_sel := Mux(clr,0,ctrlSignals(0))
    io.A_sel  := Mux(clr,0,ctrlSignals(1))
    io.B_sel  := Mux(clr,0,ctrlSignals(2))
    io.imm_sel:= Mux(clr,0,ctrlSignals(3))
    io.alu_op := Mux(clr,0,ctrlSignals(4))
    io.br_type:= Mux(clr,0,ctrlSignals(5))
    io.st_type:= Mux(clr,0,ctrlSignals(6))
    io.ld_type:= Mux(clr,0,ctrlSignals(7))
    io.wb_sel := Mux(clr,0,ctrlSignals(8))
    io.wb_en  := Mux(clr,0,ctrlSignals(9))
    io.mem_ren:= Mux(clr,0,ctrlSignals(10))
    io.mem_wen:= Mux(clr,0,ctrlSignals(11))
    io.illegal:= Mux(clr,0,ctrlSignals(12))

}