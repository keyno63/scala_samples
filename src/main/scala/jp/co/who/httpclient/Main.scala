package jp.co.who.httpclient


// akka
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.coding.{Deflate, Gzip, NoCoding}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.xml.XML

object Main {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  // tmp
  val useXML = false;
  val proto = "http"
  val host = "localhost"
  val port = 8080
  val entry = "hoge"

  def main(args: Array[String]): Unit = {
    while (true) {
      printf("set string: ")
      val stdin = io.StdIn.readLine().split(" ")
      if (stdin.head != "") {
        sendRequest(stdin.head)
      } else {
        //println("null string. set again.")
        val str = {
          "%s://%s:%d/%s".format(proto, host, port, entry)
        }
        sendRequest(str)
      }
    }
  }

  // request 送信、reaponse表示
  def sendRequest(url: String): Unit = {
    //val url = "http://localhost:8080/hoge"
    val req = request(url)
    //req.map(decodeResponse(_)).map(println)
    execXML(req)
  }

  // request 実装
  def request(uri: String): Future[HttpResponse] = {
    val req = HttpRequest(GET, uri = Uri(uri))
      .withHeaders(
        RawHeader("Accept-Encoding", "gzip,deflate")
      )
    Http().singleRequest(req)
  }

  // エンコーダー
  def decodeResponse(res: HttpResponse): HttpResponse = {
    val decoder = res.encoding match {
      case HttpEncodings.gzip ⇒
        Gzip
      case HttpEncodings.deflate ⇒
        Deflate
      case HttpEncodings.identity ⇒
        NoCoding
      case _ ⇒
        NoCoding
    }

    decoder.decodeMessage(res)
  }
  // ボディ表示
  def extractBody(res: HttpResponse): String = {
    val body: Future[String] = Unmarshal(res.entity).to[String]

    Await.result(body, Duration.Inf)
  }
  // フィードから抽出
  def postProcess(res: HttpResponse): Seq[String] = {
    val content = extractBody(res)

    val xml = XML.loadString(content)
    val titles = for (t <- xml \\ "title") yield t.text

    println("success: %d titles found (%d bytes)".format(titles.size, content.length))

    titles.toSeq
  }

  def execXML(req: Future[HttpResponse] ): Unit = {
    if (useXML) {
      req.map(decodeResponse(_)).map(postProcess(_)).recover {
        case e =>
          println(e.getMessage)
          Seq
      }
    } else {
      //req.map(decodeResponse(_)._3.getDataBytes()).map(println)
      req.map(decodeResponse(_).headers).map(println)
    }
  }
}
