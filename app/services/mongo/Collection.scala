package services.mongo

import reactivemongo.api.collections.bson.BSONCollection
import services.Database
import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait Collection {
  def name: String

  lazy val collection: BSONCollection = Await.result(Database.instance.collection(name), Duration.Inf)
}
