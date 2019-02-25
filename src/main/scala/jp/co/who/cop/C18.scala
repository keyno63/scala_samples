package jp.co.who.cop

class C18 extends LogicBase {
  override def run: Unit = {
    Common.head("start C18.")
    chkMut
    chkSetGetter
  }
  // 18.1
  private class BankAccount {
    private var bal: Int = 0
    def balance: Int = bal
    def depoit(amount: Int): Unit = {
      require(amount > 0)
      bal += amount
    }
    def withdraw(amount: Int): Boolean =
      if (amount > bal) false
      else {
        bal -= amount
        true
      }
  }

  def chkMut = {
    val account = new BankAccount
    account depoit 100
    println(account.withdraw(80))
    println(account.withdraw(80))
  }

  // 18.2 setter/getter
  private class Time {
    private[this] var h = 12
    private[this] var m = 0
    def hour: Int = h
    def hour_=  (x: Int) = {
      require(0 <= x && x < 24)
      h = x
    }
    def minute = m
    def minute_= (x: Int) = {
      require(0 <= x && x < 60)
      m = x
    }
  }

  def chkSetGetter = {
    var t = new Time
    t.hour = 23
    println(t.hour)
  }

}
