
// Project details
name := "reminder"
description := "Say goodbye to forgotten TODOs in your code!"
Global / version := "0.3.0-SNAPSHOT"
Global / organization := "com.norcane"
Global / licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
Global / homepage := Some(url("https://github.com/norcane/reminder"))

// More info for Maven Central
Global / developers := List(
  Developer(
    id = "vaclav.svejcar",
    name = "Vaclav Svejcar",
    email = "vaclav.svejcar@gmail.com",
    url = url("https://github.com/vaclavsvejcar")
  )
)

Global / scmInfo := Some(
  ScmInfo(
    url("https://github.com/norcane/reminder"),
    "scm:git@github.com:norcane/reminder.git"
  )
)

// Bintray configuration
bintrayOrganization := Some("norcane")
bintrayRepository := "reminder"

scalaVersion := "2.12.10"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.1.1" % Test
)

Global / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros",
  "-unchecked"
)

// --- Scala Cross compile specific code ---

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 13 => Nil
    case _ => compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full) :: Nil
  }
}

Global / scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 13 => "-Ymacro-annotations" :: Nil
    case _ => Nil
  }
}
