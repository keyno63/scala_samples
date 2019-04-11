package jp.co.who.cop

import scala.collection.mutable

class C4(args: Array[String]) extends LogicBase {

  override def run: Unit = {
    println("=== start C4 ===")
    testClassField
    testSemicolum
    testSingleton
    testApplication(args)

  }

  class ChecksumAccumulator {
    // write class define here.
    private var sum = 0
    def add(b: Byte): Unit = sum += b
    def checksum(): Int = ~(sum & 0xFF) + 1
  }
  object ChecksumAccumulator {
    private val cache = mutable.Map.empty[String, Int]
    def calcurate(s: String): Int =
      if (cache.contains(s))
        cache(s)
      else {
        val acc = new ChecksumAccumulator
        for (c <- s)
          acc.add(c.toByte)
        val cs = acc.checksum()
        cache += (s -> cs)
        cs
      }
  }

  // 4.1
  def testClassField = {

    val acc = new ChecksumAccumulator
    val csa = new ChecksumAccumulator
    acc.add(3)
    println(acc.checksum)

  }

  // 4.2
  def testSemicolum(): Unit = {
    // one liner
    val s = "hello"; println(s)

    // if
    val x = 10
    if (x < 2)
      println("too small")
    else
      print("ok")

    // grouping
    val y = 5
    val z = (x
      + y)
    print(z)

    // grouping No.2
    val u = x +
      y +
      z
    println(u)
  }

  // 4.3
  def testSingleton(): Unit = {
    val s = "Every value is an object."
    ChecksumAccumulator.calcurate(s)
    ChecksumAccumulator.calcurate(s)
  }

  // 4.4
  def testApplication(args: Array[String]): Unit = {
    import ChecksumAccumulator.calcurate
    object Summer {
      def main(args: Array[String]) = {
        for (arg <- args)
          println(s"${arg}: ${calcurate(arg)}")
      }
    }

    val s = Summer.main(args)
  }

}
