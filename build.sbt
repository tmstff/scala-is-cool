inThisBuild(
  List(
    version := "0.1",
    scalaVersion := "3.3.0"
  ))

val scalaTestVersion = "3.2.18"

lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test // Test framework: http://www.scalatest.org/
lazy val scalaTestWordSpec = "org.scalatest" %% "scalatest-wordspec" % scalaTestVersion % Test // Test style for scala test

libraryDependencies ++= List(
  scalaTest,
  scalaTestWordSpec
)

scalacOptions ++= Seq("-deprecation", "-feature")

//conflictWarning := ConflictWarning.default
conflictManager := ConflictManager.latestRevision