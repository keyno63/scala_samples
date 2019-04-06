package jp.co.who.httpclient

import scala.swing.event.{ButtonClicked, EditDone}
import swing._

object HttpClientGUI extends SimpleSwingApplication {

  def top = new MainFrame() {
    title = this.getClass.toString
    minimumSize = new Dimension(300, 50)
    val button = new Button {
      text = "Send"
    }

    var url = new TextField
    val body = new Label
    var data = ""

    contents = new BoxPanel(Orientation.Vertical) {
      contents += new Label("URL")
      contents += url
      //contents += hoge
      contents += body
      contents += button
    }
    listenTo(url, button)
    reactions += {
      case EditDone(url) =>
        //hoge.text = url.text
        data = url.text
      case ButtonClicked(b) =>
        //hoge.text = ""
        body.text = sendRequest(data)
        //Dialog.showMessage( message = "sendTo: " + data)
    }
  }

  def sendRequest(url: String): String = {
    Dialog.showMessage( message = "sendTo: " + url )
    "sample data"
  }


}
