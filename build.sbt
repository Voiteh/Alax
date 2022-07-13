lazy val alax = project.in(file("."))
  .aggregate(
    ast,
    parser,
    scala_compiler
  )

lazy val ast = project
lazy val parser = project
lazy val scala_compiler = project