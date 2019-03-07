package jp.co.who.swing

import java.awt.Shape
import java.awt.geom.Rectangle2D

import sun.font.CoreMetrics
import sun.font.Decoration.Label

import scala.swing._
import scala.swing.event._

object MainFrameSample extends SimpleSwingApplication {
  var app: String = ""

  def top = new MainFrame {
    // Windowのタイトル
    title = "Window Title"
    // Windowのサイズ
    minimumSize = new Dimension( 300, 200 )
    val button = new Button {
      text = "Click me"
    }

    //val label = new Label
    if (!loadConfig) {
      //throw IllegalAccessError
      Nil

    }

    contents = app match {
      case "box" => getBox(button)
      case "spreadSheet" => new Spreadsheet(100, 26)
      case _ => new Spreadsheet(1, 1)
    }

    listenTo(button)
    var nClicks = 0
    reactions += {
      case ButtonClicked(b) =>
        nClicks += 1
        button.text = (nClicks % 3) match {
          case 0 => "さーん"
          case _ => "clicked=" + nClicks
        }
    }
  }

  def getBox(button: Button) = {
    new BoxPanel(Orientation.Vertical) {
      contents += button
      //contents += label
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
  }

  def loadConfig= {
    val filePath = "./src/resource/scala_test.properties"

    val p = new java.util.Properties()
    p.load(new java.io.FileInputStream(filePath))
    app = p.getProperty("scala_test.monitor.app")

    true
  }
}
