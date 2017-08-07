package models

import scala.util.Random

case class EightBallReply (content: String)

object EightBallReply {

  private var replies = Array(
    EightBallReply("Nooo"),
    EightBallReply("Yes"),
    EightBallReply("Absolutely no!")
  )


  def getRndBall: EightBallReply = replies(Random.nextInt(replies.length))

  def getAll: List[EightBallReply] = replies toList

  def add(newReply: EightBallReply): Unit = { replies = replies.+:( newReply ) }







}
