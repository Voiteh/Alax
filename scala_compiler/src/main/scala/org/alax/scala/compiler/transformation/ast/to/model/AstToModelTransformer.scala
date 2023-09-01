package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast.model as ast
import org.alax.ast.model.Partial.Name.{LowerCase, Qualified, UpperCase}
import org.alax.ast.model.Statement.Declaration
import org.alax.scala.compiler
import org.alax.scala.compiler.Context
import org.alax.scala.compiler.model.*

import scala.collection.mutable

class AstToModelTransformer {

  object transform {


    def value(
               valueDeclaration: ast.Statement.Declaration.Value,
               context: Context.Unit | Context.Package
             ): compiler.model.Value.Declaration | CompilationError = {
      val imports = context match
        case Context.Unit(_, imports) => imports;
        case Context.Package(_, imports) => imports;


      val typeId: compiler.model.Declaration.Type.Id | CompilationError = valueDeclaration.`type` match {
        case ast.Partial.types.ValueTypeReference(id, _) => id match {
          case ast.Partial.Name.UpperCase(value, _) => compiler.model.Declaration.Type.Id(name = value)
          case ast.Partial.Name.Qualified(value: Seq[ast.Partial.Name.LowerCase | ast.Partial.Name.UpperCase], _) =>
            compiler.model.Declaration.Type.Id(
              name = value.foldLeft(mutable.StringBuilder(""))((acc, element) => acc.append(element match {
                case lower: ast.Partial.Name.LowerCase => lower.value + "."
                case upper: ast.Partial.Name.UpperCase => upper.value
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

    def trace(location: ast.Node.Location): compiler.model.Trace = compiler.model.Trace(
      unit = location.unit,
      lineNumber = location.lineNumber,
      startIndex = location.startIndex,
      endIndex = location.endIndex
    )

    private def foldNames(names: Seq[ast.Partial.Name]): String = {
      return names.foldLeft(mutable.StringBuilder())((acc: mutable.StringBuilder, ancestor: ast.Partial.Name) =>
        if (acc.isEmpty) then acc.append(ancestor.text())
        else acc.append("." + ancestor.text()))
        .toString()
    }

    //TODO use streams instead of sequences
    def imports(`import`: ast.Statement.Declaration.Import, ancestors: Seq[ast.Partial.Name.Qualified] = Seq()): Seq[Tracable[compiler.model.Import]] = {
      return `import` match {
        case container: Declaration.Import.Nested => container.nestee.flatMap(element => this.imports(element, ancestors :+ container.nest))
        case simple: Declaration.Import.Simple => Seq(Tracable(
          trace = trace(simple.metadata.location),
          transformed = compiler.model.Import(
            ancestor = simple.member match {
              case qualified: Qualified => foldNames(ancestors) + foldNames(qualified.prefix)
              case _ => foldNames(ancestors)
            },
            member = simple.member match {
              case qualified: Qualified => qualified.suffix.text();
              case uppercase: UpperCase => uppercase.text();
              case lowercase: LowerCase => lowercase.text();
            },
          )
        ))
        case alias: Declaration.Import.Alias => Seq(Tracable(
          trace = trace(alias.metadata.location),
          transformed = compiler.model.Import(
            ancestor = alias.member match {
              case qualified: Qualified => foldNames(ancestors) + foldNames(qualified.prefix)
              case _ => foldNames(ancestors)
            },
            member = alias.member match {
              case qualified: Qualified => qualified.suffix.text();
              case uppercase: UpperCase => uppercase.text();
              case lowercase: LowerCase => lowercase.text();
            },
            alias = alias.alias.text()
          )
        ))
      }
    }

  }


}
