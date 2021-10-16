package jp.co.who.sandbox

class Service {

  def func1(id: String): ServiceResponse = {
    ServiceResponse(id)
  }
}

case class ServiceResponse(id: String)
