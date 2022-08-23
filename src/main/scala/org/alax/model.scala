package org.alax



object model {

  abstract class Statement {}


  object Return {
    case class Statement() extends model.Statement;
  }

  object Assignment {
    trait Target;

    case class Statement(val leftSide: Target,
                         val rightSide: Expression)
      extends model.Statement {

    }
  }

  abstract class Expression

  case object Invocation {
    case class Expression() extends model.Expression
  }


  object Declaration {

    type Name = String;

    abstract class Type() {
    }

    abstract class Access(fullyQualifiedName: String) {

    }

    object Reference {


    }

    abstract class Reference(val access: Access | Null, val name: Declaration.Name) {

    }

  }

  object This {
    case class Access(fullyQualifiedName: String) extends model.Declaration.Access(fullyQualifiedName = fullyQualifiedName);
  }

  object Super {
    case class Access(fullyQualifiedName: String) extends model.Declaration.Access(fullyQualifiedName = fullyQualifiedName);
  }


  abstract class Declaration(val name: Declaration.Name | Null)
    extends Statement


  object Valuable {
    abstract class Declaration(override val name: Declaration.Name,
                               val `type`: Declaration.Type)
      extends model.Declaration(name = name)


    object Constructor {
      case class Declaration(override val name: model.Declaration.Name, override val `type`: model.Valuable.Type)
        extends Valuable.Declaration(
          name = name, `type` = `type`
        );

    }

    case class Type(val id: model.Type.Id) extends model.Declaration.Type;

    case class Reference() extends Assignment.Target {

    }
  }

  object Functional {
    case class Type(val `return`: model.Type.Id | Null,
                    val parameters: Seq[model.Type.Id])
      extends model.Declaration.Type;

    abstract class Declaration(override val name: Declaration.Name | Null, `type`: Type)
      extends model.Declaration(name = name);


    object Constructor {
      case class Declaration(override val name: model.Declaration.Name | Null, `type`: Type)
        extends Functional.Declaration(
          name = name, `type` = `type`
        );
    }

    case class Reference() extends Assignment.Target {

    }
  }

  object Value {
    case class Declaration(override val name: model.Declaration.Name, override val `type`: model.Declaration.Type)
      extends Valuable.Declaration(
        name = name, `type` = `type`
      ) with Assignment.Target;
  }

  object Union {
    case class Type(val ids: Seq[model.Type.Id]) extends model.Declaration.Type;
  }

  object Intersection {
    case class Type(val ids: Seq[model.Type.Id]) extends model.Declaration.Type;
  }

  object Function {
    case class Declaration(override val name: model.Declaration.Name, `type`: model.Functional.Type)
      extends model.Functional.Declaration(
        name = name, `type` = `type`
      );
  }

  object Type {
    class Id(fullyQualifiedName: String) {
      val lastSegment: String = fullyQualifiedName.split(".").last;
    }

    abstract class Declaration(id: Type.Id) extends model.Declaration(id.lastSegment) {

    }

    case class Access(fullyQualifiedName: String) extends model.Declaration.Access(fullyQualifiedName = fullyQualifiedName);
  }


  object Class {
    case class Declaration(id: model.Type.Id) extends Type.Declaration(id = id) {

    }
  }


  object Interface {
    case class Declaration(id: model.Type.Id) extends Type.Declaration(id = id) {

    }
  }


  object Annotation {
    case class Declaration(id: model.Type.Id) extends Type.Declaration(id = id) {

    }
  }


}

