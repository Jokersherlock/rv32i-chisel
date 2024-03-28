package rv32i

import chisel3._
import chisel3.util._

class InstructionMemory(depth: Int) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(depth * 4).W)) // 地址以字节为单位，确保足够的位宽
    val inst = Output(UInt(32.W))
  })

  // 用于存储指令的内存，这里假设每个存储位置是32位宽
  val mem = Mem(depth, UInt(32.W))

  // 简单的读取操作，注意地址需要按4字节对齐，所以右移2位
  io.inst := mem.read(io.addr >> 2)
}

