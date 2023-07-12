package org.alax.scala.compiler.transformer

import org.alax.scala.compiler
import org.alax.ast.model as ast
import org.alax.ast.model.partials
import org.alax.scala.compiler.model.CompilationError

import scala.collection.mutable

class AstTransformer {

  def transform(
                 valueDeclaration: ast.statements.declarations.Value,
                 //TODO we could create a compilation context
                 imports: Seq[compiler.model.Import]
               ): compiler.model.Value.Declaration = {
    return compiler.model.Value.Declaration(
      name = valueDeclaration.name.value,
      `type` = compiler.model.Value.Type(
        id = compiler.model.Declaration.`type`.Id(
          fullyQualifiedName = valueDeclaration.`type` match {
            case ast.partials.types.Value(id, metadata) =>
              id match {
                case upperCase: ast.partials.names.UpperCase => imports.flatMap(`import` => `import`.items)
                  .find(element => element.endsWith("." + upperCase))
                  .getOrElse(
                    throw new CompilationError(
                      path = valueDeclaration.metadata.location.unit,
                      startIndex = valueDeclaration.metadata.location.startIndex,
                      endIndex = valueDeclaration.metadata.location.endIndex,
                      message = "No such type: " + upperCase.value)
                  )
                case qualified: ast.partials.names.Qualified => qualified.qualifications
                  .foldLeft(mutable.StringBuilder(""))((acc, element) => acc.append(element match {
                    case lower: partials.names.LowerCase => lower.value + "."
                    case upper: partials.names.UpperCase => upper.value
                  })).toString
              }
          }
        )
      )
    )
  }


}
