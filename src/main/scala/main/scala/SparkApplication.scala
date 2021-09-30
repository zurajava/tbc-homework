package main.scala

import main.scala.AppConfig.configuration
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{avg, col, count}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SparkApplication {

  def main(args: Array[String]): Unit = {
    var startDate: String = null;
    var endDate: String = null;

    if (args != null && args.size > 0) {
      startDate = if (validateDate(args(0))) args(0) else null;
      endDate = if (validateDate(args(1))) args(1) else null;
    }

    val spark = getSparkSession(configuration)

    val clientQuery = generateClientQuery(startDate, endDate)
    val clients = loadDataFromDatabase(spark, clientQuery)

    val transactionQuery = generateTransactionQuery(startDate, endDate)
    val transactions = loadDataFromDatabase(spark, transactionQuery)

    val currencies = loadDataFromDatabase(spark, "public.currency")

    /*
    Join Table
     */
    val joinedData = transactions.join(clients, transactions("iban") === clients("iban"), "left")
      .join(currencies, transactions("currency_id") === currencies("id"))
      .select(transactions("iban"), transactions("amount"), transactions("date"),
        clients("first_name"), clients("last_name"), clients("age"), clients("date") as "birthdate",
        currencies("ccy_from") as "ccy",
        (col("amount") * col("rate")) as "amount_gel");

    /*
    Add aggregated column transaction count per iban using window function and save in hive metastore
     */
    val window = Window.partitionBy("iban");
    val addTotalCount = joinedData.withColumn("count", count("iban").over(window));
    createTransactionTable(addTotalCount)

    /*
    Aggregate data and save csv file
     */
    val aggregatedData = joinedData.groupBy("iban", "first_name", "last_name")
      .agg(avg(col("amount_gel")).as("average"));
    generateCSV(aggregatedData, configuration.csvLocation)

    // import spark.sql
    // sql("SELECT * FROM  transaction").show()

  }

  def getSparkSession(configuration: Configuration): SparkSession = {
    val warehouseLocation = new File(configuration.hiveWarehouseLocation).getAbsolutePath
    SparkSession
      .builder()
      .master("local[1]")
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()
  }

  def generateCSV(data: DataFrame, location: String): Unit = {
    // data.collect().foreach(println)
    data.coalesce(1).write.format("csv").save(location)
  }

  def createTransactionTable(data: DataFrame): Unit = {
    //data.collect().foreach(println)
    data.coalesce(1).write.mode("overwrite").saveAsTable("transaction")
  }

  def loadDataFromDatabase(spark: SparkSession, query: String): DataFrame = {
    //println(query)
    spark.read
      .format("jdbc")
      .option("url", configuration.url)
      .option("user", configuration.userName)
      .option("password", configuration.password)
      .option("dbtable", query)
      .option("dateFormat", "yyyy-MM-dd")
      .load();
  }

  def validateDate(date: String): Boolean = {
    try {
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      LocalDate.parse(date, formatter)
      true
    } catch {
      case e: Exception => false
    }
  }

  def generateClientQuery(startDate: String, endDate: String) = {
    "(select * from public.client where 1=1 " +
      (if (startDate != null) " and date>='" + startDate + "'" else "") +
      (if (endDate != null) " and date<='" + endDate + "'" else "") +
      ") as tmp"
  }

  def generateTransactionQuery(startDate: String, endDate: String) = {
    "(select * from public.transaction where 1=1 " +
      (if (startDate != null) " and date>='" + startDate + "'" else "") +
      (if (endDate != null) " and date<='" + endDate + "'" else "") +
      ") as tmp"
  }
}
