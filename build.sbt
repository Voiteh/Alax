import sbt.Keys.libraryDependencies

lazy val commonSettings = Seq(
  scalaVersion := "3.1.3"
)
lazy val alax = project.in(file("."))
  .settings(
    commonSettings,
    name := "Alax",
    description := "Alax programming language",
    scalaVersion := "3.1.3",
  )
  .aggregate(
    syntax,
    model,
    syntax_model_transformer,
    parser,
    scala_compiler
  )

lazy val syntax = project.in(file("syntax"))
  .enablePlugins(Antlr4Plugin)
  .settings(
    commonSettings,
    version := "0.1.0",
    Antlr4 / antlr4GenVisitor := true,
    Antlr4 / antlr4GenListener := false,
    Antlr4 / antlr4PackageName := Option("org.alax.syntax"),
  )
lazy val model = project.in(file("model"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
    )
  )
lazy val parser = project.in(file("parser"))
  .dependsOn(syntax)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
    )
  )
lazy val scala_compiler = project.in(file("scala_compiler"))
  .settings(
    commonSettings
  )


lazy val syntax_model_transformer = project.in(file("syntax_model_transformer"))
  .settings(commonSettings)