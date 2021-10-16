name := "scala_test"

version := "0.1"

scalaVersion := "2.13.4"
//scalaVersion := "2.10.8"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",
  "com.typesafe.akka" %% "akka-actor" % "2.6.16",

  // swing
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.1",

  // skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.1.0",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-log4j12" % "1.7.32" % Test
  
  
) ++ Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion) ++
  Seq("org.scalatest" %% "scalatest" % "3.2.9" % "test")

val circeVersion = "0.14.1"
