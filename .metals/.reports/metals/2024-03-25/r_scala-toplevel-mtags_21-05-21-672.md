error id: file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Datapath.scala:[1125..1125) in Input.VirtualFile("file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Datapath.scala", "package rv32i

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
}

class ID_EX extends Bundle{
    val inst = UInt(32.W)
    val pc_sel = UInt(1.W)
    val A_sel = UInt(1.W)
    val B_sel = UInt(1.W)
    val imm_sel = UInt(3.W)
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
    val rdata1 = UInt(5.W)
    val rdata2 = UInt(5.W)
    val waddr = UInt(5.W)
    val imm_o = UInt(32.W)
}

class EX_MEM extends Bundle{
    val wb_sel = UInt(2.W)
    val wb_en = Bool()
    val st_type = UInt(2.W)
    val ld_type = UInt(3.W)
    val mem_ren = Bool()
    val mem_wen = Bool() 
    val waddr = UInt(32.W)
    val imm_o = UInt(32.W)
    val alu_o = UInt(32.W)
}

class ")
file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Datapath.scala
file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Datapath.scala:51: error: expected identifier; obtained eof
class 
      ^
#### Short summary: 

expected identifier; obtained eof