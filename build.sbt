name := "cv"

version := "0.1"

scalaVersion := "2.13.4"

scalacOptions ++= Seq("-unchecked", "-deprecation")


libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.1"

libraryDependencies += "com.mortennobel" % "java-image-scaling" % "0.8.6"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value


//watchSources <+= baseDirectory { _ / "demo" / "examples.txt" }
