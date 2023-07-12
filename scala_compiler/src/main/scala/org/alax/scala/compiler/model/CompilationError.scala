package org.alax.scala.compiler.model

class CompilationError(val path: String, val startIndex: Int, val endIndex: Int, val message: String) extends Throwable
