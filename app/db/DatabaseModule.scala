package db

import com.google.inject.AbstractModule

class DatabaseModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserContext]).asEagerSingleton()
    bind(classOf[ClassContext]).asEagerSingleton()

  }
}