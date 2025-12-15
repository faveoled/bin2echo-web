package app

import org.scalajs.dom
import org.scalajs.dom.HTMLInputElement
import org.scalajs.dom.window.alert

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.annotation.*
import scala.util.Failure
import scala.util.Success
implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

// import javascriptLogo from "/javascript.svg"
@js.native @JSImport("/javascript.svg", JSImport.Default)
val javascriptLogo: String = js.native

@main
def App(): Unit =
  dom.document.querySelector("#app").innerHTML = s"""
    <div>
      <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank">
        <img src="$javascriptLogo" class="logo vanilla" alt="JavaScript logo" />
      </a>
      <h1>bin2echo</h1>

      <!-- File selector -->
      <label for="fileUpload">Choose a file:</label>
      <input type="file" id="fileUpload" name="fileUpload">

      <br>

      <label for="bytesCount">Bytes per line:</label>
      <input type="number" id="bytesCount" name="bytesCount" min="1" value="6" />

      <br>

      <button id="convertBtn" type="button">Convert</button>
    
      <!-- Multiline text field -->
      <label for="textOut"></label><br>
      <textarea id="textOut" name="textOut" rows="24" cols="80" placeholder="bin2echo output will be here"></textarea>

      <br>

      <button id="dlBtn" type="button">Download</button>

    </div>
  """

  val convertBtn = dom.document.getElementById("convertBtn")
  convertBtn.addEventListener("click", e => onConvertBtnClick())

  val dlBtn = dom.document.getElementById("dlBtn")
  dlBtn.addEventListener("click", e => onDlBtnClick())

end App

def onDlBtnClick(): Unit = {
  val time = new Date().getTime().toString()
  val filename = UI.getFileName().getOrElse(s"bin2echo-$time.sh")
  FileDownloader.download(filename + ".sh", UI.getTextOutContent())
}

def onConvertBtnClick(): Unit = {
  
  val fileInput = dom.document.getElementById("fileUpload").asInstanceOf[HTMLInputElement]
  val fileList = fileInput.files
  if (fileList.length == 0) {
    alert("Please select a file first")
    return
  }
  fileList(0).arrayBuffer().toFuture.onComplete {
    case Success(value) => {
      val bytes = ScalaUtil.toScalaArray(value)
      val textOut = Converter.bytesToEchoCommands(bytes, fileList(0).name, UI.bytesCount())
      UI.setTextOutContent(textOut)
    }
    case Failure(ex)    => 
      alert(s"Failure: $ex")
  }
}

