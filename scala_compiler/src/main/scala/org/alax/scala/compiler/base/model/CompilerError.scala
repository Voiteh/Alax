package org.alax.scala.compiler.base.model

abstract class CompilerError(val message: String,val cause:Throwable|Null = null) extends Throwable

case class CompilationError(val path: String, val startIndex: Int, val endIndex: Int, override val message: String,override val cause:Exception|Null = null)
  extends CompilerError(message = message, cause = cause)

case class CompilerBugException(override val cause: Throwable | Null = null) extends CompilerError("You found a bug in a compiler!", cause);
