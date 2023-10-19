package org.alax.scala.compiler.model

import org.alax.scala.compiler.model
import org.alax.scala.compiler.model.Declaration.{Name, Type}

import scala.meta.Defn
import scala.meta.{Decl, Term, Pat, Name as MName, Type as MType}

object Value {

  /**
   * Value declaration
   *
   * @param name   - simple name of that declaration available to identifiy that value
   * @param `type` - type of value declaration
   */
  case class Declaration(
                          override val name: Name,
                          override val `type`: Value.Type //|Union.Type|Intersection.Type|Functional.Type...
                        )
    extends model.Declaration(name = name, `type` = `type`) {

    override val scala: Decl.Val = Decl.Val(
      mods = collection.immutable.List(),
      pats = collection.immutable.List(
        Term.Name(name)
      ),
      decltpe = MType.Name.Initial(`type`.id.value)
    )


  }

  /**
   * Value type, this is internal property of declaration/definition! And not type declaration itself
   *
   * @param id
   */
  case class Type(var id: model.Declaration.Type.Id) extends
    model.Declaration.Type() {
    override def equals(obj: Any): Boolean = {
      return obj match {
        case value: Type => id == value.id;
        case _ => false
      }
    }
  }

  case class Definition(override val declaration: Declaration,
                        override val initialization: Literal | Reference
                       ) extends model.Definition(declaration = declaration, initialization = initialization) {

    override def scala: Defn.Val = Defn.Val(
      rhs = initialization.scala,
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
