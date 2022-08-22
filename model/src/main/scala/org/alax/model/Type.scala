package org.alax.model

object Type {
  class Id(fullyQualifiedName: String)
}

abstract class Type(id: Type.Id) {
  def visit[Result](`type`: Type): Result = `type`.visit(this);
}


case class ValueType(id: Type.Id) extends Type(id = id)

case class UnionType(id: Type.Id) extends Type(id = id)

case class IntersectionType(id: Type.Id) extends Type(id = id);

case class FunctionalType(id: Type.Id) extends Type(id = id);



