package jp.co.who.httpclient.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.coding.{Deflate, Gzip, NoCoding}
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.headers.{HttpEncodings, RawHeader}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.xml.XML

class HttpSenderImpl extends HttpSender {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  val useXML = false

  // request 実装
  @Override
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

  @Override
  def printResponse(req: Future[HttpResponse] ): Unit = {
    if (useXML) {
      req.map(decodeResponse(_)).map(postProcess(_)).recover {
        case e =>
          println(e.getMessage)
          Seq
      }
    } else {
      val ret = req.map(decodeResponse(_))
      val h = ret.map(_.headers).map(println)
      val r = ret.map(_.entity).map(println)
      //req.map(decodeResponse(_).headers).map(println)
    }
  }

}
