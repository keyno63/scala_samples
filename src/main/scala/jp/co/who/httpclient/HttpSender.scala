package jp.co.who.httpclient

import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.Future

trait HttpSender {

  def request(uri: String): Future[HttpResponse]
  def printResponse(req: Future[HttpResponse] ): Unit
}
