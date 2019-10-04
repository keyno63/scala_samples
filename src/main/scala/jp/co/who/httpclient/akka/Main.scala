package jp.co.who.httpclient.akka

object Main {

  // tmp values.
  val proto = "http"
  val host = "localhost"
  val port = 8080
  val entry = "hoge"

  val sender: HttpSender = new HttpSenderImpl

  def main(args: Array[String]): Unit = {
    while (true) {
      printf("scalahttp> ")
      val stdin = scala.io.StdIn.readLine().split(" ")
      if (stdin.head != "") {
        //sendRequest(stdin.head)
        println(stdin)
      } else {
        println(stdin)
        val str = "%s://%s:%d/%s".format(proto, host, port, entry)
        sendRequest(str)
      }
    }
  }

  // request 送信、reaponse表示
  def sendRequest(url: String): Unit = {
    val req = sender.request(url)
    //req.map(decodeResponse(_)).map(println)
    sender.printResponse(req)
  }
}
