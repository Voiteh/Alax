lazy val commonSettings = Seq(
  scalaVersion := "3.1.3"
)

lazy val alax = project.in(file("."))
  .settings(
    commonSettings,
    name := "Alax",
    description := "Alax programming language",
    scalaVersion := "3.1.3"
  )
  .aggregate(
    model,
    parser,
    scala_compiler
  )

lazy val model = project.in(file("model"))
  .settings(
    commonSettings
  )

lazy val parser = project.in(file("parser"))
  .settings(
    commonSettings
  )
lazy val scala_compiler = project.in(file("scala_compiler"))
  .settings(
    commonSettings
  )