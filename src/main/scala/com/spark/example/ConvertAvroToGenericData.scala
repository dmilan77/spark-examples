
package com.spark.example
import org.apache.avro.Schema
import org.apache.trevni.avro._
import org.apache.avro.util.RandomData
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericRecord}
import java.io.{ByteArrayInputStream, DataInputStream, FileInputStream}

import org.apache.avro.io.DecoderFactory


object ConvertAvroToGenericData {

  def main(args: Array[String]): Unit = {

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

        val input = new FileInputStream("/Users/milandas/Downloads/jsondata.avro")
        val din = new DataInputStream(input)
        val decoder = DecoderFactory.get().jsonDecoder(schema,din)
        val reader = new GenericDatumReader[Object](schema)
        val datum = reader.read(null,decoder)
        print(datum.getClass)
        print(datum)


  }
}
