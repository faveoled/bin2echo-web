package app

import org.scalajs.dom
import org.scalajs.dom.HTMLInputElement

object UI {
  def setTextOutContent(content: String): Unit = {
    dom.document.getElementById("textOut").textContent = content
  }

  def getTextOutContent(): String = {
    dom.document.getElementById("textOut").textContent
  }

  def getFileName(): Option[String] = {
    val fileInput = dom.document.getElementById("fileUpload").asInstanceOf[HTMLInputElement]
    val fileList = fileInput.files
    if (fileList.length == 0) {
      None
    } else {
      Some(fileList(0).name)
    }
  }

  def bytesCount(): Int = {
    val num = dom.document.getElementById("bytesCount")
      .asInstanceOf[HTMLInputElement]
      .valueAsNumber.toInt
    return if num < 1 then 1 else num
  }
}
