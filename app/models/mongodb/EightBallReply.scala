package models.mongodb

import reactivemongo.bson._
import services.mongo._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EightBallReply(_id: Option[BSONObjectID], var message: String) extends Model[EightBallReply](_id) {
  def this(message: String) = this(None, message)

  private[EightBallReply] def this(bson: BSONDocument) =
    this(
      bson.getAs[BSONObjectID]("_id"),
      bson.getAs[String]("message").get
    )

  val collection: Collection = EightBallReply

  val writer: BSONDocumentWriter[EightBallReply] = EightBallReply.Writer

  def toBSON: BSONDocument = {
    val doc: BSONDocument = BSONDocument("message" -> message)
    if(_id.isDefined) doc ++ ("_id" -> id) else doc
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
    def findById(id: BSONObjectID): Future[Option[EightBallReply]] = {
      collection.flatMap(_.find(BSONDocument("_id" -> id)).one[EightBallReply])
    }

    def getRandomBall: Future[EightBallReply] = {
      collection.flatMap { coll =>
        import coll.BatchCommands.AggregationFramework.Sample
        coll.aggregate(Sample(1)).map(_.head[EightBallReply].head)
      }
    }
  }

}