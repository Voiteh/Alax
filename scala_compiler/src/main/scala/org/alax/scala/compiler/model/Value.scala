package org.alax.scala.compiler.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Declaration as BaseDeclaration, Type as BaseType}
import org.alax.scala.compiler.base.model.{Literal, Reference}

import scala.meta.{Decl, Defn, Pat, Term, Tree, Name as MName, Type as MType}

object Value {


  case class Reference(`package`: Package.Reference | Null, identifier: Value.Declaration.Identifier) extends model.Reference {
    override val scala: Term.Name | Term.Select =
      if `package` == null then Term.Name(identifier)
      else Term.Select(qual = `package`.scala, name = Term.Name(identifier))

  }

  /**
   * Value declaration
   *
   * @param identifier   - simple name of that declaration available to identify that value
   * @param `type` - type of value declaration
   */
  case class Declaration(
                          override val identifier: Declaration.Identifier,
                          `type`: Value.Type.Reference //|Union.Type|Intersection.Type|Functional.Type...
                        )
    extends model.Declaration(identifier = identifier) {

    override val scala: Decl.Val = Decl.Val(
      mods = collection.immutable.List(),
      pats = collection.immutable.List(
        Term.Name(identifier)
      ),
      decltpe = `type`.scala
    )


  }

  object Declaration{
    type Identifier = BaseDeclaration.Identifier;
  }


  /**
   * Value type, this is internal property of declaration/definition! And not type declaration itself
   *
   * @param id
   */


  object Type {
    case class Reference(id: BaseType.Id) extends BaseType.Reference() {
      override def equals(obj: Any): Boolean = {
        return obj match {
          case value: Reference => id == value.id;
          case _ => false
        }
      }

      override def scala: MType = MType.Name.Initial(id.value)
    }
  }


  case class Definition(override val declaration: Declaration,
                        override val meaning: Literal | Reference
                       ) extends model.Definition(declaration = declaration, meaning = meaning) {

    override def scala: Defn.Val = Defn.Val(
      rhs = meaning.scala,
      mods = collection.immutable.List(),
      pats = collection.immutable.List(
        Pat.Var(
          Term.Name(declaration.identifier)
        )
      ),
      decltpe = Option(MType.Name(declaration.`type`.id.value)),

    )


  }


}
