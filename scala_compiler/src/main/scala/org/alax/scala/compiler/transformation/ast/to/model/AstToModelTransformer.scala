package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast as ast;
import org.alax.ast.model.Partial.Name.{LowerCase, Qualified, UpperCase}
import org.alax.ast.model.Statement.Declaration
import org.alax.scala.compiler
import org.alax.scala.compiler.Context
import org.alax.scala.compiler.model.{CompilationError, *}
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class AstToModelTransformer {

  object transform {


    def valueDeclaration(
                          valueDeclaration: ast.model.Statement.Declaration.Value,
                          context: Context.Unit | Context.Package
                        ): compiler.model.Value.Declaration | CompilationError = {
      val imports = context match
        case Context.Unit(_, imports, _) => imports;
        case Context.Package(_, imports, _) => imports;


      val typeId: compiler.model.Declaration.Type.Id | CompilationError = valueDeclaration.`type` match {
        case ast.model.Partial.Type.ValueTypeReference(id, _) => id match {
          case ast.model.Partial.Name.UpperCase(value, _) => imports.find(element => value.equals(element.alias) || value.equals(element.member))
            .map(element => s"${element.ancestor}.${element.member}")
            .map(result => compiler.model.Declaration.Type.Id(value = result))
            .getOrElse(new CompilationError(
              path = valueDeclaration.metadata.location.unit,
              startIndex = valueDeclaration.metadata.location.startIndex,
              endIndex = valueDeclaration.metadata.location.endIndex,
              message = s"Unknown type: ${valueDeclaration.`type`}, did You forget to import?"
            ))
          case ast.model.Partial.Name.Qualified(value: Seq[ast.model.Partial.Name.LowerCase | ast.model.Partial.Name.UpperCase], _) =>
            compiler.model.Declaration.Type.Id(
              value = {
                val prefix = imports.find(element => element.alias.equals(value.head.text()) || element.member.equals(value.head.text()))
                  .map(element => s"${element.ancestor}").getOrElse("")
                value.foldLeft(mutable.StringBuilder(prefix))((acc, element) => acc.append(if (acc.isEmpty) then element.text() else s".${element.text()}")).toString
              }


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

    def trace(location: ast.model.Node.Location): compiler.model.Trace = compiler.model.Trace(
      unit = location.unit,
      lineNumber = location.lineNumber,
      startIndex = location.startIndex,
      endIndex = location.endIndex
    )

    private def foldNames(names: Seq[ast.model.Partial.Name]): String = {
      return names.foldLeft(mutable.StringBuilder())((acc: mutable.StringBuilder, ancestor: ast.model.Partial.Name) =>
        if (acc.isEmpty) then acc.append(ancestor.text())
        else acc.append("." + ancestor.text()))
        .toString()
    }


    //TODO use streams instead of sequences
    def imports(`import`: ast.model.Statement.Declaration.Import, ancestors: Seq[ast.model.Partial.Name.Qualified] = Seq()): Seq[Tracable[compiler.model.Import]] = {
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

    object source {
      def `package`(source: ast.Source.Unit.Package, context: Context.Package | Context.Module | Null = null): Source.Package | Seq[CompilationError] = {
        //TODO validate imports
        val imports = source.imports.flatMap(element => transform.imports(element));
        val errors = ImportsValidator.validate(imports)

        return if (errors.isEmpty) then Source.Package(
          path = source.path, 
          statements = imports.map(tracable => tracable.transformed), 
          context = context
        )
        else errors;
      }

    }
  }


}
