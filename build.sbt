lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion    = "2.6.14"

fork := true

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.github.makssobolevs",
      scalaVersion    := "2.13.5"
    )),
    name := "Akka Open Telemetry Test",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",

      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test,
    ),
    javaAgents += "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % "1.9.2" % "runtime",
    run / javaOptions ++= Seq(
      "-Dotel.resource.attributes=service.name=test",
      "-Dotel.traces.exporter=logging",
      "-Dotel.traces.sampler=always_on",
    )
  )
  .enablePlugins(JavaAgent)


