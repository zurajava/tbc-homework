package main.scala

import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus.{stringValueReader, toFicusConfig}

/**
 *
 * @param config
 */
class Configuration(config: Config) {
  val url: String = config.as[String]("database.url")
  val userName: String = config.as[String]("database.username")
  val password: String = config.as[String]("database.password")

  val csvLocation: String = config.as[String]("destination.csvLocation")
  val hiveWarehouseLocation: String = config.as[String]("destination.hiveWarehouseLocation")

}
