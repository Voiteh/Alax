package org.alax.ast

import org.alax.ast.base.expressions.Literal
import org.alax.ast.base.Node.Metadata

object Literals {
  case class Boolean(value: java.lang.Boolean, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

  case class Character(value: java.lang.Character, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

  case class Integer(value: java.lang.Integer, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

  case class Float(value: java.lang.Double, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

  case class String(value: java.lang.String, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);
}
