package domains.model.material

import java.io.File
import scala.collection.mutable.ListBuffer

object MaterialRepository {
  /**
   * 対象ディレクトリのMaterialを返す
   *
   * @param path 捜査対象のディレクトリ
   * @return Materialリスト
   */
  def retrieve(path: File): List[Material] = {
    var buffer = ListBuffer[Material]()
    val files: Array[File] = path.listFiles
    val tsFiles = files.filter(_.getName.endsWith(".ts"))

    for(tsFile <- tsFiles) {
      val errFile = {
        val f = new File(tsFile.toString + ".err")
        if (f.exists) Some(f) else None
      }
      val programFile = {
        val f = new File(tsFile.toString + ".program.txt")
        if (f.exists) Some(f) else None
      }
      val mp4File = {
        val f = new File(tsFile.toString.replace(".ts", ".mp4"))
        if (f.exists) Some(f) else None
      }
      buffer += Material(tsFile, errFile, programFile, mp4File)
    }
    buffer.toList
  }

}
