package app

object Converter {
  /**
   * Converts a byte array into a sequence of shell `echo` commands that write the bytes
   * into a file in hexadecimal escape format.
   *
   * Each command writes up to `bytesPerLine` bytes, formatted as `\xHH`, to the specified file.
   * The commands use `echo -n -e` with append redirection (`>>`) so that multiple lines
   * build the file incrementally.
   *
   * Example:
   * {{{
   * val data = Array[Byte](0x68, 0x65, 0x6c, 0x6d, 0x21) // "helm!"
   * val script = bytesToEchoCommands(data, "file.bin", 4)
   * println(script)
   * // Output:
   * // echo -n -e "\x68\x65\x6c\x6d" >> file.bin
   * // echo -n -e "\x21" >> file.bin
   * }}}
   *
   * @param data         The input array of bytes to be converted into echo commands.
   * @param filename     The output filename to which the commands will append data.
   * @param bytesPerLine The number of bytes to include per echo command line.
   * @return             A single string containing all echo commands separated by newlines.
   */
  def bytesToEchoCommands(data: Array[Byte], filename: String, bytesPerLine: Int): String = {
    // Split the array into groups of size `bytesPerLine`
    data.grouped(bytesPerLine).map { group =>
      // Convert each byte to \xHH format
      val hexString = group.map(b => f"\\x${b & 0xFF}%02x").mkString
      s"""echo -n -e "$hexString" >> $filename"""
    }.mkString("\n")
  }
}
