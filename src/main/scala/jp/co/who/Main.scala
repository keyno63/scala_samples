package jp.co.who

import scala.concurrent._
import scala.concurrent.duration._
import scala.xml._
import java.util.concurrent.TimeoutException

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.coding.{Deflate, Gzip, NoCoding}
import akka.http.scaladsl.unmarshalling.Unmarshal
import jp.co.who.swing.MainFrameSample





object Main {
  def main(args: Array[String]): Unit = {
    val str = "hello world"
    println(str)
    /*
    val win = MainFrameSample
    win.main(args)
    */
    val filePath = "./src/resource/scala_test.properties"

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
