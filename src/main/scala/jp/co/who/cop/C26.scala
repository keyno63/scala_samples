package jp.co.who.cop

class C26 extends LogicBase {
  override def run = {
    Common.head("start C26.")
    testExt

  }

  def testExt = {
    object EMail {
      def apply(user: String, domain: String) = user + "@" + domain
      // 抽出子
      def unapply(str: String): Option[(String, String)] = {
        val parts = str split "@"
        if (parts.length == 2) Some(parts(0), parts(1)) else None
      }
    }
    //"fujiwara_kohei@nextgen.co.jp" match { case EMail(user, domain) => }
  }

}
