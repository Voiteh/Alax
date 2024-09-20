package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.{Expression, Node, ParseError};

object Routine {


  object Call {

    type Argument = Routine.Call.Positional.Argument | Routine.Call.Named.Argument;

    object Positional {
      case class Argument(
                           expression: base.Expression,
                           metadata: Metadata = Metadata.unknown
                         ) extends ast.base.Argument(metadata = metadata)
    }

    object Named {
      case class Argument(
                           identifier: ast.Identifier.LowerCase,
                           expression: base.Expression,
                           metadata: Metadata = Metadata.unknown
                         ) extends ast.base.Argument(metadata = metadata)
    }

    case class Expression(
                           routineReference: Evaluable.Reference,
                           arguments: Seq[Routine.Call.Argument] = Seq(),
                           metadata: Metadata = Metadata.unknown
                         ) extends ast.base.Expression(metadata = metadata)

  }


  abstract class Definition(
                             identifier: ast.Evaluable.Identifier,
                             parameters: Seq[Routine.Declaration.Parameter],
                             body: Routine.Definition.Body,
                             metadata: Metadata = Metadata.unknown
                           ) extends ast.Evaluable.Definition[Routine.Definition.Body](
    metadata = metadata,
    identifier = identifier,
    definable = body
  )

  object Definition {
    type Body = Block.Body | Lambda.Body;

    object Lambda {
      type Element = Chain.Expression
        | Evaluable.Reference
        | Routine.Call.Expression
        | ParseError

      case class Body(
                       element: Element,
                       metadata: Metadata = Metadata.unknown
                     ) extends ast.base.Node(metadata = metadata)


    }


    object Block {
      type Element = Chain.Expression
        | Value.Declaration
        | Value.Definition
        | Value.Assignment.Expression
        | Evaluable.Reference
        | Routine.Call.Expression
        | ParseError

      case class Body(
                       elements: Seq[Element],
                       metadata: Metadata = Metadata.unknown
                     ) extends ast.base.Node(metadata = metadata)
    }
  }

  //TODO rename to Function in distinction to Procedure
  object Pure {


  }

  //TODO rename to Procedure in distinction to Function
  object SideEffect {
    case class Definition(
                           identifier: ast.Evaluable.Identifier,
                           parameters: Seq[Routine.Declaration.Parameter],
                           body: Routine.Definition.Body,
                           metadata: Metadata = Metadata.unknown
                         ) extends ast.Evaluable.Definition[Routine.Definition.Body](
      identifier = identifier,
      metadata = metadata,
      definable = body
    )

    case class Body() {

    }
  }


  object Declaration {
    case class Parameter(
                          identifier: Identifier.LowerCase,
                          `type`: Value.Type.Reference,
                          expression: Chain.Expression | Null = null,
                          metadata: Metadata = Metadata.unknown
                        ) extends Node(metadata) {

    }
  }

  abstract class Declaration(
                          identifier: ast.Evaluable.Identifier,
                          parameters: Seq[Routine.Declaration.Parameter] = Seq(),
                          metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata) {

  }

}
