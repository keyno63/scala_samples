package jp.co.who.cop

class C5 extends LogicBase {

  override def run(): Unit = {
    println("=== start C5 ===")
  }

  // 5.2
  def testLiteral: Unit = {
    // int literal
    val hex = 0x5
    val hex2 = 0x00FF
    val magic = 0xcafebabe
    println(s"hex=[$hex], hex2=[$hex2], magic=[$magic].")
  }

  // TODO: impl other functions.

}
