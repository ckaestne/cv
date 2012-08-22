name := "cv"

version := "0.1"

scalaVersion := "2.9.2"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1"

libraryDependencies += "commons-io" % "commons-io" % "2.1"

//watchSources <+= baseDirectory { _ / "demo" / "examples.txt" }