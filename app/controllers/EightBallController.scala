package controllers

import javax.inject._

import play.api.mvc._

import models.EightBallReply


@Singleton
class EightBallController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {




  def randomReply = Action {
    val rndString = EightBallReply.getRndBall.content
    Ok(views.txt.text(rndString))
  }


  def getMessages = Action {
    val allMsgs = (EightBallReply.getAll map ( x => x.content ) ).fold("")((x:String, y:String) => x + "\n" + y)
    Ok(views.txt.text(allMsgs))
  }


  def getMessage(id: Int) = Action {
    EightBallReply.get(id) map { x =>
      Ok(views.txt.text(x.content))
    } getOrElse  {
      Ok(views.txt.text("Nothing to see here!"))
    }
  }
}
