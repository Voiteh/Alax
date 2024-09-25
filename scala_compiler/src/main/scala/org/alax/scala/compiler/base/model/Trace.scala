package org.alax.scala.compiler.base.model

case class Trace(
                  unit: String,
                  startLine: Int,
                  endLine: Int,
                  startIndex: Int,
                  endIndex: Int
                ) {


  override def equals(obj: Any): Boolean = {
    obj match {
      case trace: Trace => trace.unit.equals(this.unit)
        && trace.startLine.equals(this.startLine)
        && trace.endLine.equals(this.endLine)
        && trace.startIndex.equals(this.startIndex)
        && trace.endIndex.equals(this.endIndex)
      case _ => false;
    }
  }

  override def toString: String = org.alax.utilities.toString(this)
}

object Trace {
  val unknown :Trace= Trace(
    unit = "", startLine = -1, endLine = -1, startIndex = -1, endIndex = -1
  )
}