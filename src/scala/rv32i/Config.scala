
package rv32i

case class Config(core: CoreConfig)

object RVConfig {
    def apply(): Config = {
        val xlen = 32
        Config(
            core = CoreConfig(
                xlen = xlen,
                makeAlu = new AluArea(_),
                makeBrCond = new BrcondArea(_),
                makeImmGen = new ImmGenWire(_)
            )
        )
    }
}