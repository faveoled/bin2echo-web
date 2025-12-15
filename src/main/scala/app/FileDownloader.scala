package app

import org.scalajs.dom.Blob
import org.scalajs.dom.BlobPropertyBag
import org.scalajs.dom.HTMLAnchorElement
import org.scalajs.dom.URL
import org.scalajs.dom.document
import org.scalajs.dom.raw.Blob
import org.scalajs.dom.window

import scala.scalajs.js

object FileDownloader {

  def download(filename: String, content: String): Unit = {
    val blob = new Blob(js.Array(content), BlobPropertyBag(`type` = "text/plain"))
    download(filename, blob)
  }

  def download(filename: String, content: Blob): Unit = {
    val elem = window.document.createElement("a").asInstanceOf[HTMLAnchorElement];
    elem.href = URL.createObjectURL(content);
    elem.download = filename;
    document.body.appendChild(elem);
    elem.click();
    document.body.removeChild(elem);
  }
}
