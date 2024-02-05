package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.Partial
import org.alax.ast.partial.Identifier
object Package {

  case class Declaration(name: Name,
                         metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(name: Name,
                        body: Body,
                        metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  case class Body(elements: Seq[Element], override val metadata: Metadata)
    extends base.Partial.Scope(metadata = metadata)

  case class Name(source:String,metadata: Metadata) extends Partial.Identifier(metadata=metadata){
    assert(text().matches("[a-z]*"))
    override def text(): String = source
  }
  type Element = Value.Definition |  ParseError
}
