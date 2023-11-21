package org.alax.scala.compiler.base.model

object Type {

  class Id(val value: String) {
    override def equals(obj: Any): Boolean = {
      return obj match {
        case id: Id => value == id.value
        case _ => false
      }
    }
  }


  abstract class Reference extends ScalaMetaNode

}
