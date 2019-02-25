package jp.co.who.cop

class C20 extends LogicBase {
  // 抽象メンバー

  override def run: Unit = {
    Common.head("start C20.")
    testAbstractor
    testAbstVal
    testLazy
    testAbstType
    testEnum
  }

  // 20.1
  def testAbstractor = {
    trait Abstract {
      type T

      def transform(x: T): T

      val initial: T
      var current: T
    }

    class Concrete extends Abstract {
      type T = String

      def transform(x: String): String = x + x

      val initial = "hi"
      var current = initial
    }

    val c = new Concrete
    print(c.transform("hoge"))
  }

  // 20.5 抽象val
  def testAbstVal = {
    trait RationalTrait {
      val numerArg: Int
      val denomArg: Int
      require(denomArg != 0)
      private val g = gcd(numerArg, denomArg)
      val numer = numerArg / g
      val denom = denomArg / g
      private def gcd(i: Int, i1: Int): Int =
        if (i1 == 0) i else gcd(i1, i % i1)

      override def toString: String = numer + "/" + denom
    }
    /*
    println(new RationalTrait(1,2))
    val x = 2
    println(new RationalTrait(1*x,2*x))
    */

  }

  // 20.5 lazy
  def testLazy = {
    Common.head("testLazy")
    object Demo {
      lazy val x = { println("initializing x"); "done"}
    }
    println(Demo.x)

    trait LazyRationalTrait {
      val numerArg: Int
      val denomArg: Int
      lazy val numer = numerArg / g
      lazy val denom = denomArg / g
      override def toString = numer + "/" + denom
      private lazy val g = {
        require(denomArg != 0)
        gcd(numerArg, denomArg)
      }
      private def gcd(i: Int, i1: Int): Int =
        if (i1 == 0) i else gcd(i1, i % i1)
    }
    val x = 2
    // lazy で事前初期化不要
    val c = new LazyRationalTrait {
      val numerArg: Int = 1 * x
      val denomArg: Int = 2 * x
    }
    println(c)
  }

  // 20.6 抽象型宣言
  def testAbstType: Unit = {
    class Food
    abstract class Animal {
      type SuitableFood <: Food
      def eat(food: SuitableFood)
    }

    class Grass extends Food
    class Cow extends Animal {
      override type SuitableFood = Grass
      override def eat(food: Grass) = {println("eat:" + food.getClass)}
    }

    val c = new Cow
    c.eat(new Grass)
  }

  // 20.9 enum
  def testEnum = {
    object Direction extends Enumeration {
      val North = Value("North")
      val East = Value("East")
      val South = Value("South")
      val West = Value("West")
    }
    for(d <- Direction.values) print (d + " ")
    println()
  }

  // 20.10
  def testAll20 = {
    abstract class CurrencyZone {
      type Currency <: AbstractCurrency
      def make(x: Long): Currency
      abstract class AbstractCurrency {
        val amount: Long
        def designation: String

        override def toString: String = amount + " " + designation
        def + (that: Currency): Currency =
          make(this.amount + that.amount)
        def * (x: Double): Currency =
          make((this.amount * x).toLong)
      }
    }

    object US extends CurrencyZone {
      abstract class Dollar extends AbstractCurrency {
        def designation = "USD"
      }

      override type Currency = Dollar
      def make(cents: Long) = new Dollar {
        override val amount: Long = cents
      }
      val Cent = make(1)
      val Dollar = make(100)
      val CurrencyUnit = Dollar
    }
  }


}
