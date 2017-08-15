package services.mongo

import reactivemongo.api.collections.bson.BSONCollection
import services.Database

import scala.concurrent.Future

trait Collection {
  def name: String

  def collection: Future[BSONCollection] = Database.instance.collection(name)
}
