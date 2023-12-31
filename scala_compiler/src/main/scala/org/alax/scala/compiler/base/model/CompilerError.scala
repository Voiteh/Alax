package org.alax.scala.compiler.base.model

abstract class CompilerError(val message: String, val cause: Any  = null)

case class CompilationError(path: String, startIndex: Int, endIndex: Int, override val message: String, override val cause: Exception | Null = null)
  extends CompilerError(message = message, cause = cause)

case class CompilationErrors(override val message: String, errors: Seq[CompilationError]) extends CompilerError(message = message, cause = null)

case class CompilerBugError(override val cause: Any = null) extends CompilerError("You found a bug in a compiler!", cause);
