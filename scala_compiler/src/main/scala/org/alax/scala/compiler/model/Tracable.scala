package org.alax.scala.compiler.model

class Tracable[Transformed](
                             val trace: Trace,
                             val transformed: Transformed
                           ) {
  override def equals(obj: Any): Boolean = {
    return obj match {
      case other: Tracable[Transformed] => transformed.equals(other.transformed)
      case _ => false;
    }
  }
}
