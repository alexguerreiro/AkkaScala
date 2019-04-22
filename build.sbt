name := "AkkaScala"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.22"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.22" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.22"
libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.11"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22"
