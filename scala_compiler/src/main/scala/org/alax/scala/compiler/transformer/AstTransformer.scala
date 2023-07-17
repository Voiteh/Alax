package org.alax.scala.compiler.transformer

import org.alax.scala.compiler
import org.alax.ast.model as ast
import org.alax.ast.model.partials
import org.alax.scala.compiler.model.{CompilationError, CompilerBugException, Context, Import}

import scala.collection.mutable

class AstTransformer {

  def transform(
                 valueDeclaration: ast.statements.declarations.Value,
                 context: Context.Unit | Context.Package
               ): compiler.model.Value.Declaration | CompilationError = {
    val imports = context match
      case Context.Unit(parent, imports) => imports;
      case Context.Package(parent, imports) => imports;


    val typeId: compiler.model.Declaration.Type.Id | CompilationError = valueDeclaration.`type` match {
      case ast.partials.types.Value(id, _) => id match {
        case ast.partials.names.UpperCase(value, _) => imports.find(element => element.alias == value || element.member == value)
          .map(element => compiler.model.Declaration.Type.Id(name = value, `import` = element))
          .getOrElse(
            CompilationError(
              path = valueDeclaration.metadata.location.unit,
              startIndex = valueDeclaration.metadata.location.startIndex,
              endIndex = valueDeclaration.metadata.location.endIndex,
              message = s"No such type:$value, did You forgot to import ?"
            )
          )
        case ast.partials.names.Qualified(value: Seq[ast.partials.names.LowerCase | ast.partials.names.UpperCase], _) =>
          compiler.model.Declaration.Type.Id(
            name = value.foldLeft(mutable.StringBuilder(""))((acc, element) => acc.append(element match {
              case lower: partials.names.LowerCase => lower.value + "."
              case upper: partials.names.UpperCase => upper.value
            })).toString
          )
      }
    }
    return typeId match {
      case id: compiler.model.Declaration.Type.Id => compiler.model.Value.Declaration(
        name = valueDeclaration.name.value,
        `type` = compiler.model.Value.Type(
          id = id
        )
      )
      case error: CompilationError => error
    }

  }


}
