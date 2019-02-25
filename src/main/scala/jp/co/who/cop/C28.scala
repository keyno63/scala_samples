package jp.co.who.cop

class C28 extends LogicBase {
  override def run = {
    Common.head("start C28.")
    testXML
    testToXML

  }

  // 28.3
  def testXML = {
    val yearMade = 1955
    val vXml =
      <a> { if (yearMade < 2000) <old>{yearMade}</old> else xml.NodeSeq.Empty}</a>
    println(vXml)
  }

  // 28.4
  def testToXML = {
    abstract class CCTherm {
      val description: String
      val yearMade: Int
      val dateObtained: String
      val bookPrice: Int
      val purchasePrice: Int
      val condition: Int
      override def toString = description
      def toXML =
        <cctherm>
          <description>{description}</description>
          <yearMade>{yearMade}</yearMade>
          <dateObtained>{dateObtained}</dateObtained>
          <bookPrice>{bookPrice}</bookPrice>
          <purchasePrice>{purchasePrice}</purchasePrice>
          <condition>{condition}</condition>
        </cctherm>
    }
    val therm = new CCTherm {
      override val description: String = "hot dog #5"
      override val yearMade: Int = 1952
      override val dateObtained: String = "March 14, 2006"
      override val bookPrice: Int = 2199
      override val purchasePrice: Int = 500
      override val condition: Int = 9
    }
    println(therm.toXML)
  }

}
