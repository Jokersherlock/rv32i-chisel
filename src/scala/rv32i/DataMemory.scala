package rv32i

import chisel3._
import chisel3.util._

class DataMemory(depth: Int) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(depth * 4).W)) // 乘以4因为每个地址指向32位数据
    val dataIn = Input(UInt(32.W))
    val dataOut = Output(UInt(32.W))
    val wen = Input(Bool())                      // 写使能信号
    val byteEnable = Input(UInt(4.W))            // 字节使能信号，每个位代表一个字节
  })

  val mem = SyncReadMem(depth, Vec(4, UInt(8.W))) // 以字节为单位存储，总宽度32位

  // 写操作
  when(io.wen) {
    // 根据字节使能逐个检查并更新字节
    (0 until 4).foreach { i =>
      when(io.byteEnable(i)) {
        mem.write(io.addr, VecInit(Seq.tabulate(4)(j => 
          Mux(io.byteEnable(j), io.dataIn(8*j+7, 8*j), mem.read(io.addr)(j))
        )))
      }
    }
  }

  // 读操作
  val readData = mem.read(io.addr)
  io.dataOut := readData.asUInt // 将读取的字节向量转换回UInt
  
  // 注意: 这里简化了一些细节，实际应用时可能需要根据具体需求调整
}
