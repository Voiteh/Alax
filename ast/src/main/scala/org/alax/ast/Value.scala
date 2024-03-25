package org.alax.ast

import org.alax.ast
import org.alax.ast.base.{Expression as BaseExpression,  Statement as BaseStatement}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.Evaluable

object Value {

  case class Declaration(identifier: Evaluable.Identifier,
                         typeReference: Value.Type.Identifier,
                         metadata: Metadata = Metadata.unknown
                        ) extends Evaluable.Declaration(identifier = identifier, metadata = metadata);

  case class Definition(
                         identifier: Evaluable.Identifier,
                         typeReference: Value.Type.Identifier,
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


    case class Identifier(prefix: Seq[ast.Identifier] = Seq(), suffix: ast.Identifier.UpperCase, metadata: Metadata = Metadata.unknown) extends ast.base.Identifier(metadata = metadata) {

      def text: String = if prefix.isEmpty
      then suffix.text
      else s"${ast.base.Identifier.fold(prefix, ".")}.${suffix.text}"

    }
  }

  object Assignment {
    case class Expression(left: Evaluable.Reference, right: Chain.Expression, metadata: Metadata) extends BaseExpression(metadata = metadata)
  }


}
