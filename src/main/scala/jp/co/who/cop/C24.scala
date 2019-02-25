package jp.co.who.cop

class C24 extends LogicBase {
  override def run = {
    Common.head("start C24.")
    test1
  }

  def test1 = {
    var v = List(List(1, 2, 3), List(5, 6, 7))
    v foreach println
    val s = List(List(7, 7, 7))
    //val c = v + s
    //println(c.toString)
  }

}
