name := "sangria-akka-http-graphql"
version := "0.0.1-SNAPSHOT"

description := "An example GraphQL server written with Akka HTTP and Sangria."

scalaVersion in ThisBuild := "2.12.2"
scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.2.2",
  "org.sangria-graphql" %% "sangria-spray-json" % "1.0.0",
  "org.sangria-graphql" %% "sangria-akka-streams" % "1.0.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.4",
  "com.typesafe.akka" %% "akka-stream" % "2.5.4",
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",

  "org.reactivemongo" %% "reactivemongo" % "0.12.1",

  "ch.qos.logback" % "logback-classic" % "1.1.3",

  "com.google.inject" % "guice" % "4.1.0",
  "net.codingwell" %% "scala-guice" % "4.1.0",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
