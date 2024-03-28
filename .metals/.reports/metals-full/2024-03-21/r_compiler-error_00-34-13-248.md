file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Control.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.1\scala3-library_3-3.3.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.10\scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 768
uri: file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Control.scala
text:
```scala
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

    val PC_4  = 0.U(2.W)
    val PC_ALU= 1.U(2.W)
    val PC_0  = 2.U(2.W)

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
    val WB_IMM = 3.U(@@)

    
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