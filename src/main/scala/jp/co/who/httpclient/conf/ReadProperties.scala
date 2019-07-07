package jp.co.who.httpclient.conf

object ReadProperties {
  def main(args: Array[String]):Unit = {
    readPropertyFile()
  }

  //プロパティファイルを読み込んで表示
  def readPropertyFile():Unit = {

    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream("conf.properties"))
    val result = p.getProperty("hoge")
    println(result)
  }

}
