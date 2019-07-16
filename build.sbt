name := "untitled7"
 
version := "1.0" 
      
lazy val `untitled7` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "0.16.2"
)

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo-play-json" % "0.12.0"
)

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      