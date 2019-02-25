package jp.co.who.cop

class Common (val s: String)

object Common {

  def head(value: String) = {
    println("""=== %s ===""".format(value))
  }

}
