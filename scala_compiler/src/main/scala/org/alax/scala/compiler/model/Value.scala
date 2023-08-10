package org.alax.scala.compiler.model

import org.alax.scala.compiler.model
import org.alax.scala.compiler.model.Declaration.{Name, Type};
import scala.meta.{Decl, Name as MName, Type as MType, Term}

object Value {

  /**
   * Value declaration
   *
   * @param name   - simple name of that declaration available to identifiy that value
   * @param `type` - type of value declaraiton
   */
  case class Declaration(
                          override val name: Name,
                          override val `type`: Value.Type //|Union.Type|Intersection.Type|Functional.Type...
                        )
    extends model.Declaration(name = name, `type` = `type`) {

    override val scala: String = Decl.Val(
      mods = collection.immutable.List(),
      pats = collection.immutable.List(
        Term.Name(name)
      ),
      decltpe = MType.Name(`type`.id.name)
    ).toString()

  }

  /**
   * Value type, this is internal property of declaration! And not type declaration itself
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


}
