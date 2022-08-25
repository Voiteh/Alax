package org.alax.model

object Value {

  /**
   * Value declaration
   *
   * @param name   - simple name of that declaration available to identifiy that value
   * @param `type` - type of value declaraiton 
   */
  case class Declaration(
                          override val name: base.Declaration.Name,
                          override val `type`: Value.Type //|Union.Type|Intersection.Type|Functional.Type...
                        )
    extends base.Declaration(name = name, `type` = `type`)

  /**
   * Value type, this is internal property of declaration! And not type declaration itself
   *
   * @param id
   */
  case class Type(var id: base.Declaration.Type.Id) extends
    base.Declaration.Type()
  
  
}
