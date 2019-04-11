val pname = "scala_test"
val pversion = "0.1.0"

name := pname

version := pversion

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.typesafe.akka" %% "akka-actor" % "2.5.8"
)

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.1.0"

assemblyJarName := { s"$pname-$pversion.jar" }
