file:///D:/study/ic_study/project/riscv-chisel/src/scala/rv32i/Control.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.1\scala3-library_3-3.3.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.10\scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 529
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

    val JUP_BLTU = 4.U(3.W)
    val JUP_BGEU = 5.U(3.W)
    val JUP_XXX  = 7.U(3.W)

    val IMM_I = 0.U(3.W)
    val IMM_S = 1.U(3.W)
    val IMM_B = 2.U(3.W)
    val IMM_U = 3.U(3.W)
    val IMM_J = 4.U(3.W)
    val IMM_X = 7.U(3.W)

    val PC_4  = 0.U(2.W)
    val PC_ALU= 1.U(@@)
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