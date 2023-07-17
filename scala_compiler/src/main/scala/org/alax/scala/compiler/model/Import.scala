package org.alax.scala.compiler.model



case class Import(`package`: String, member: String, alias: String|Null = null) extends Statement {

}
