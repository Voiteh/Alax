package org.alax.scala.compiler.model

import org.alax.scala.compiler.model
import scala.meta.Lit

/**
 * The most basic language construct indicating some hardcoded value
 *
 * @param value
 */
abstract class Literal(val value: Object) extends Expression;

object literals {

  case class Boolean(override val value: java.lang.Boolean) extends Literal(value = value) {

    override val scala: java.lang.String = Lit.Boolean(value).toString();

  }


  //FIXME probably value should be  UTF-16 char
  case class Character(override val value: java.lang.Character) extends Literal(value = value) {
    override val scala: java.lang.String = Lit.Char(value).toString();
  }


  case class Integer(override val value: java.lang.Long) extends Literal(value = value) {
    override val scala: java.lang.String = Lit.Long(value).toString();
  }


  case class Float(override val value: java.lang.Double) extends Literal(value = value) {
    override val scala: java.lang.String = Lit.Double(value).toString();
  }


  case class String(override val value: java.lang.String) extends Literal(value = value) {
    override val scala: java.lang.String = Lit.String(value).toString();
  }

}