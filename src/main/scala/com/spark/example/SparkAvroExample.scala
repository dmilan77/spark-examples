package com.spark.example

import java.io.{ByteArrayInputStream, DataInputStream}

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.to_json
import org.apache.spark.sql._
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

import scala.reflect.ClassTag


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


    val avroInputDF = spark.read.format("avro").load("/Users/milandas/Downloads/jsondata.avro")

    val mappedJson = avroInputDF.toJSON.map(x=>x)(Encoders.STRING)
    mappedJson.foreach(x=>processData(x))


  }

  def processData( in:String): Unit = {
    val generateTestData:GenericRecord = converToGenericRecord(in)
    println(generateTestData.getClass)
    println(generateTestData)
  }
  def converToGenericRecord( in:String): GenericRecord = {
    val input = new ByteArrayInputStream(in.getBytes())
    val din = new DataInputStream(input)
    val decoder = DecoderFactory.get().jsonDecoder(schema,din)
    val reader = new GenericDatumReader[Object](schema)
    val datum = reader.read(null,decoder).asInstanceOf[GenericRecord]
//    println(datum.getClass)
//    println(datum)
    datum
  }

}
