package jp.co.who.cop

class C6 extends LogicBase {
  override def run = {
    Common.head("start C6.")
    testMethod

  }

  private class Rational(n: Int, d: Int) {
    require(d != 0)
    private val g = gcd(n.abs, d.abs)
    val numer = n / g
    val denom = d / g
    def this(n: Int) = this(n,1)
    def + (that: Rational): Rational =
      new Rational(
        numer * that.denom + that.numer * denom,
        denom * that.denom
      )
    def + (i: Int): Rational =
      new Rational(numer + i * denom, denom)
    def - (that: Rational): Rational =
      new Rational(
        numer * that.denom - that.numer * denom,
        denom * that.denom
      )
    def - (i: Int): Rational =
      new Rational(numer - i * denom, denom)
    def * (that: Rational): Rational =
      new Rational(numer * that.numer, denom * that.denom)
    def * (i: Int): Rational =
      new Rational(numer * i, denom)
    def / (that: Rational): Rational =
      new Rational(numer * that.denom, denom * that.numer)
    def / (i: Int): Rational =
      new Rational(numer, denom * i)

    override def toString: String = numer + "/" + denom
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)
  }

  private def testMethod = {
    println(new Rational(5,4) + new Rational(2,3))
  }

}
