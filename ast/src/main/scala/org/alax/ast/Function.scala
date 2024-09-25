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

  object Return {
    type Type = Value.Type.Reference;
  }

  case class Definition(
                         returnTypeReference: Function.Return.Type,
                         identifier: ast.Evaluable.Identifier,
                         parameters: Seq[Routine.Declaration.Parameter],
                         body: Routine.Definition.Body,
                         metadata: Metadata = Metadata.unknown
                       ) extends ast.Routine.Definition(
    identifier = identifier,
    parameters = parameters,
    metadata = metadata,
    body = body
  )


  case class Declaration(
                          identifier: ast.Evaluable.Identifier,
                          returnTypeReference: Value.Type.Reference,
                          parameters: Seq[Routine.Declaration.Parameter] = Seq(),
                          metadata: Metadata = Metadata.unknown
                        ) extends ast.Routine.Declaration(identifier = identifier, parameters = parameters, metadata = metadata) {

  }

}
