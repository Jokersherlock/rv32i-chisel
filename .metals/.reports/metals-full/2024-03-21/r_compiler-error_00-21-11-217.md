file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Jup.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.1\scala3-library_3-3.3.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.10\scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 266
uri: file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Jup.scala
text:
```scala
package rv32i

import chisel3._
import chisel3.util._

object Jup{
    val JUP_BEQ = 0.U(3.W)
    val JUP_BNE = 1.U(3.W)
    val JUP_BLT = 2.U(3.W)
    val JUP_BGE = 3.U(3.W)
    val JUP_BLTU = 4.U(3.W)
    val JUP_BGEU = 5.U(3.W)
    val JUP_JAL  = 6.U(@@)
    val JUP_XXX  = 7.U(3.W)
}

class JupIO(width:Int) extends Buddle{
    val A       = Input(UInt(width.W))
    val B       = Input(UInt(width.W))
    val jup_op  = Input(UInt(3.W))
    val out     = Out(Bool())
}

trait Jup extends Module{
    def width: Int
    val io: JupIO
}

class JupSimple(val width: Int) extends Jup{
    val io = IO(new JupIO(width))
    io.out := MuxLookup(io.jup_op,0)(
        Seq(
            JUP_BEQ  -> ( io.A        == io.B),
            JUP_BNE  -> ( io.A        != io.B),
            JUP_BLT  -> ( io.A.asSInt <  io.B.asSInt),
            JUP_BEG  -> ( io.A.asSInt >= io.B.asSInt),
            JUP_BLTU -> ( io.A        <  io.B),
            JUP_BGEU -> ( io.A        >= io.B)
        )
    )
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