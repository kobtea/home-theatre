package domains.model.material

import java.io.File

import com.typesafe.config.ConfigFactory

/**
 * 作業単位のファイル群
 *
 * @param tsFile TSファイル
 * @param errFile エラーログファイル
 * @param programFile メタデータファイル
 * @param mp4File MP4ファイル
 */
case class Material(tsFile: File,
                    errFile: Option[File],
                    programFile: Option[File],
                    mp4File: Option[File]) {
  val name = tsFile.getName.split('.')(0)

  override def toString = s"Material($name, err_file: ${errFile.isDefined}, program_file: ${programFile.isDefined}, mp4_file: ${mp4File.isDefined})"

  /**
   * 対象ディレクトリへ移動する
   *
   * @param dir 移動先のディレクトリ
   * @return 正常に完了したかどうか
   */
  def moveTo(dir: File): Boolean = {
    if (dir.isDirectory) {
      // TODO: 移動先に十分な容量があるか確認したほうが良いかも
      // FIXME: 各移動処理の結果を確認しておきたい
      tsFile.renameTo(new File(dir, tsFile.getName))
      List(errFile, programFile, mp4File) foreach {
        case Some(f) => f.renameTo(new File(dir, f.getName))
        case None => false
      }
      true
    } else false
  }

  /**
   * ゴミ箱相当のディレクトリへ移動する
   *
   * @return 正常に完了したかどうか
   */
  def trash: Boolean = {
    val config = ConfigFactory.load("private")
    moveTo(new File(config.getString("path.trash")))
  }
}
