package jp.co.who.cop

class C8 extends LogicBase {
  override def run = {
    Common.head("start C8.")
    testFuncLiter
    testTail

  }

  def testFuncLiter = {
    val someNumber = List(-11, -10, -5, 0, 5, 10)
    someNumber.filter(_ > 0).foreach(println)
  }

  def testNameParam = {
    def speed(distance: Float, time: Float) = {
      distance / time
    }
    println(speed(time = 10, distance = 100))
  }

  def testTail = {
    def boom(x: Int): Int = {
      if (x == 1) x
      else if (x == 0) throw new Exception("boom!")
      else boom(x-1)
    }
    println(boom(6))
  }

}
