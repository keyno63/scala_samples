package jp.co.who.cop

class C23 extends LogicBase {

  override def run: Unit = {
    Common.head("start C23.")
    testForGen
    testFilter
  }

  def testForGen = {
    def queens(n: Int): List[List[(Int, Int)]] = {
      def placeQueens(k: Int): List[List[(Int, Int)]] =
        if (k == 0)
          List(List())
        else
          for {
            queens <- placeQueens(k - 1)
            column <- 1 to n
            queen = (k, column)
            if isSafe(queen, queens)
          } yield queen :: queens
      placeQueens(n)
    }
    def isSafe(queen: (Int, Int), queens: List[(Int, Int)]) =
      queens forall (q => !inCheck(queen, q))
    def inCheck(q1: (Int, Int), q2: (Int, Int)) =
      q1._1 == q2._1 ||
      q1._2 == q2._2 ||
        (q1._1 - q2._1).abs == (q1._2 - q2._2).abs

    val q = queens(7)
    println(q)
  }

  def testFilter = {
    case class Person(name: String, isMale: Boolean, children: Person*)
    val l = Person("Lara", false)
    val b = Person("Bob", true)
    val j = Person("Julie", false, l, b)
    val persons = List(l, b, j)
    val ret = for (p <- persons; if !p.isMale; c <- p.children)
      yield (p.name, c.name)
    println(ret)
  }

}
