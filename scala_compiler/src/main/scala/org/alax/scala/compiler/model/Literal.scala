package org.alax.scala.compiler.model


/**
 * The most basic language construct indicating some hardcoded value
 *
 * @param value
 */
abstract class Literal(val value: Object) extends Expression;
object literals {

  case class Boolean(override val value: java.lang.Boolean) extends Literal(value = value);


  case class Character(override val value: java.lang.Character) extends Literal(value = value);


  case class Integer(override val value: java.lang.Integer) extends Literal(value = value);


  case class Float(override val value: java.lang.Double) extends Literal(value = value);


  case class String(override val value: java.lang.String) extends Literal(value = value);

}