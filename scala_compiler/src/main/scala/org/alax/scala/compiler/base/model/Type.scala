package org.alax.scala.compiler.base.model

object Type {

  //FIXME add assert for Upper case words
  class Id(val value: String) {
    override def equals(obj: Any): Boolean = {
      return obj match {
        case id: Id => value == id.value
        case _ => false
      }

    }
    override def toString: String = value;
  }


  abstract class Reference extends ScalaMetaNode

}
