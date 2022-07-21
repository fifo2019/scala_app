ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.8"

val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % "0.13.0")

lazy val root = (project in file("."))
  .settings(
    name := "scala_app",
    libraryDependencies ++= circe
  )
