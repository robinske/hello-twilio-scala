scalaVersion := "2.12.3"

name := "hello-twilio"

libraryDependencies ++= {
  Seq(
    "com.twilio.sdk" % "twilio" % "7.15.5",
    "com.typesafe"   % "config" % "1.3.2"
  )
}
