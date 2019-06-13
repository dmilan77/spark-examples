package com.spark.example

import java.io.{ByteArrayInputStream, DataInputStream}

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.io.DecoderFactory
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.to_json



object SparkAvroExample {
  val schema = new Schema.Parser().parse(
    """{
      |   "type":"record",
      |   "name":"myschema",
      |   "fields":[
      |      {
      |         "name":"id",
      |         "type":"int",
      |         "doc":"Type inferred from '1'"
      |      },
      |      {
      |         "name":"name",
      |         "type":"string",
      |         "doc":"Type inferred from '\"A green door\"'"
      |      },
      |      {
      |         "name":"price",
      |         "type":"double",
      |         "doc":"Type inferred from '12.5'"
      |      },
      |      {
      |         "name":"tags",
      |         "type":{
      |            "type":"array",
      |            "items":"string"
      |         },
      |         "doc":"Type inferred from '[\"home\",\"green\"]'"
      |      }
      |   ]
      |}""".stripMargin)

  def main(args: Array[String]): Unit = {
//    import spark.implicits._
  val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("SparkAvroExample")
    val sc = new SparkContext(conf)

    val spark: SparkSession = {
      SparkSession
        .builder()
        .master("local")
        .appName("SparkAvroExample")
        .getOrCreate()
    }

    val avroInputDF = spark.read
      .format("avro")
      .load("/Users/milandas/Downloads/jsondata.avro")


    val mappedJson = avroInputDF.toJSON
//    mappedJson.collect().foreach(converToGenericRecord)
    mappedJson.collect().foreach(converToGenericRecord)
//    mappedJson.map(converToGenericRecord).foreach()


  }

  def converToGenericRecord( in:String): Object = {
    val input = new ByteArrayInputStream(in.getBytes())
    val din = new DataInputStream(input)
    val decoder = DecoderFactory.get().jsonDecoder(schema,din)
    val reader = new GenericDatumReader[Object](schema)
    val datum = reader.read(null,decoder)
    println(datum.getClass)
    println(datum)
    return datum
  }

}
