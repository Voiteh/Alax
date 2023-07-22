package org.alax.scala.compiler.transformer

import org.alax.scala.compiler
import org.alax.ast.model as ast

import org.alax.scala.compiler.model.{CompilationError, CompilerBugException, Context, Import, Tracable}
import ast.statements.declarations

import scala.collection.mutable

class AstTransformer {

  object transform {


    def value(
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
                case lower: ast.partials.names.LowerCase => lower.value + "."
                case upper: ast.partials.names.UpperCase => upper.value
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

    def trace(location: ast.node.Location): compiler.model.Trace = compiler.model.Trace(
      unit = location.unit,
      lineNumber = location.lineNumber,
      startIndex = location.startIndex,
      endIndex = location.endIndex
    )

    //TODO use streams instead of sequences
    def `import`(`import`: ast.statements.declarations.Import, parent: ast.partials.names.Qualified | Null = null): Seq[Tracable[compiler.model.Import]] = {

      `import` match {
        case simple: declarations.Import.Simple => Seq(
          Tracable(
            trace = trace(`import`.metadata.location),
            transformed = compiler.model.Import(
              `package` = Option.apply(parent)
                .map((par: ast.partials.names.Qualified) =>
                  Option.apply(`import`.`package`)
                    .map(importPackage => s"${par.text()}.${importPackage.text()}")
                    .getOrElse(par.text())
                )
                .getOrElse(`import`.`package`.text()),
              member = simple.member match {
                case uppercase: ast.partials.names.UpperCase => uppercase.text()
                case lowercase: ast.partials.names.LowerCase => lowercase.text()
              }
            )
          )
        )
        case alias: declarations.Import.Alias => Seq(
          Tracable(
            trace = trace(`import`.metadata.location),
            transformed = compiler.model.Import(
              `package` = Option.apply(parent)
                .map((par: ast.partials.names.Qualified) =>
                  Option.apply(`import`.`package`)
                    .map(importPackage => s"${par.text()}.${importPackage.text()}")
                    .getOrElse(par.text())
                )
                .getOrElse(`import`.`package`.text()),
              member = alias.member match {
                case uppercase: ast.partials.names.UpperCase => uppercase.text()
                case lowercase: ast.partials.names.LowerCase => lowercase.text()
              },
              alias = alias.alias match {
                case uppercase: ast.partials.names.UpperCase => uppercase.text()
                case lowercase: ast.partials.names.LowerCase => lowercase.text()
              },
            ))
        )
        case container: declarations.Import.Container => container.members.flatMap(element => this.`import`(element, container.`package`))
      }
    }

  }


}
