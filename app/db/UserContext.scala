package db

import models.User
import io.getquill._
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import play.api.{Configuration, Logger}
import javax.inject.{Inject, Singleton}

@Singleton
class UserContext @Inject()(config: Configuration) {

  private val logger = Logger(this.getClass)
  private val hikariConfig = new HikariConfig()
  hikariConfig.setDriverClassName(config.get[String]("db.default.driver"))
  hikariConfig.setJdbcUrl(config.get[String]("db.default.url"))
  hikariConfig.setUsername(config.get[String]("db.default.username"))
  hikariConfig.setPassword(config.get[String]("db.default.password"))
  hikariConfig.setMaximumPoolSize(10)

  private val dataSource = new HikariDataSource(hikariConfig)
  val ctx = new MysqlJdbcContext(SnakeCase, dataSource)

  import ctx._

  // DB CONNECTION TEST
  try {
    // Get a raw connection from the pool
    val connection = dataSource.getConnection()

    // Create a simple statement
    val statement = connection.createStatement()

    //Execute the "Ping"
    statement.executeQuery("SELECT 1")


    statement.close()
    connection.close()

    logger.info("DATABASE CONNECTED SUCCESSFULLY: MySQL is ready!")
    ()


  } catch {
    case e: Exception =>
      logger.error("DATABASE CONNECTION FAILED!")
      logger.error(s"Reason: ${e.getMessage}")


      ()
  }



}