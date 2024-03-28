package rv32i

import chisel3._
import chisel3.util._

object ImmGen{
    val IMM_I = 0.U(3.W)
    val IMM_S = 1.U(3.W)
    val IMM_B = 2.U(3.W)
    val IMM_U = 3.U(3.W)
    val IMM_J = 4.U(3.W)
    val IMM_X = 7.U(3.W)
}

class ImmGenIO(xlen: Int) extends Buddle{
    val inst = Input(UInt(xlen.W))
    val sel  = Input(UInt(3.W))
    val imm_o  = Output(UInt(xlen.W))
}

trait ImmGen extends Module{
    def xlen: Int
    val io: ImmGenIO 
}

class ImmGenWire(val xlen: Int) extends ImmGen{
    val io = IO(new ImmGenIO(xlen))
    val Iimm = io.inst(31,20).asSInt
    val Simm = Cat(io.inst(31,25),io.inst(11,7)).asSInt
    val Bimm = Cat(io.inst(31), io.inst(7), io.inst(30, 25), io.inst(11, 8), 0.U(1.W)).asSInt
    val Uimm = Cat(io.inst(31,12),0.U(12.W)).asSInt
    val Jimm = Cat(io.inst(31), io.inst(19, 12), io.inst(20), io.inst(30, 25), io.inst(24, 21), 0.U(1.W)).asSInt
    io.imm_o := MuxLookup(io.sel,Iimm & (-2).S)(
        Seq(
            IMM_I -> Iimm,
            IMM_S -> Simm,
            IMM_B -> Bimm,
            IMM_U -> Uimm,
            IMM_J -> Jimm
        )
    ).asUInt
}

