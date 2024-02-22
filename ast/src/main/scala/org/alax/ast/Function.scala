package org.alax.ast

import org.alax.ast.Module.Element
import org.alax.ast.base.{Expression, Node, ParseError, Partial, Statement}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.Partial.Type.Reference as BaseReference
import org.alax.ast.partial.Identifier
import org.alax.ast.partial.Identifier.LowerCase
import org.alax.ast.Value;

object Function {

  case class Reference(id: Identifier.LowerCase | Identifier.Qualified, override val metadata: Metadata = Metadata.unknown)
    extends BaseReference(metadata = metadata);

  object Lambda {
    type Element = Chain.Expression | Value.Assignment | ParseError

    case class Body(element: Element, override val metadata: Metadata) extends base.Partial.Scope(metadata = metadata)
  }

  object Block {
    type Element = Chain.Expression | Value.Definition | Value.Declaration | Value.Assignment | Return.Statement | ParseError

    case class Body(elements: Seq[Element], override val metadata: Metadata) extends base.Partial.Scope(metadata = metadata)
  }

  object Pure {
    case class Definition(
                           returnTypeReference: Value.Type.Identifier,
                           identifier: LowerCase,
                           parameters: Parameters,
                           metadata: Metadata
                         ) extends BaseDefinition(metadata)


  }

  object SideEffect {
    case class Definition(
                           identifier: LowerCase,
                           parameters: Parameters,
                           metadata: Metadata
                         ) extends BaseDefinition(metadata)

    case class Body() {

    }
  }

  case class Parameter(identifier: LowerCase, `type`: Value.Type.Identifier, metadata: Metadata) extends Node(metadata) {

  }

  case class Parameters(parameters: Seq[Parameter], metadata: Metadata) extends Node(metadata) {

  }

  case class Declaration(returnTypeReference: Value.Type.Identifier | Null, identifier: LowerCase, parameters: Parameters, metadata: Metadata) extends BaseDeclaration(metadata) {

  }

}
