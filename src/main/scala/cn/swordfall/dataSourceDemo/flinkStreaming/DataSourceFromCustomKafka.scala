package cn.swordfall.dataSourceDemo.flinkStreaming

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
  * @Author: Yang JianQiu
  * @Date: 2019/3/4 1:07
  */
class DataSourceFromCustomKafka {

  /**
    * 自定义的source, 如kafka
    */
  def CustomKafkaSource: Unit ={
    //2.指定kafka数据流的相关信息
    val kafkaCluster = "192.168.187.201:9092"
    val topic = "test"

    //3.创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //4.创建kafka数据流
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.187.201:9092")
    props.put("group.id", "kv_flink")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val kafkaConsumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema, props)
    val text = env.addSource(kafkaConsumer).setParallelism(4)

    //5.执行计算
    text.print()

    //6.触发运算
    env.execute("flink-kafka-source")
  }
}
object DataSourceFromCustomKafka{
  def main(args: Array[String]): Unit = {

  }
}