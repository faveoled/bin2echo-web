package app

import org.scalajs.dom.Blob

import scala.scalajs.js
import scala.scalajs.js.typedarray.*

import scalajs.js.typedarray.AB2TA

object ScalaUtil {
  
  def toScalaArray(input: ArrayBuffer): Array[Byte] =
    new Int8Array(input).toArray

  def toBlob(input: Array[Byte]): Blob = {
    new Blob(js.Array(input.toTypedArray))
  }

}
