package org.alax.ast

import org.alax.ast
import org.alax.ast.Module.Element
import org.alax.ast.base.{Expression, Node, ParseError, Statement}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.Value;

object Function {

  object Call {

    type Argument = Function.Call.Positional.Argument | Function.Call.Named.Argument;

    object Positional {
      case class Argument(
                           expression: Chain.Expression,
                           metadata: Metadata
                         ) extends ast.base.Argument(metadata = metadata)
    }

    object Named {
      case class Argument(
                           identifier: ast.Identifier.LowerCase,
                           expression: Chain.Expression,
                           metadata: Metadata
                         ) extends ast.base.Argument(metadata = metadata)
    }

    case class Expression(
                           functionReference: Function.Reference,
                           arguments: Seq[Function.Call.Argument],
                           metadata: Metadata = Metadata.unknown
                         ) extends ast.base.Expression(metadata = metadata)

    case class Statement(functionReference: Function.Reference,
                         arguments: Seq[Function.Call.Argument],
                         metadata: Metadata = Metadata.unknown) extends ast.base.Statement(metadata = metadata)
  }

  case class Reference(valueTypeIdentifier: Value.Type.Identifier | Null, functionId: Function.Identifier, metadata: Metadata = Metadata.unknown)
    extends ast.base.expressions.Reference(metadata = metadata);

  object Lambda {
    type Element = Chain.Expression | Value.Assignment.Expression | ParseError

    case class Body(element: Element, metadata: Metadata) extends ast.base.Node(metadata = metadata)
  }

  type Body = Block.Body | Lambda.Body;

  object Block {
    type Element = Chain.Expression | Value.Definition | Value.Declaration | Value.Assignment.Expression | ParseError

    case class Body(elements: Seq[Element], metadata: Metadata) extends ast.base.Node(metadata = metadata)
  }

  type Identifier = Identifier.LowerCase;

  object Pure {
    case class Definition(
                           returnTypeReference: Value.Type.Identifier,
                           identifier: Function.Identifier,
                           parameters: Seq[Function.Parameter],
                           metadata: Metadata
                         ) extends BaseDefinition(metadata)


  }

  object SideEffect {
    case class Definition(
                           identifier: Function.Identifier,
                           parameters: Seq[Function.Parameter],
                           metadata: Metadata
                         ) extends BaseDefinition(metadata)

    case class Body() {

    }
  }

  case class Parameter(
                        identifier: Identifier.LowerCase,
                        `type`: Value.Type.Identifier,
                        metadata: Metadata
                      ) extends Node(metadata) {

  }


  case class Declaration(
                          returnTypeReference: Value.Type.Identifier | Null,
                          identifier: Function.Identifier,
                          parameters: Seq[Function.Parameter], metadata: Metadata
                        ) extends BaseDeclaration(metadata) {

  }

}
