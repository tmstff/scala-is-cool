lazy val root = (project in file(".")).
  settings(
    name := "scala-demo",
    version := "1.0",
    scalaVersion := "2.11.5"
  )

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"