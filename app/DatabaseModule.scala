import javax.inject.{Inject, Singleton}

import com.google.inject.AbstractModule
import play.api.Configuration
import play.api.inject.ApplicationLifecycle
import services.Database

@Singleton
final class DatabaseModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DatabaseInitializer]).to(classOf[DatabaseInitializerImpl]).asEagerSingleton()
  }
}

trait DatabaseInitializer {}

class DatabaseInitializerImpl @Inject()(configuration: Configuration, lifecycle: ApplicationLifecycle)
  extends DatabaseInitializer {

  Database.initialize(configuration, lifecycle)
}
