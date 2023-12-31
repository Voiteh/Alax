package org.alax.scala.compiler.base.model

/**
 * Defined language construct
 */
abstract class Definition(val declaration: Declaration, val meaning: Any) extends Statement

