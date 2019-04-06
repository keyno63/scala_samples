package jp.co.who

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._

import scala.concurrent._





object Main {
  def main(args: Array[String]): Unit = {
    testReq
  }

  def testReq: Unit = {
    val str = "hello world"
    println(str)
    /*
    val win = MainFrameSample
    win.main(args)
    */
    val filePath = "./src/resources/scala_test.properties"

    val p = new java.util.Properties()
    val config = {
      p.load(new java.io.FileInputStream(filePath))
    }
    println(config)
    val app = p.getProperty("scala_test.monitor.app", "")
    println(app)
  }

  def request(uri: String): Future[HttpResponse] = {
    val req = HttpRequest(GET, uri = Uri(uri))
      .withHeaders(
        RawHeader("Accept-Encoding", "gzip,deflate")
      )

    val data = ActorSystem("system")
    Http(system = data: ActorSystem).singleRequest(req)
  }

}
