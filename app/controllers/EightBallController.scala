package controllers

import javax.inject._

import play.api.mvc._

import models.EightBallReply

@Singleton
class EightBallController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def randomMessage = Action {
    val rndString = EightBallReply.randomBall.content
    Ok(rndString)
  }

  def messages = Action {
    val allMsgs = (EightBallReply.toList map ( _.content )).
      foldRight("")( _ + "\n" + _ )
    Ok(allMsgs)
  }

  def message(id: Int) = Action {
    EightBallReply(id) map { x =>
      Ok(x.content)
    } getOrElse {
      NotFound("Nothing to see here!")
    }
  }
}
