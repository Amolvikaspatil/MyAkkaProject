ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "scala_test"
  )

// In build.sbt, add these Akka dependencies
// In build.sbt, add these Akka dependencies
name := "MyAkkaProject"

version := "0.1"

scalaVersion := "2.12.15" // Or whatever Scala version you're using

// Add Akka dependencies
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.8.8"  // Replace with the latest version
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.8.8" // For testing (optional)
