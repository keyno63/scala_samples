package jp.co.who.cop

class C2(args: Array[String]) extends LogicBase {
  // Capture 2 is using repl for introduction Scala.
  // So nothing to do.

  override def run: Unit = {
    println("=== start C2 ===")
    testVariable
    testFunction
    testLoop
  }
  // 2.2
  def testVariable: Unit = {
    val msg = "Hello, world!"
    println(msg)
    val msg2: java.lang.String = "Hello again, world!"
    println(msg2)
    val msg3: String = "Hello yet again, world!"
    println(msg3)

    var greeting = "Hello, world!"
    println(greeting)
    greeting = "Goodbye cruel world!"
    println(greeting)
  }

  // 2.3
  def testFunction: Unit = {
    def max(x: Int, y: Int) = if (x > y) x else y
    val value = max(3, 5)
    println(value)

    def greet() = println("Hello, world!")
  }

  // 2.4
  // using scala script.

  // 2.5
  def testLoop: Unit = {
    var i = 0
    println(s"testLoop. while loop.")
    while (i < args.length) {
      println(args(i))
      i += 1
    }
    println(s"testLoop. while loop2.")

    i = 0
    while (i < args.length) {
      if (i != 0)
        print(" ")
      print(args(i))
      i += 1
    }
  }

  // 2.6
  def testFor: Unit = {
    println("foreach print")
    args.foreach(arg => println(arg))

    println("for print")
    for (arg <- args)
      println(arg)
  }
}
