name := "cv"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-unchecked", "-deprecation")


libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.1"

libraryDependencies += "com.mortennobel" % "java-image-scaling" % "0.8.6"


//watchSources <+= baseDirectory { _ / "demo" / "examples.txt" }
