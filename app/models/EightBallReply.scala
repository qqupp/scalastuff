package models

import scala.util.Random

case class EightBallReply (content: String)

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


  def getRndBall: EightBallReply = replies(Random.nextInt(replies.length))

  def getAll: List[EightBallReply] = replies toList

  def add(newReply: EightBallReply): Unit = { replies = replies.+:( newReply ) }







}
