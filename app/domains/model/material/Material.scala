package domains.model.material

import java.io.File

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
}
