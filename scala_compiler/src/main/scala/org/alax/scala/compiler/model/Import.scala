package org.alax.scala.compiler.model


case class Import(ancestor: String, member: String, alias: String | Null = null) extends Statement {

  val text: String = if (alias != null) then s"${ancestor}.$alias" else s"${ancestor}.$member";


  override def toString : String = text;




}
