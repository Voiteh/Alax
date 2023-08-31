package org.alax.scala.compiler.model


/**
 * Declaration with initialization
 */
abstract class Definition(val name: Declaration.Name | Null, val `type`: Declaration.Type,val expression: Expression) extends Statement