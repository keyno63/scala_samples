package jp.co.who.httpclient.skinny

import skinny.http.HTTP

object Main extends App {
  val url = "http://example.com/"
  val req = new SkinnySample
  val res = req.Get(url, Map.empty)

  println(res)

  //val response = HTTP.post("http://example.com/", "foo" -> "bar")
  val response = HTTP.get(url, "param1" -> "bar")
  println(response)

  val postRes = req.Post(url = url, Map.empty, requestBody = "param1=bar")
  println(postRes)
}
