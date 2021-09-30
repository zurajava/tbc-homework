package main.scala

import com.typesafe.config.ConfigFactory

/**
 * Global configuration
 */
object AppConfig {
  private val config = ConfigFactory.load
  val configuration: Configuration = new Configuration(config)
}
