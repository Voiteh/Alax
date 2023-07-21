package org.alax.scala.compiler.model


case class Import(`package`: String, member: String, alias: String | Null = null) extends Statement {

  val text: String = if (alias != null) then s"${`package`}.$alias" else s"${`package`}.$member";


  override def toString() : String = text;

}
