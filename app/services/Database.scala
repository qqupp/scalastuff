package services

import play.api.Configuration
import play.api.inject.ApplicationLifecycle
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Database(configuration: Configuration, lifecycle: ApplicationLifecycle) {

  val driver = new MongoDriver

  val (connection: MongoConnection, database: Future[DefaultDB]) = {
    val uri = configuration.underlying.getString("mongodb.uri")
    val parsedUri = MongoConnection.parseURI(uri).get
    val conn = driver.connection(parsedUri)
    (conn, conn.database(parsedUri.db.get))
  }

  lifecycle.addStopHook { () =>
    connection.close()
    driver.close()
    Future.successful(())
  }

  def collection(name: String): Future[BSONCollection] = database.map(_.collection(name))
}

object Database {
  private var _instance: Database = _

  def instance: Database = _instance

  def initialize(configuration: Configuration, lifecycle: ApplicationLifecycle): Unit = {
    _instance = new Database(configuration, lifecycle)
  }
}

