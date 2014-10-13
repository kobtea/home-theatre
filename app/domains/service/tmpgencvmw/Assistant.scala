package domains.service.tmpgencvmw

import autoitx4java.AutoItX
import java.io.File
import com.typesafe.config.ConfigFactory

object Assistant {
  // window
  val windowStart = "[CLASS:TStart_MainForm]"
  val windowMain = "[CLASS:TMainForm]"
  val windowOpen = "[CLASS:#32770]"
  val windowMessage = "[CLASS:TMessageForm]"
  val windowEdit = "[CLASS:TClipEditForm]"
  // button
  val buttonNewProject = "[CLASS:TRLStyleButton; INSTANCE:2]"
  val buttonAddFile = "[CLASS:TRLStyleButton; INSTANCE:16]"
  val buttonOpen = "[CLASS:Button; INSTANCE:1]"
  val buttonOk = "[CLASS:TRLStyleButton; INSTANCE:27]"
  // form
  val formPath = "[CLASS:ToolbarWindow32; INSTANCE:2]"
  val formFile = "[CLASS:Edit; INSTANCE:1]"
  val formClipName = "[CLASS:TRLEdit_InplaceEdit; INSTANCE:4]"

  val x = new AutoItX()
  val config = ConfigFactory.load("private")

  /**
   * TMPGEnc VMW5を起動する
   */
  private def launch() = {
    if (!x.winExists(windowStart)) {
      x.run(config.getString("tmpgencvmw.exe.path"))
      x.winWaitActive(windowStart)
    }
    x.winActivate(windowStart)
  }

  /**
   * 新規プロジェクトを開く
   */
  private def openNewProject() = {
    x.controlClick(windowStart, "", buttonNewProject)
    x.winWaitActive(windowMain)
  }

  /**
   * ファイルを追加する
   *
   * @param file 追加するファイル
   */
  private def addFile(file: File) = {
    // Open an add file window
    x.winWaitActive(windowMain)
    x.controlClick(windowMain, "", buttonAddFile)

    // Open a file
    x.winWaitActive(windowOpen)
    x.ControlSetText(windowOpen, "", formFile, file.toString)
    x.controlClick(windowOpen, "", buttonOpen)

    // .............
    // Choose a clip
    // .............

    // Fill a clip name
    x.winWaitActive(windowEdit)
    x.ControlSetText(windowEdit, "", formClipName, file.getName.split('.')(0))
    x.controlClick(windowEdit, "", buttonOk)
  }

  /**
   * 新規プロジェクトを作成しファイルを追加する
   *
   * @param files 追加するファイル
   */
  def addFiles(files: List[File]) = {
    launch()
    openNewProject()
    files.map(addFile)
  }
}
