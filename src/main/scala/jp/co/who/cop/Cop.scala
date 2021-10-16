package jp.co.who.cop

import jp.co.who.Sample

object Cop extends App {
  Console.println("Hello World: " + (args mkString ", "))
  val a = new Sample("hoge")
  a.call()
  hoge("new")
  val c2 = new C2(args)
  val c3 = new C3
  val c4 = new C4(args)
  val c6 = new C6
  val c7 = new C7
  val c8 = new C8
  val c9 = new C9
  val c10 = new C10
  val c16 = new C16
  val c17 = new C17
  val c18 = new C18
  val c19 = new C19
  val c20 = new C20
  val c23 = new C23
  val c24 = new C24
  val c26 = new C26
  val c28 = new C28


  def hoge(a: String): Unit = {
    Console.println("hogehoge: " + a)
  }

}
