package test

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

class SparkApplicationTest extends AnyFunSuite with BeforeAndAfterAll with SparkSessionTestWrapper {


  test("Check if spark session is working") {
    assertResult("hello")(spark.sql("SELECT 'hello'").collect().head.get(0))
  }

  override def afterAll: Unit = {
    spark.close()
  }
}
