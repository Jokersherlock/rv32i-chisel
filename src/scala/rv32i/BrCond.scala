package rv32i

import chisel3._

object BrCond{
    val BR_BEQ = 0.U(3.W)
    val BR_BNE = 1.U(3.W)
    val BR_BLT = 2.U(3.W)
    val BR_BGE = 3.U(3.W)
    val BR_BLTU = 4.U(3.W)
    val BR_BGEU = 5.U(3.W)
    val BR_JAL  = 6.U(3.W)
    val BR_XXX  = 7.U(3.W)
}

class BrCondIO(xlen:Int) extends Buddle{
    val RS1       = Input(UInt(xlen.W))
    val RS2       = Input(UInt(xlen.W))
    val br_type  = Input(UInt(3.W))
    val out     = Out(Bool())
    val flush   = Out(Bool())
}

trait BrCond extends Module{
    def xlen: Int
    val io: BrcondIO
}

class BrCondSimple(val xlen: Int) extends BrCond{
    val io = IO(new BrCondIO(Int))
    io.out := MuxLookup(io.br_type,0)(
        Seq(
            BR_BEQ  -> ( io.RS1        == io.RS2),
            BR_BNE  -> ( io.RS1        != io.RS2),
            BR_BLT  -> ( io.RS1.asSInt <  io.RS2.asSInt),
            BR_BEG  -> ( io.RS1.asSInt >= io.RS2.asSInt),
            BR_BLTU -> ( io.RS1        <  io.RS2),
            BR_BGEU -> ( io.RS1        >= io.RS2),
            BR_JAL  -> 1,
            BR_XXX  -> 0
        )
    )
    io.flush := io.out     
}