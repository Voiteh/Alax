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
               ): compiler.model.Value.Declaration | CompilationError = {

    val fullyQualifiedName: String | CompilationError = valueDeclaration.`type` match {
      case ast.partials.types.Value(id, _) => id match {
        case ast.partials.names.UpperCase(value, _) => imports.flatMap(`import` => `import`.items)
          .filter(element => element.endsWith(s".$value"))
          .reduceOption[String | CompilationError](
            (left, right) => CompilationError(
              path = valueDeclaration.metadata.location.unit,
              startIndex = valueDeclaration.metadata.location.startIndex,
              endIndex = valueDeclaration.metadata.location.endIndex,
              message = s"Multiple imports found for $value, use fully qualified name or apply import alias for $value")
          )
          .getOrElse(
            CompilationError(
              path = valueDeclaration.metadata.location.unit,
              startIndex = valueDeclaration.metadata.location.startIndex,
              endIndex = valueDeclaration.metadata.location.endIndex,
              message = s"No such type:$value"
            )
          )
        case ast.partials.names.Qualified(value: Seq[ast.partials.names.LowerCase | ast.partials.names.UpperCase], _) =>
          value.foldLeft(mutable.StringBuilder(""))((acc, element) => acc.append(element match {
            case lower: partials.names.LowerCase => lower.value + "."
            case upper: partials.names.UpperCase => upper.value
          })).toString
      }
    }
    return fullyQualifiedName match {
      case name: String => compiler.model.Value.Declaration(
        name = valueDeclaration.name.value,
        `type` = compiler.model.Value.Type(
          id = compiler.model.Declaration.Type.Id(name)
        )
      )
      case error: CompilationError => error
    }

  }


}
