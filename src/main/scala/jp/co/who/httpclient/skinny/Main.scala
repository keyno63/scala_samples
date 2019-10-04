package jp.co.who.httpclient.skinny

import io.circe._
import io.circe.syntax._

object Main extends App {
  implicit val url = "http://example.com/"
  val req = new SkinnySample

  val req2 = Param("hoge", 10, true)
  //val res2 = req.Post(url = "http://localhost:9000/create", Map("content-type" -> "application/json"), req2.asJson.toString())
  val res2 = req.Post(url = "http://localhost:9000/create", Map[String, String](), req2.asJson.toString())
  println(res2)

  def reqest1() = {
    val res = req.Get(url, Map.empty)
    println(res)

  }

  def reqest2() = {
    /*
    val response = HTTP.post("http://example.com/", "foo" -> "bar")
    val response = HTTP.get(url, "param1" -> "bar")
    println(response)
    */
  }

  def reqest2_() = {
    val postRes = req.Post(url = url, Map.empty, requestBody = "param1=bar")
    println(postRes)

  }
}



case class Param(value1: String, value2: Int, value3: Boolean)
object Param{
  implicit val decoder: Decoder[Param] = new Decoder[Param] {
    final def apply(c: HCursor): Decoder.Result[Param] =
      for {
        foo <- c.downField("value1").as[String]
        bar <- c.downField("value2").as[Int]
        zoo <- c.downField("value3").as[Boolean]
      } yield {
        Param(foo, bar, zoo)
      }
  }

  implicit val encoderParam: Encoder[Param] =
    Encoder.forProduct3("value1", "value2", "value3")(u =>
      (u.value1, u.value2, u.value3))
}