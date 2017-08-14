package controllers

import javax.inject._
import controllers.HomeController.Alphabet
import models.mongodb.EightBallReply
import play.api.Logger
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def randomEightBall: Action[AnyContent] = Action.async {
    EightBallReply.Queries.getRandomBall.map { ball => Ok(ball.message) }
  }

  def eightBall(id: String): Action[AnyContent] = Action.async {
    decompress(id) match {
      case Failure(_) =>
        Future(BadRequest("Invalid id format."))
      case Success(oid) =>
        EightBallReply.Queries.findById(oid).map {
          case None => NotFound("Unknown id.")
          case Some(ball) => Ok(ball.message)
        }
    }
  }

  def compress(id: BSONObjectID, alphabet: String = Alphabet): Try[String] = {
    HomeController.toString(BigInt(id.stringify, 16), alphabet)
  }

  def decompress(id: String, alphabet: String = Alphabet): Try[BSONObjectID] = {
    HomeController.toBigInt(id, alphabet).flatMap(v => BSONObjectID.parse(v.toString(16)))
  }
}

object HomeController {
  val Alphabet = "-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~"

  def toString(value: BigInt, alphabet: String = Alphabet): Try[String] = {
    if(value < 0) {
      return Failure(new IllegalArgumentException(s"""Value must be greater or equal to 0. Got $value"""))
    }

    if(value == 0) {
      return Success("0")
    }

    var digits: List[Int] = Nil
    var num = value
    while(num > 0) {
      digits = (num % alphabet.length).toInt :: digits
      num /= alphabet.length
    }

    Success(digits.map(alphabet(_)).mkString(""))
  }

  def toBigInt(value: String, alphabet: String = Alphabet): Try[BigInt] = {
    val digits = value.map(alphabet.indexOf(_))

    if(digits.contains(-1)) {
      return Failure(new StringIndexOutOfBoundsException("""Value contains characters outside the given alphabet."""))
    }

    Success(digits.map(BigInt(_)).reduceLeft(_ * alphabet.length + _))
  }
}
