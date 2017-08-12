package models.mongodb

import reactivemongo.api.collections.bson.BSONCollection
import services.Database

import scala.concurrent.Future

class EightBallReply (db: Database) {

  def write(): Unit = {

  }
}

object EightBallReply {
  lazy val collection: Future[BSONCollection] = Database.instance.collection("eightballreply")

}