package jp.co.who.cop

class C16 extends LogicBase {

  override def run = {
    Common.head("start C16.")
    testList
  }

  def testList = {
    val c = List() ::: List(1, 2, 3)
    println(c.toString)
  }

}
