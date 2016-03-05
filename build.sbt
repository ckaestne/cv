name := "cv"

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation")


libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.4"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.1"

//watchSources <+= baseDirectory { _ / "demo" / "examples.txt" }
