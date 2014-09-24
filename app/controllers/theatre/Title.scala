package controllers.theatre

import play.api.mvc._
import java.io._
import com.typesafe.config.ConfigFactory

object Title extends Controller{
  val config = ConfigFactory.load("theatre")

  def list = Action{
    val rootDir = new File(config.getString("theatre.directory.root"))
    val directories = rootDir.listFiles()

    Ok(views.html.theatre.list(directories))
  }
}
