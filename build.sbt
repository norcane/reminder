
// Project details
name := "reminder"
description := "Say goodbye to forgotten TODOs in your code!"
version in Global := "0.2.0-SNAPSHOT"
organization in Global := "com.norcane"
licenses in Global += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage in Global := Some(url("https://github.com/norcane/reminder"))

// More info for Maven Central
developers in Global := List(
  Developer(
    id = "vaclav.svejcar",
    name = "Vaclav Svejcar",
    email = "vaclav.svejcar@gmail.com",
    url = url("https://github.com/vaclavsvejcar")
  )
)

scmInfo in Global := Some(
  ScmInfo(
    url("https://github.com/norcane/reminder"),
    "scm:git@github.com:norcane/reminder.git"
  )
)

// Bintray configuration
bintrayOrganization := Some("norcane")
bintrayRepository := "reminder"

scalaVersion := "2.12.9"
crossScalaVersions := Seq("2.11.12", "2.12.9")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros",
  "-unchecked",
  "-Ypartial-unification"
)