package services.mongo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, BSONObjectID}

abstract class Model[T <: Model[T]](private var _id: Option[BSONObjectID] = None) {
  def id: Option[BSONObjectID] = _id

  def collection: Collection

  protected def writer: BSONDocumentWriter[T]

  def save: Future[WriteResult] = {
    val self = this.asInstanceOf[T]
    implicit val writer: BSONDocumentWriter[T] = this.writer

    _id match {
      case None =>
        _id = Option(BSONObjectID.generate)
        collection.collection.flatMap(_.insert(self))

      case Some(id) =>
        collection.collection.flatMap(_.update(BSONDocument("_id" -> id), self))
    }
  }
}
