package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast.model as ast
import org.alax.ast.model.Node.someFun
import org.alax.ast.model.partials.names.{LowerCase, Qualified, UpperCase}
import org.alax.ast.model.statements.declarations
import org.alax.scala.compiler
import org.alax.scala.compiler.Context
import org.alax.scala.compiler.model.*

import scala.collection.mutable

class AstToModelTransformer {

  object transform {


    def value(
               valueDeclaration: ast.statements.declarations.Value,
               context: Context.Unit | Context.Package
             ): compiler.model.Value.Declaration | CompilationError = {
      val imports = context match
        case Context.Unit(_, imports) => imports;
        case Context.Package(_, imports) => imports;


      val typeId: compiler.model.Declaration.Type.Id | CompilationError = valueDeclaration.`type` match {
        case ast.partials.types.ValueTypeReference(id, _) => id match {
          case ast.partials.names.UpperCase(value, _) => compiler.model.Declaration.Type.Id(name = value)
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

    private def foldNames(names: Seq[ast.partials.Name]): String = {
      return names.foldLeft(mutable.StringBuilder())((acc: mutable.StringBuilder, ancestor: ast.partials.Name) =>
        if (acc.isEmpty) then acc.append(ancestor.text())
        else acc.append("." + ancestor.text()))
        .toString()
    }

    //TODO use streams instead of sequences
    def imports(`import`: ast.statements.declarations.Import, ancestors: Seq[ast.partials.names.Qualified] = Seq()): Seq[Tracable[compiler.model.Import]] = {
      return `import` match {
        case container: declarations.Import.Nested => container.nestee.flatMap(element => this.imports(element, ancestors :+ container.nest))
        case simple: declarations.Import.Simple => Seq(Tracable(
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
        case alias: declarations.Import.Alias => Seq(Tracable(
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
