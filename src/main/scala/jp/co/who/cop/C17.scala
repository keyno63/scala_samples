package jp.co.who.cop

import scala.collection.mutable
import scala.collection.immutable

class C17 extends LogicBase {
  override def run: Unit = {
    Common.head("start C17.")
    testSet
    testTapple
    testTreeMap
    testTreeSet
  }

  def testSet : Unit = {
    val mutaSet = mutable.Set(1,2,3)
    println(mutaSet)
  }

  def testTreeSet : Unit = {
    val ts = mutable.TreeSet(9, 3, 1, 8, 0, 2, 7, 4, 6, 5)
    println(ts)
  }

  def testTreeMap : Unit = {
    var tm = immutable.TreeMap(3 -> "x", 1 -> "x", 4 -> "x")
    println(tm)

    tm += (2 -> "x")
    println(tm)
  }

  // 17.5
  def testTapple: Unit = {
    // helper function.
    def longestWord(words: Array[String]) = {
      var word = ""
      var idx = 0
      for (i <- 0 until words.length)
        if (words(i).length > word.length) {
          word = words(i)
          idx = i
        }
      (word, idx)
    }

    val longest = longestWord("The quick brown fox".split(" "))
    println(longest)
    longest._1
    longest._2

  }
}
