package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.model.Expression.Literal
import org.alax.ast.model.Partial
import org.alax.ast.model.Partial.Name.{LowerCase, Qualified, UpperCase}
import org.alax.ast.model.Statement.Declaration
import org.alax.scala.compiler
import org.alax.scala.compiler.model
import org.alax.scala.compiler.model.{CompilerError, *}
import org.alax.scala.compiler.transformation.Context

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class AstToModelTransformer {

  object transform {
    def expression(expression: ast.model.Expression): compiler.model.Expression | CompilerError = {
      expression match {
        case literal: Literal => transform.expression.literal(literal);
        case _ => compiler.model.CompilerBugException(cause = Exception("Not implemented!"));
      }
    }

    object expression {
      def literal(literal: ast.model.Expression.Literal): compiler.model.Literal | CompilerError = {
        return literal match {
          case Literal.Character(value, metadata) => compiler.model.Literal.Character(
            value = value
          )
          case Literal.Integer(value, metadata) => compiler.model.Literal.Integer(
            value = value.longValue()
          )
          case Literal.Boolean(value, metadata) => compiler.model.Literal.Boolean(
            value = value
          )
          case Literal.String(value, metadata) => compiler.model.Literal.String(
            value = value
          )
          case Literal.Float(value, metadata) => compiler.model.Literal.Float(
            value = value
          )
        }
      }
    }

    object `type` {
      def reference(typeReference: ast.model.Partial.Type.Reference, imports: Seq[compiler.model.Import]): compiler.model.Declaration.Type | CompilerError = {
        return typeReference match {
          case valueTypeReference: ast.model.Partial.Type.Reference.Value => transform.`type`.reference.value(valueTypeReference, imports);
          case other => compiler.model.CompilerBugException(cause = Exception(s"Not implemented - $other !"))
        }
      }

      object reference {
        def value(valueTypeReference: ast.model.Partial.Type.Reference.Value, imports: Seq[compiler.model.Import]): compiler.model.Value.Type | CompilerError = {
          return valueTypeReference.id match {
            case ast.model.Partial.Name.UpperCase(value, _) => imports.find(element =>
              value.equals(element.alias) || value.equals(element.member)
            ).map(element => s"${element.ancestor}.${element.member}")
              .map(result => compiler.model.Value.Type(id =
                model.Declaration.Type.Id(value = result)
              ))
              .getOrElse(new CompilationError(
                path = valueTypeReference.metadata.location.unit,
                startIndex = valueTypeReference.metadata.location.startIndex,
                endIndex = valueTypeReference.metadata.location.endIndex,
                message = s"Unknown type: ${valueTypeReference}, did You forget to import?"
              ))
            case ast.model.Partial.Name.Qualified(value: Seq[ast.model.Partial.Name.LowerCase | ast.model.Partial.Name.UpperCase], _) =>
              compiler.model.Value.Type(
                id = compiler.model.Declaration.Type.Id(
                  value = {
                    val prefix = imports.find(element => element.alias.equals(value.head.text()) || element.member.equals(value.head.text()))
                      .map(element => s"${element.ancestor}").getOrElse("")
                    value.foldLeft(mutable.StringBuilder(prefix))((acc, element) => acc.append(if (acc.isEmpty) then element.text() else s".${element.text()}")).toString
                  }
                )
              )
          }
        }
      }
    }

    object value {

      def definition(valueDefinition: ast.model.Statement.Definition.Value, context: Context.Unit | Context.Package | Null
                    ): compiler.model.Value.Definition | CompilerError = {
        val imports = context match
          case Context.Unit(_, imports, _) => imports;
          case Context.Package(_, imports, _) => imports;

        val typeOrError: compiler.model.Value.Type | CompilerError = transform.`type`.reference.value(valueDefinition.typeReference, imports);
        val nameOrError: String | CompilerError = transform.value.declaration.name(name = valueDefinition.name);
        val expressionOrError: compiler.model.Expression | CompilerError = transform.expression(valueDefinition.initialization);
        return typeOrError match {
          case tpe: compiler.model.Value.Type =>
            nameOrError match {
              case name: String =>
                expressionOrError match
                  case expression: compiler.model.Expression =>
                    expression match {
                      case valid@(_: compiler.model.Literal | _: compiler.model.Reference) => compiler.model.Value.Definition(
                        declaration = Value.Declaration(
                          name = name,
                          `type` = tpe
                        ),
                        initialization = valid
                      )
                      case _ => throw compiler.model.CompilerBugException(cause = Exception("Unhandled !"))
                    }
                  case error: CompilerError => error;
              case error: CompilerError => error;
            }
          case error: CompilerError => error

        }


      }


      object declaration {
        def name(name: Partial.Name.LowerCase): String | CompilerError = name.text();

        object `type` {
          def reference(valueTypeReference: Partial.Type.Reference.Value, imports: Seq[compiler.model.Import]): compiler.model.Value.Type | compiler.model.CompilerError = {
             val typeReferenceOrError:compiler.model.Declaration.Type | CompilerError=transform.`type`.reference(typeReference = valueTypeReference, imports = imports);
             return typeReferenceOrError match {
              case valueTypeReference: compiler.model.Value.Type => valueTypeReference
              case error: compiler.model.CompilerError => error
              case other => compiler.model.CompilerBugException(cause = Exception(f"Unhandled type: $other !"))
            }
          }
        }
      }

      def declaration(
                       valueDeclaration: ast.model.Statement.Declaration.Value,
                       context: Context.Unit | Context.Package
                     ): compiler.model.Value.Declaration | CompilerError = {
        val imports = context match
          case Context.Unit(_, imports, _) => imports;
          case Context.Package(_, imports, _) => imports;

        val typeOrError: compiler.model.Value.Type | CompilerError = transform.value.declaration.`type`.reference(valueDeclaration.typeReference, imports);
        val nameOrError = transform.value.declaration.name(valueDeclaration.name);

        return typeOrError match {
          case tpe: compiler.model.Value.Type =>
            nameOrError match {
              case name: String => compiler.model.Value.Declaration(
                name = name,
                `type` = tpe
              )
              case error: CompilerError => error;
            }

          case error: CompilerError => error

        }
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
      def `package`(source: ast.Source.Unit.Package, parentContext: Context.Package | Context.Module | Null = null): compiler.model.Source.Package | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element));
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Package(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Unit.Package.Member) =>
              member match {
                case valueDefinition: ast.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
              }
            )
            return Source.Package(
              path = source.path,
              members = membersOrErrors.filter(item => item.isInstanceOf[Source.Package.Member])
                .map(item => item.asInstanceOf[Source.Package.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = context
            )

        }
      }


      //TODO probably there will be context for nested classes
      def `class`(source: ast.Source.Unit.Class, parentContext: Context.Package | Null = null): Source.Unit | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element));
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Unit(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Unit.Class.Member) =>
              member match {
                case valueDefinition: ast.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
                case _ => compiler.model.CompilerBugException(cause = Exception("Not implemented!"))
              }
            )
            return Source.Unit(
              path = source.path,
              members = membersOrErrors.filter(item => item.isInstanceOf[Source.Unit.Member])
                .map(item => item.asInstanceOf[Source.Unit.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = parentContext
            )

        }
      }

      //TODO probably there will be context for nested classes
      def `interface`(source: ast.Source.Unit.Interface, parentContext: Context.Package | Null = null): Source.Unit | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element));
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Unit(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Unit.Interface.Member) =>
              member match {
                case valueDefinition: ast.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
                case _ => compiler.model.CompilerBugException(cause = Exception("Not implemented!"))
              }
            )
            return Source.Unit(
              path = source.path,
              members = membersOrErrors.filter(item => item.isInstanceOf[Source.Unit.Member])
                .map(item => item.asInstanceOf[Source.Unit.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = parentContext
            )

        }
      }


    }
  }
}