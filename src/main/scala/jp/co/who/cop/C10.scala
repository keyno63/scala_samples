package jp.co.who.cop

class C10 extends LogicBase {

  override def run(): Unit = {
    println("=== start C10 ===")
    testInherit
    testSub
    testParameter
    testExtendClass
  }

  abstract class Element {
    def contents: Array[String]
    def height: Int = contents.length
    def width: Int = if (height == 0) 0 else contents(0).length
    def demo() = {
      println("Element's implementation invoked")
    }
  }

  class ArrayElement(conts: Array[String]) extends Element {
    override val contents: Array[String] = conts
  }

  // 10.4
  def testInherit: Unit = {
    val ae = new ArrayElement(Array("hello", "world"))
    println(ae.width)
  }

  def testSub: Unit = {
    val e: Element = new ArrayElement(Array("hello"))
  }

  // 10.6
  class NewArrayElement(
                         val contents: Array[String]
                       ) extends Element

  def testParameter: Unit = {
    val nae = new NewArrayElement(Array("test"))
    println(s"NewArrayElement contens. v=[${nae.contents.head}].")
  }

  class Cat {
    val dangerous = false
  }
  class Tiger(
             override val dangerous: Boolean,
             private var age: Int
             ) extends Cat {
    override def toString: String = {
      s"Tiger. dangerous=[$dangerous], age=[$age]."
    }
  }

  def testExtendClass: Unit = {
    val t = new Tiger(true, 10)
    println(t)
  }

  // 10.7
  // call super class constructor
  class LineElement(s: String) extends ArrayElement(Array(s)) {
    override def width = s.length
    override def height = 1
  }

  // 10.10
  class TruthArrayElement extends Element {
    final override def demo() = {
      println("ArrayElement's implementation invoked")
    }
    override def contents = Array("TruthArrayElement")
  }

  // 10.11
  class TruthLineElement(s: String) extends Element {
    val contents = Array(s)
    override def height: Int = 1
    override def width: Int = s.length
    override def demo(): Unit = super.demo()
  }

  // 10.12
}
