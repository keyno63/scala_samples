package jp.co.who.scraper

import java.io._

import jp.co.who.scraper.Sample.url
import net.ruippeixotog.scalascraper.browser.{Browser, _}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._



object Sample {
  val workerDirectory = System.getenv("PWD")

  // config param
  var url = "https://baseball-data.com/player"
  var cEntriesStr = "c,s,g,yb,d,t"
  var pEntriesStr = "l,h,f,bs,m,e"

  lazy val cEntries = cEntriesStr.split(",").toSeq
  lazy val pEntries = pEntriesStr.split(",").toSeq
  lazy val entries = cEntries ++ pEntries

  val matcher = """^(http).*""".r

  def main(args: Array[String]) = {
    println(s"${this.getClass}. start.")
    loadConf
    for (entry <- entries) {
      val s = new Thread(new SampleRunnable(entry))
      s.start
      s.join
      Thread.sleep(30000)
    }
    println("finished")
  }

  private def simplePrint(doc: Browser#DocumentType ): Unit = {
    println(doc)
  }

  def xPrint(doc: Browser#DocumentType ) = {
    val text_ = doc >> text("title")
    val name = doc >> text("h2")
    val href_ =  getPlayersURL(doc)
    println("=== xPrint ===")
    println(text_)
    println(name)
    href_.foreach(println)
  }

  def getPlayersURL(doc: Browser#DocumentType ) = {
    val ret = doc >> element("tbody") >> elementList("tr") >> element("td > a") >> attrs("href")("a")
    ret

  }

  def extraPrint(doc: Browser#DocumentType ): Unit = {
    for {
      main <- doc >?> element("#main div")
      topEntry <- main >?> element("div[data-track-section=hotentry] > ul > li.entry-unit")
      entries  <- main >?> elementList("div > ul[data-track-section=hotentry] > li.entry-unit")
      entry    <- topEntry +: entries
      users    <- entry >?> text("ul.users")
      contents <- entry >?> element("div.entry-contents h3")
      url      <- contents >?> attr("href")("a")
      title = contents.text
    } {
      println("printed")
      println(s"$users,$title,$url")
    }

  }

  def loadConf(): Unit = {
    println(System.getenv("PWD"))
    val p = new java.util.Properties()
    val path = s"$workerDirectory/src/resources/scraper/freak.properties"
    p.load(new java.io.FileInputStream(path))

    url = Option(p.getProperty("scraper.url")).getOrElse(url)
    cEntriesStr = Option(p.getProperty("scraper.cList")).getOrElse(cEntriesStr)
    pEntriesStr = Option(p.getProperty("scraper.pList")).getOrElse(pEntriesStr)

  }
}

class Ptype(value: String) {
  private val types = if (value == "投手") true else false
  def is: Boolean = {
    types
  }

  override def toString: String = super.toString
}

class SampleRunnable(entry: String) extends Runnable {
  val workerDirectory = System.getenv("PWD")
  var work = workerDirectory

  val pheader = List("年度","球団","試合","勝利","敗北","セーブ","ホールド","HP","完投","完封",
    "無四球","打者","投球回","被安打","被本塁打","与四球","敬遠","与死球","奪三振","暴投",
    "ボーク","失点","自責点","防御率")
  val bheader = List("年度","球団","打席数","打数","得点","安打","二塁打","三塁打","本塁打","塁打",
    "打点","盗塁","盗塁刺","犠打","犠飛","四球","敬遠","死球","三振","併殺打","打率",
    "出塁率","長打率","OPS")

  val matcher = """^(http).*""".r

  override def run(): Unit = {
    val end = s"$url/$entry/"
    println(s"start. URL=[$end].")
    val doc = getJDoc(end)

    work = s"$workerDirectory/src/resources/scraper/output/$entry/"
    http(doc)

  }

  def getJDoc(end: String): Browser#DocumentType = {
    println(end)
    end match {
      case matcher(value) => JsoupBrowser().get(end)
      case _ => JsoupBrowser().parseFile(s"$workerDirectory/src/resources/scraper/data.xml")
    }
  }

  def http(doc: Browser#DocumentType ) = {
    val hrefs = getHref(doc)
    hrefs.foreach(accessPlayers)
  }

  def accessPlayers(end: String): Unit  = {
    Thread.sleep(3000)
    val doc = end match {
      case matcher(url) => JsoupBrowser().get(end)
    }
    playersData(doc)
  }

  /*
  データ要素を解析する
   */
  def parse(data:List[String]) = {
    var pType = ""
    val pattern1 = """^([0-9]{4})""".r
    val pattern2 = """生年月日""".r
    for (value <- data) {
      val ls = value.split(" ")
      println(value.indexOf(data))
      ls.head match {
        case pattern1(year) => buildModel(ls)
        case "通算" => buildAllModel(ls)
        case "年齢" => {
          pType = if (ls.last == "投手") "投手" else "野手"
          println(pType)
        }
        case _ =>
      }
    }
  }

  def output(name: String, data:List[String]): Unit = {
    val pattern1 = """^([0-9]{4})""".r
    val pattern2 = """生年月日""".r

    val filepath = s"$work/$name.txt"
    val f = new PrintWriter(filepath)

    for (value <- data) {
      val ls = value.split(" ")
      ls.head match {
        case "年齢" => {
          val pType = new Ptype(ls.last)
          val header = if(pType.is) pheader else bheader
          f.println(header.mkString(","))
        }
        case pattern1(year) => f.println(ls.mkString(","))
        case "通算" => f.println(ls.mkString(","))
        case _ =>
      }
    }
    f.flush()
    f.close()
  }

  private def buildModel(ls: Array[String]) : Unit = {
    ls.mkString(",")
  }

  private def buildAllModel(strings: Array[String]): Unit = {
    strings.mkString(",")
  }

  def playersData(doc: Browser#DocumentType ) = {
    val name = getPlayerName(doc)
    val ret = doc >> elementList("tr")
    val datas = ret.map(_.text)
    datas.foreach(println)
    output(name, datas)
  }

  def getHref(doc: Browser#DocumentType ): List[String] = {
    var ret = List[String]()
    for {
      tb <- doc >?> elementList("tbody")
      tr <- tb >?> elementList("tr")
      attrs <- tr >?> element("td > a")
    } {
      ret = attrs.map(_ >> attr("href")("a")).map(_.get)
    }
    ret
  }

  private def getPlayerName(doc: Browser#DocumentType ): String = {
    val strong = doc >> element("dd") >> element("strong")
    strong.text
  }

}