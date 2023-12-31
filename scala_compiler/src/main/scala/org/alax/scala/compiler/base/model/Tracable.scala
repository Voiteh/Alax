package org.alax.scala.compiler.base.model

import scala.reflect.ClassTag


class Tracable[Transformed](
                             val trace: Trace,
                             val transformed: Transformed
                           ) {
  override def equals(obj: Any): Boolean = {
    return obj match {
      case other: Tracable[_] => transformed.equals(other.transformed)
      case _ => false;
    }
  }
}
