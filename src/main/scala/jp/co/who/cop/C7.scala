package jp.co.who.cop

class C7(val x:String = "none") extends LogicBase {
  override def run = {
    Common.head("start C7." + x)
    testIf(Array(x))
    testWhile
    testFor
    testTry
    testMatch
  }
  // if, while, for, try, match
  def testIf(args: Array[String]) = {
    val filename =
      if(!args.isEmpty) args(0)
      else "default.txt"
    println(filename)
  }

  def testWhile = {
    // 手続き型.
    def gcdLoop(x: Long, y: Long): Long = {
      var a = x
      var b = y
      while (a != 0) {
        val temp = a
        a = b % a
        b = temp
      }
      b
    }
    println(gcdLoop(100, 200))

    // 再帰.
    def gcd2(x: Long, y: Long): Long = {
      if (y == 0) x else gcd2(y, x%y)
    }
    println(gcd2(100, 200))

  }

  def testFor = {
    val filePath = "./src/main/scala/jp/co/who/cop"
    //val filePath = "./src/main/scala/jp/"
    val filesHere = (new java.io.File(filePath)).listFiles
    for (file <- filesHere)
      println(file)

    // filter
    println("print filter")
    for (
      file <- filesHere
      if file.isFile
      if file.getName.endsWith(".scala")
    ) println(file)

    // yield
    println("print yield")
    def scalaFiles =
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
      } yield file
    scalaFiles.foreach(println)
  }

  def testTry = {

  }
  def testMatch = {

  }

}
