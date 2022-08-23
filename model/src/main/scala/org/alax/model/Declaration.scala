package org.alax.model

object Declaration {
  object Type {
    class Id(fullyQualifiedName: String)
  }

  abstract class Type(id: Type.Id) {
    def visit[Result](`type`: Type): Result = `type`.visit(this);
  }


  case class ValueType(id: Type.Id)
    extends Type(id = id)

  case class UnionType(id: Type.Id)
    extends Type(id = id)

  case class IntersectionType(id: Type.Id)
    extends Type(id = id);

  case class FunctionalType(id: Type.Id)
    extends Type(id = id);


}

abstract class Declaration(name: String|Null)
  extends Statement

abstract class ValuableDeclaration(name: String, `type`: Declaration.Type)
  extends Declaration(name = name);

case class ValueDeclaration(name: String, `type`: Declaration.Type)
  extends ValuableDeclaration(
    name = name, `type` = `type`
  );

case class ValueConstructorDeclaration(name: String, `type`: Declaration.ValueType)
  extends ValuableDeclaration(
    name = name, `type` = `type`
  );

abstract class FunctionalDeclaration(name: String|Null)
  extends Declaration(name = name);

case class FunctionDeclaration(name: String)
  extends FunctionalDeclaration(name = name)
case class FunctionalConstructorDeclaration(name: String|Null)
  extends FunctionalDeclaration(name=name)

abstract class TypeDeclaration()

case class ClassDeclaration()
  extends TypeDeclaration()

case class InterfaceDeclaration()
  extends TypeDeclaration()

case class AnnotationDeclaration()
  extends TypeDeclaration()



