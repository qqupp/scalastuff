package models.mongodb

import reactivemongo.api.collections.bson.BSONBatchCommands.AggregationFramework
import reactivemongo.bson._
import services.mongo._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EightBallReply(_id: Option[BSONObjectID], var message: String) extends Model(_id) {
  def this(message: String) = this(None, message)

  private[EightBallReply] def this(bson: BSONDocument) =
    this(
      bson.getAs[BSONObjectID]("_id"),
      bson.getAs[String]("message").get
    )

  def collection: Collection = EightBallReply

  def toBSON: BSONDocument = {
    var doc = BSONDocument("message" -> message)
    if(_id.isDefined) doc ++= ("_id" -> id)
    doc
  }
}

object EightBallReply extends Collection {
  override def name: String = classOf[models.mongodb.EightBallReply].getSimpleName.toLowerCase

  implicit object Reader extends BSONDocumentReader[EightBallReply] {
    override def read(bson: BSONDocument): EightBallReply = new EightBallReply(bson)
  }

  implicit object Writer extends BSONDocumentWriter[EightBallReply] {
    override def write(obj: EightBallReply): BSONDocument = obj.toBSON
  }

  object Queries {
    def getRandomBall: Future[EightBallReply] = {
      import collection.BatchCommands.AggregationFramework.Sample
      collection.aggregate(Sample(1)).map(_.head[EightBallReply].head)
    }
  }

}