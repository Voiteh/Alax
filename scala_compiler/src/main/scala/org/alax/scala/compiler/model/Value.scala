package org.alax.scala.compiler.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Declaration as BaseDeclaration, Type as BaseType}
import org.alax.scala.compiler.base.model.{Literal, Reference}

import scala.meta.{Decl, Defn, Pat, Term, Tree, Name as MName, Type as MType}

object Value {

  /**
   * Value declaration
   *
   * @param name   - simple name of that declaration available to identify that value
   * @param `type` - type of value declaration
   */
  case class Declaration(
                          override val name: BaseDeclaration.Name,
                          `type`: Value.Type.Reference //|Union.Type|Intersection.Type|Functional.Type...
                        )
    extends model.Declaration(name = name) {

    override val scala: Decl.Val = Decl.Val(
      mods = collection.immutable.List(),
      pats = collection.immutable.List(
        Term.Name(s"`${name}`")
      ),
      decltpe = `type`.scala
    )


  }


  /**
   * Value type, this is internal property of declaration/definition! And not type declaration itself
   *
   * @param id
   */


  object Type {
    case class Reference(var id: BaseType.Id) extends BaseType.Reference() {
      override def equals(obj: Any): Boolean = {
        return obj match {
          case value: Reference => id == value.id;
          case _ => false
        }
      }

      override def scala: MType = {
        return MType.Name.Initial(id.value)
      }
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
          Term.Name(declaration.name)
        )
      ),
      decltpe = Option(MType.Name.Initial(declaration.`type`.id.value)),

    )


  }


}
