package kafkaspark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
object readkafka {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("KafkaToJson").master("local[*]").getOrCreate()

    // Define the Kafka parameters
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "ip-172-31-14-3.eu-west-2.compute.internal:9092",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "group.id" -> "group1",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // Define the Kafka topic to subscribe to
    val topic = "arrivaldata1"

    // Define the schema for the JSON messages
    val schema = StructType(Seq(
      StructField("id", StringType, nullable = true),
      StructField("stationName", StringType, nullable = true),
      StructField("lineName", StringType, nullable = true),
      StructField("towards", StringType, nullable = true),
      StructField("expectedArrival", StringType, nullable = true),
      StructField("vehicleId", StringType, nullable = true),
      StructField("platformName", StringType, nullable = true),
      StructField("direction", StringType, nullable = true),
      StructField("destinationName", StringType, nullable = true),
      StructField("timestamp", StringType, nullable = true),
      StructField("timeToStation", StringType, nullable = true),
      StructField("currentLocation", StringType, nullable = true),
      StructField("timeToLive", StringType, nullable = true)
    ))

    // Read the JSON messages from Kafka as a DataFrame
    val df = spark.readStream.format("kafka").option("kafka.bootstrap.servers", " ip-172-31-8-235.eu-west-2.compute.internal:9092,ip-172-31-14-3.eu-west-2.compute.internal:9092").option("subscribe", topic).option("startingOffsets", "latest").load().select(from_json(col("value").cast("string"), schema).as("data")).selectExpr("data.*")
    // Write the DataFrame as CSV files to HDFS
    df.writeStream.format("csv").option("checkpointLocation", "/tmp/jenkins/kafka/trainarrival/checkpoint").option("path", "/tmp/jenkins/kafka/trainarrival/data").start().awaitTermination()
  }

}
