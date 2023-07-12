package org.alax.scala.compiler.model

/**
 * Something representing state change of language constructs like values
 *
 * @param rightSide
 */
abstract class Assignment(val rightSide: Expression) extends Statement {

}