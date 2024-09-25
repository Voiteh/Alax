package org.alax.ast

import org.alax.ast
import org.alax.ast.Module.Element
import org.alax.ast.Value
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.{Declaration as BaseDeclaration, Definition as BaseDefinition}
import org.alax.ast.base.{Expression, Node, ParseError, Statement};

object Procedure {


  case class Definition(
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
                          parameters: Seq[Routine.Declaration.Parameter] = Seq(),
                          metadata: Metadata = Metadata.unknown
                        ) extends ast.Routine.Declaration(identifier = identifier, parameters = parameters, metadata = metadata) {

  }


}
