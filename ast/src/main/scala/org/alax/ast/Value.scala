package org.alax.ast

import org.alax.ast
import org.alax.ast.base.{Expression as BaseExpression, Statement as BaseStatement}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.Evaluable

object Value {

  case class Declaration(identifier: Evaluable.Identifier,
                         typeReference: Value.Type.Reference,
                         metadata: Metadata = Metadata.unknown
                        ) extends Evaluable.Declaration(identifier = identifier, metadata = metadata);

  case class Definition(
                         identifier: Evaluable.Identifier,
                         typeReference: Value.Type.Reference,
                         initialization: BaseExpression,
                         metadata: Metadata = Metadata.unknown
                       )
    extends Evaluable.Definition[BaseExpression](
      metadata = metadata,
      identifier = identifier,
      definable = initialization
    ) {

  }

  object Type {
    type Identifier = ast.Identifier.UpperCase;

    case class Reference(`package`: ast.Package.Reference | Null, identifier: Identifier, metadata: Metadata = Metadata.unknown) extends ast.base.expressions.Reference(metadata = metadata) {

      def text: String = `package` match {
        case reference: ast.Package.Reference => s"${reference.text}.${identifier.text}"
        case null => identifier.text
      }

    }
  }

  object Assignment {
    case class Expression(left: Evaluable.Reference, right: Chain.Expression, metadata: Metadata) extends BaseExpression(metadata = metadata)
  }


}
