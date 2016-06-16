import sbt.Keys._
import de.johoop.jacoco4sbt._
import JacocoPlugin._

organization := "com.wallace.demo"

name := "rest-sever-demo"

version := "0.1"

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

parallelExecution in ThisBuild := false

parallelExecution  in test := false

jacoco.settings

parallelExecution in jacoco.Config := false

testOptions in jacoco.Config += Tests.Argument("-h","target/html-test-report")

testOptions in jacoco.Config += Tests.Argument("-o")

testOptions in accTest += Tests.Argument("-h","target/accept-test-report")

testOptions in accTest += Tests.Argument("-o")

testOptions in unitTest += Tests.Argument("-h","target/unit-test-report")

testOptions in unitTest += Tests.Argument("-o")

testOptions in systemTest += Tests.Argument("-h","target/system-test-report")

testOptions in systemTest += Tests.Argument("-o")


libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.1"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

libraryDependencies ++= {
  val akkaV = "2.3.5"
  val sprayV = "1.3.2"
  Seq(
    "org.pegdown" % "pegdown" % "1.0.2" % "test",
    "io.spray" %% "spray-servlet" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-httpx" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.json4s" %% "json4s-native" % "3.2.4",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
  )
}

// delete all svn directories
postProcess in webapp := {
  webappDir => removeAllSvn(webappDir)
}

// use jetty container
jetty(port=8080)
