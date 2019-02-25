package jp.co.who.cop

class C3 extends LogicBase {
  override def run = {
    Common.head("start C3.")
    val sample = "Sample: "
    testList(sample)
    testList2
  }

  def testList(sample: String) = {
    println(sample + 1 :: 2 :: 3 :: List())

  }

  def testList2 = {
    val th = List()
    val thrill = "Will" :: "fill" :: "until" :: th
    val x = thrill.filter(a => a.length == 4)
    println(x)
    thrill.foreach(print)
    println("")
  }

  def testTupple = {
    val pair = (99, "Luftballons")
    println(pair.toString())
  }

}
