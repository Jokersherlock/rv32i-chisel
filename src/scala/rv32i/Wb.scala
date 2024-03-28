package rv32i

import chisel3._

class WbIO extends Bundle{
    val wb_sel = Input(UInt(2.W))
    val ld_type = Input(UInt(2.W))
    val imm_o = Input(UInt(32.W))
    val alu_o = Input(UInt(32.W))
    val mem_data = Input(UInt(32.W))
    val pc = Input(UInt(32.W))
    val wb_data = Input(UInt(32.W))
}

class Wb extends Module{
    import Control._
    val io = IO(new WbIO)
    io.wb_data := MuxLookup(io.wb_sel,0)(
        Seq(
            WB_ALU -> io.alu_o,
            WB_PC4 -> io.pc + 4,
            WB_IMM -> io.imm_o,
            WB_MEM -> MuxLookup(io.ld_type,0)(
                Seq(
                    LD_XXX -> 0,
                    LD_LB  -> io.mem_data(7,0).asSInt,
                    LD_LH  -> io.mem_data(15,0).asSInt,
                    LD_LW  -> io.mem_data(31,0).asSInt,
                    LD_LBU -> io.mem_data(7,0).asUInt,
                    LD_LHU -> io.mem_data(15,0)asUInt
                )
            )
        )
    )
}
