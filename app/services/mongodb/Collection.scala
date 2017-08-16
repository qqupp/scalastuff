package services.mongo

import scala.concurrent.Future
import reactivemongo.api.collections.bson.BSONCollection

import services.Database

trait Collection {
  def name: String

  def collection: Future[BSONCollection] = Database.instance.collection(name)
}
