file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/ImmGen.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.1\scala3-library_3-3.3.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.10\scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 96
uri: file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/ImmGen.scala
text:
```scala
package rv32i

import chisel3._
import chisel3.util._

object ImmGen{
    val IMM_I = 0.U(@@)
}

class ImmGenIO(xlen: Int) extends Buddle{
    val inst = Input(UInt(xlen.W))
    val sel  = Input(UInt(3.W))
    val out  = Output(UInt(xlen.W))
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
}


```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:131)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.countParams(Signatures.scala:501)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:186)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:94)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:63)
	scala.meta.internal.pc.MetalsSignatures$.signatures(MetalsSignatures.scala:17)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:51)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:398)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: 0