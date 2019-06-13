name := "spark-dev-sandbox"

version := "1.0"

scalaVersion := "2.12.6"

val sparkVersion = "2.4.3"
val sparkCSVVersion = "1.5.0"
val jacksonVersion = "2.9.8"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
//  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "org.apache.spark" %% "spark-avro" % "2.4.3",
  "org.apache.avro" % "trevni-core" % "1.9.0",
  "org.apache.avro" % "trevni-avro" % "1.9.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion

)
        