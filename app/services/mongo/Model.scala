package services.mongo

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Future

abstract class Model(private var _id: Option[BSONObjectID] = None) {
  def collection: Collection

  def id: Option[BSONObjectID] = _id

  def exists: Boolean = _id.isDefined

  /*def save: Future[WriteResult] = {
    _id match {
      case None =>
        _id = Option(BSONObjectID.generate)
        collection.collection.insert(this)

      case Some(id) =>
        collection.collection.update(BSONDocument("_id" -> id), this)
    }
  }*/
}
