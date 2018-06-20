name := "reminder"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.6",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-deprecation",
  "-language:experimental.macros",
  "-language:implicitConversions"
)