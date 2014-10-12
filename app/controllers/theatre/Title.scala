package controllers.theatre

import play.api.mvc._
import java.io._
import com.typesafe.config.{Config, ConfigFactory}

object Title extends Controller {
  val config: Config = ConfigFactory.load("theatre")
  val rootDir: File = new File(config.getString("theatre.directory.root"))

  def list = Action {
    val directories: Array[File] = rootDir.listFiles()
    Ok(views.html.theatre.list(directories))
  }

  def detail(title: String) = Action {
    val directory: File = new File(rootDir, title)
    val files: Array[File] = directory.listFiles
    Ok(views.html.theatre.detail(files))
  }

  def show(title: String, chapter: String) = Action {
    val file: File = new File(new File(rootDir, title), chapter)
    Ok(views.html.theatre.show(file))
  }
}
