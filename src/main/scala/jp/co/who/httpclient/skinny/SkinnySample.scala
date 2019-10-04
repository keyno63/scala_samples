package jp.co.who.httpclient.skinny

import skinny.http._

class SkinnySample {

  val readTimeoutMillis = 5000
  val connectTimeoutMillis = 5000

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.GET, url, headers)

  def Post(url: String, headers: Map[String, String],
           requestBody: String, contentType: String = "application/x-www-form-urlencoded"): CustomResponse =
    httpRequest(Method.POST, url, headers, requestBody)

  def Put(url: String, headers: Map[String, String],
          requestBody: String, contentType: String = "application/x-www-form-urlencoded"): CustomResponse =
    httpRequest(Method.PUT, url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.DELETE, url, headers)

  def httpRequest(webMethod: Method, url: String, headers: Map[String, String],
                  requestBody: String = "", contentType: String = ""): CustomResponse = {

    var request = new Request(url)
    request.connectTimeoutMillis(this.readTimeoutMillis)
    request.readTimeoutMillis(this.connectTimeoutMillis)
    if (!requestBody.isEmpty && !contentType.isEmpty) {
      request.body(requestBody.getBytes, contentType)
    }
    headers.foreach {
      case (k, v) => {
        //request = request.header(k, v)
        request.header(k, v)
      }
    }
    println(request.headerNames)
    request.headers.foreach {
      case (k, v) => println(k, v)
    }
    val response = HTTP.request(webMethod, request)

    return extractResponse(response)
  }

  def extractResponse(res: Response): CustomResponse = {

    val statusCode = res.status
    val httpStatus = statusCode match {
      case 200 => "OK"
      case 201 => "CREATED"
      case 204 => "NO_CONTENT"
      case 401 => "UNAUTHORIZED"
      case 400 => "BAD_REQUEST"
      case 404 => "NOT_FOUND"
      case 500 => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }
    val body = res.textBody
    return CustomResponse(statusCode, httpStatus, body)
  }

}

case class CustomResponse(statusCode: Int, status: String, body: String)
