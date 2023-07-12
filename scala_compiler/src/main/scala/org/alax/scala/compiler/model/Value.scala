package org.alax.scala.compiler.model

import org.alax.scala.compiler.model
import org.alax.scala.compiler.model.Declaration.{Name, `type`};

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
    extends model.Declaration(name = name, `type` = `type`)

  /**
   * Value type, this is internal property of declaration! And not type declaration itself
   *
   * @param id
   */
  case class Type(var id: `type`.Id) extends
    model.Declaration.Type()
  
  
}
