package jp.co.who.sandbox

import org.scalatest.flatspec.AnyFlatSpec

class ServiceTest extends AnyFlatSpec {

  "Service.func1" should "be same as ServiceResponse.Id" in {
    val target = new Service
    val act = target.func1("10")
    assert(act.id == "10")
  }
}
