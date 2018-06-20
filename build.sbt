name := "reminder"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"
crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-deprecation",
  "-language:experimental.macros",
  "-language:implicitConversions"
)