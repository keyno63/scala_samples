package jp.co.who.httpclient.skinny

import skinny.http.HTTP

object Main extends App {
  val url = "http://example.com/"
  val req = new SkinnySample
  val res = req.Get(url, Map.empty)

  println(res)

  //val response = HTTP.post("http://example.com/", "foo" -> "bar")
  val response = HTTP.get("http://example.com/", "foo" -> "bar")
}
