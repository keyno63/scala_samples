package jp.co.who.httpclient.skinny

import skinny.http.{HTTP, Request, Response}

class CustomSkinny {

  def get(url: String, query: Map[String, String]): Response = {
    val request = Request(url)
    query.foreach{ kv
      => val (k,v) = kv
      request.queryParams(k -> v)
    }
    HTTP.get(request)
  }

  def post(url: String, query: Map[String, Any]): Response  = {

    val request = Request(url)
    val b = query.map(item => s"${item._1}=${item._2}").mkString("&")
    /*
    val b = new StringBuilder
    query.foreach{ kv =>
      val (k,v) = kv
      if (b.length > 0) b.append("&")
      b.append(s"${k}=${v}")
    }
    */
    val response = HTTP.post(request.body(b.getBytes()))
    response
  }

  def sample(query: Map[String, String]) {
    val request = Request("http://example.com")

    query.foreach { item =>
      val (k, v) = item
      request.queryParams(k -> v)
    }
  }
}

object CustomSkinny {
  @Override
  def main(args: Array[String]): Unit = {

    val url = "http://192.168.1.148:8080/api/param"
    var map = Map.empty[String, String]
    map += "param1" -> "fuga"
    map += "param2" -> "fuga1"
    //get(map)
    val cs = new CustomSkinny
    println(cs.post(url, map))
    perf()
  }

  def perf(): Unit = {
    val url = "http://192.168.1.148:8080/api/param"
    val request = Request(url)
    var map = Map.empty[String, String]
    map += "param1" -> "fuga"
    map += "param2" -> "fuga1"
    val b = map.map(item => s"${item._1}=${item._2}").mkString("&")
    /*
    for (x <- 0 until 100)
    {
      val response = HTTP.post(request.body(b.getBytes()))
      println(response.status, response.textBody)
    }
    */
    (0 to 100).foreach{
      val response = HTTP.post(request.body(b.getBytes()))
      println(_, response.status, response.textBody)
    }

  }

}
