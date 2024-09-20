package org.alax.scala.compiler.base.model

abstract class CompilerError(val trace: Trace, val cause: Any, val message:String){

}

case class CompilationError(override val trace:Trace = Trace.unknown, override val message: String , override val cause: Exception | Null = null)
  extends CompilerError(trace=trace,message=message,cause=cause)

case class CompilationErrors(override val trace:Trace = Trace.unknown, override val message: String, override val cause: Seq[CompilerError] = Seq())
  extends CompilerError(trace=trace,message=message,cause=cause)

case class CompilerBugError(
                             override val trace:Trace = Trace.unknown,
                             override val message: String= "You found a bug in a compiler!",
                             override val cause: Exception | Null = null
                           )
  extends CompilerError(trace=trace,message=message,cause=cause);
