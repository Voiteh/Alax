package org.alax.scala.compiler.transformer

import org.alax.scala.compiler
import org.alax.ast.model as ast

class AstTransformer {

  def transform(
                 valueDeclaration: ast.statements.declarations.Value,
                 imports: Seq[ast.statements.declarations.Import]
               ): compiler.model.Value.Declaration = {
    return compiler.model.Value.Declaration(
      name = valueDeclaration.name.value,
      `type` = compiler.model.Value.Type(
        id = compiler.model.base.Declaration.Type.Id(
          fullyQualifiedName = valueDeclaration.`type` match {
            case lowerCase: ast.partials.names.LowerCase => imports.find(`import` => `import`)
            case qualified: ast.partials.names.Qualified =>
          }
        )
      )
    )
  }


}
