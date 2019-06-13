package com.spark.example

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}


object HelloWorld {


    def main(args: Array[String]) {

      //Create a SparkContext to initialize Spark
      val conf = new SparkConf()
      conf.setMaster("local")
      conf.setAppName("Word Count")
      val sc = new SparkContext(conf)

      // Load the text into a Spark RDD, which is a distributed representation of each line of text
      val textFile = sc.textFile("/opt/git-workspace/github/dmilan77/spark-development/testfile/shakespeare.txt")

      //word count
      val counts = textFile.flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)

      counts.foreach(println)
      System.out.println("Total words: " + counts.count());
      counts.saveAsTextFile("/tmp/shakespeareWordCount")
    }

  }