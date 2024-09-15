package org.alax.scala.compiler.base.model

abstract class CompilerError(val message: String, val cause: Any = null)

case class CompilationError(path: String, override val message: String, override val cause: Exception | Null = null, startIndex: Int = -1, endIndex: Int = -1)
  extends CompilerError(message = message, cause = cause)

case class CompilationErrors(override val message: String, errors: Seq[CompilerError] = Seq()) extends CompilerError(message = message, cause = null)

case class CompilerBugError(override val cause: Any = null) extends CompilerError("You found a bug in a compiler!", cause);
