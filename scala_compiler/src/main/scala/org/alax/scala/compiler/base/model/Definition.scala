package org.alax.scala.compiler.base.model

/**
 * Declaration with initialization
 */
abstract class Definition(val declaration: Declaration, val initialization: Expression) extends Statement