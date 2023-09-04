import sbt.Keys.libraryDependencies

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
    ast,
    parser,
    scala_compiler
  )

lazy val ast = project.in(file("ast"))
  .enablePlugins(Antlr4Plugin)
  .settings(
    commonSettings,
    version := "0.1.0",
    Antlr4 / antlr4GenVisitor := true,
    Antlr4 / antlr4GenListener := false,
    Antlr4 / antlr4PackageName := Option("org.alax.ast"),
  )
lazy val scala_compiler = project.in(file("scala_compiler"))
  .dependsOn(ast)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
      ("org.scalameta" %% "scalameta" % "4.8.6").cross(CrossVersion.for3Use2_13),
      ("org.scalatest" %% "scalatest-wordspec" % "3.2.16" % "test"),
      ("org.scalatest" %% "scalatest" % "3.2.16" % "test"),
    )
  )
lazy val parser = project.in(file("parser"))
  .dependsOn(ast)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
      ("org.scalatest" %% "scalatest-wordspec" % "3.2.16" % "test"),
      ("org.scalatest" %% "scalatest" % "3.2.16" % "test"),
    )
  )

