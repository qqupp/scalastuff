package controllers

import javax.inject._

import play.api.mvc._

import models.EightBallReply

@Singleton
class EightBallController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def randomMessage = Action { implicit request =>
    val rndString = EightBallReply.randomBall.content

    def _gencontent[T,T1](f: T => T1)(x: T): T1 = f(x)

    def _generate[T] = _gencontent( _: String => T )(rndString)

    render {
      case Accepts.Xml() => Ok(_generate( x => <b>{x}</b>))
      case Accepts.Json() => Ok(rndString)
    }

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
