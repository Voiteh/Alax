package org.alax.scala.compiler.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.Literal

import scala.meta.{Lit, Term}


object Literals {

  case class Boolean(override val value: java.lang.Boolean) extends Literal(value = value) {

    override val scala: Lit = Lit.Boolean(value);

  }


  //FIXME probably value should be  UTF-16 char
  case class Character(override val value: java.lang.Character) extends Literal(value = value) {
    override val scala: Lit = Lit.Char(value);
  }


  case class Integer(override val value: java.lang.Long) extends Literal(value = value) {
    override val scala: Lit = Lit.Long(value);
  }


  case class Float(override val value: java.lang.Double) extends Literal(value = value) {
    override val scala: Lit = Lit.Double(value);
  }


  case class String(override val value: java.lang.String) extends Literal(value = value) {
    override val scala: Lit = Lit.String(value);
  }

}