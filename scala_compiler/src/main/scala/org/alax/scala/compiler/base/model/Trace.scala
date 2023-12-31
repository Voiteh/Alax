package org.alax.scala.compiler.base.model

class Trace(
             val unit: String,
             val lineNumber: Int,
             val startIndex: Int,
             val endIndex: Int
           ) {


  override def equals(obj: Any): Boolean = {
    obj match {
      case trace: Trace => trace.unit.equals(this.unit)
        && trace.lineNumber.equals(this.lineNumber)
        && trace.startIndex.equals(this.startIndex)
        && trace.endIndex.equals(this.endIndex)
      case _ => false;
    }
  }

}
