package models

import scala.util.Random

case class EightBallReply(content: String)

object EightBallReply {

  private var replies = Array(
    EightBallReply("Nooo"),
    EightBallReply("Yes"),
    EightBallReply("Absolutely no!"),
    EightBallReply("Can you repeat please?"),
    EightBallReply("I'm sorry, I don't know"),
    EightBallReply("Forget about it!"),
    EightBallReply("Tomorrow is a good day to die."),
    EightBallReply("...")
  )


  def randomBall: EightBallReply = replies(Random.nextInt(replies.length))

  def toList: List[EightBallReply] = replies toList

  def apply(id: Int): Option[EightBallReply] = replies.lift(id)

  def add(newReply: EightBallReply): Unit = { replies = replies.+:( newReply ) }







}
