package org.alax.scala.compiler.model

class CompilerBugException(cause: Throwable | Null = null) extends Exception("You found a bug in a compiler!", cause);
