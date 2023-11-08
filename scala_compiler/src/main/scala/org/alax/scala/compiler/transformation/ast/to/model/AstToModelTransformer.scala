package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.base
import org.alax.ast.base.model.Partial
import org.alax.ast.base.model.Partial.Name.{LowerCase, Qualified, UpperCase}
import org.alax.ast.base.model.Statement.Declaration
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{CompilationError, CompilerError, Expression, Import, Reference, Tracable, Trace}
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.*
import org.alax.scala.compiler.model.{Literals, Value}
import org.alax.scala.compiler.transformation.Context
import org.alax.scala.compiler.model.sources

import java.nio.file.Path
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class AstToModelTransformer {

  object transform {
    def expression(expression: base.model.Expression): Expression | CompilerError = {
      expression match {
        case literal: ast.base.expressions.Literal => transform.expression.literal(literal);
        case _ => compiler.base.model.CompilerBugException(cause = Exception("Not implemented!"));
      }
    }

    object expression {
      def literal(literal: ast.base.expressions.Literal): model.Literal | CompilerError = {
        return literal match {
          case ast.Literals.Character(value, metadata) => Literals.Character(
            value = value
          )
          case ast.Literals.Integer(value, metadata) => compiler.model.Literals.Integer(
            value = value.longValue()
          )
          case ast.Literals.Boolean(value, metadata) => compiler.model.Literals.Boolean(
            value = value
          )
          case ast.Literals.String(value, metadata) => compiler.model.Literals.String(
            value = value
          )
          case ast.Literals.Float(value, metadata) => compiler.model.Literals.Float(
            value = value
          )
        }
      }
    }

    object `type` {
      def reference(typeReference: base.model.Partial.Type.Reference, imports: Seq[Import]): model.Declaration.Type | CompilerError = {
        return typeReference match {
          case valueTypeReference: base.model.Partial.Type.Reference.Value => transform.`type`.reference.value(valueTypeReference, imports);
          case other => compiler.base.model.CompilerBugException(cause = Exception(s"Not implemented - $other !"))
        }
      }

      object reference {
        def value(valueTypeReference: base.model.Partial.Type.Reference.Value, imports: Seq[Import]): Value.Type | CompilerError = {
          return valueTypeReference.id match {
            case base.model.Partial.Name.UpperCase(value, _) => imports.find(element =>
              value.equals(element.alias) || value.equals(element.member)
            ).map(element => s"${element.ancestor}.${element.member}")
              .map(result => Value.Type(id =
                model.Declaration.Type.Id(value = result)
              ))
              .getOrElse(new CompilationError(
                path = valueTypeReference.metadata.location.unit,
                startIndex = valueTypeReference.metadata.location.startIndex,
                endIndex = valueTypeReference.metadata.location.endIndex,
                message = s"Unknown type: ${valueTypeReference}, did You forget to import?"
              ))
            case base.model.Partial.Name.Qualified(value: Seq[base.model.Partial.Name.LowerCase | base.model.Partial.Name.UpperCase], _) =>
              compiler.model.Value.Type(
                id = model.Declaration.Type.Id(
                  value = {
                    val prefix = imports.find(element => element.alias.equals(value.head.text()) || element.member.equals(value.head.text()))
                      .map(element => s"${element.ancestor}").getOrElse("")
                    value.foldLeft(mutable.StringBuilder(prefix))((acc, element) => acc.append(if acc.isEmpty then element.text() else s".${element.text()}")).toString
                  }
                )
              )
          }
        }
      }
    }

    object value {

      def definition(valueDefinition: base.model.Statement.Definition.Value, context: Context.Unit | Context.Package | Null
                    ): Value.Definition | CompilerError = {
        val imports = context match
          case Context.Unit(_, imports, _) => imports;
          case Context.Package(_, imports, _) => imports;

        val typeOrError: Value.Type | CompilerError = transform.`type`.reference.value(valueDefinition.typeReference, imports)
        val nameOrError: String | CompilerError = transform.value.declaration.name(name = valueDefinition.name)
        val expressionOrError: Expression | CompilerError = transform.expression(valueDefinition.initialization)
        return typeOrError match {
          case tpe: Value.Type =>
            nameOrError match {
              case name: String =>
                expressionOrError match
                  case expression: Expression =>
                    expression match {
                      case valid@(_: model.Literal | _: Reference) => compiler.model.Value.Definition(
                        declaration = Value.Declaration(
                          name = name,
                          `type` = tpe
                        ),
                        initialization = valid
                      )
                      case _ => throw compiler.base.model.CompilerBugException(cause = Exception("Unhandled !"))
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
          def reference(valueTypeReference: Partial.Type.Reference.Value, imports: Seq[Import]): Value.Type | CompilerError = {
            val typeReferenceOrError: model.Declaration.Type | CompilerError = transform.`type`.reference(typeReference = valueTypeReference, imports = imports);
            return typeReferenceOrError match {
              case valueTypeReference: Value.Type => valueTypeReference
              case error: CompilerError => error
              case other => compiler.base.model.CompilerBugException(cause = Exception(f"Unhandled type: $other !"))
            }
          }
        }
      }

      def declaration(
                       valueDeclaration: base.model.Statement.Declaration.Value,
                       context: Context.Unit | Context.Package
                     ): Value.Declaration | CompilerError = {
        val imports = context match
          case Context.Unit(_, imports, _) => imports;
          case Context.Package(_, imports, _) => imports;

        val typeOrError: Value.Type | CompilerError = transform.value.declaration.`type`.reference(valueDeclaration.typeReference, imports);
        val nameOrError = transform.value.declaration.name(valueDeclaration.name);

        return typeOrError match {
          case tpe: Value.Type =>
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

    def trace(location: base.model.Node.Location): Trace = compiler.base.model.Trace(
      unit = location.unit,
      lineNumber = location.lineNumber,
      startIndex = location.startIndex,
      endIndex = location.endIndex
    )

    private def foldNames(names: Seq[base.model.Partial.Name]): String = {
      return names.foldLeft(mutable.StringBuilder())((acc: mutable.StringBuilder, ancestor: base.model.Partial.Name) =>
        if acc.isEmpty then acc.append(ancestor.text())
        else acc.append("." + ancestor.text()))
        .toString()
    }


    //TODO use streams instead of sequences
    def imports(`import`: base.model.Statement.Declaration.Import, ancestors: Seq[base.model.Partial.Name.Qualified] = Seq()): Seq[Tracable[Import]] = {
      return `import` match {
        case container: Declaration.Import.Nested => container.nestee.flatMap(element => this.imports(element, ancestors :+ container.nest))
        case simple: Declaration.Import.Simple => Seq(Tracable(
          trace = trace(simple.metadata.location),
          transformed = compiler.base.model.Import(
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
          transformed = compiler.base.model.Import(
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



      def `package`(source: ast.Source.Units.Package, parentContext: Context.Package | Context.Module | Null = null): sources.Package | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element))
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Package(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Units.Package.Member) =>
              member match {
                case valueDefinition: base.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
              }
            )
            return sources.Package(
              name = source.path.getFileName.toString,
              members = membersOrErrors.filter(item => item.isInstanceOf[sources.Package.Member])
                .map(item => item.asInstanceOf[sources.Package.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = context
            )

        }
      }


      //TODO probably there will be context for nested classes
      def `class`(source: ast.Source.Units.Class, parentContext: Context.Package | Null = null): sources.Unit | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element))
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Unit(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Units.Class.Member) =>
              member match {
                case valueDefinition: base.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
                case _ => compiler.base.model.CompilerBugException(cause = Exception("Not implemented!"))
              }
            )
            return sources.Unit(
              name = source.path.getFileName.toString,
              members = membersOrErrors.filter(item => item.isInstanceOf[sources.Unit.Member])
                .map(item => item.asInstanceOf[sources.Unit.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = parentContext
            )

        }
      }

      //TODO probably there will be context for nested classes
      def `interface`(source: ast.Source.Units.Interface, parentContext: Context.Package | Null = null): sources.Unit | Seq[CompilerError] = {
        val imports = source.imports.flatMap(element => transform.imports(element))
        val errors = ImportsValidator.validate(imports)
        return errors match {
          case Nil =>
            val context = Context.Unit(
              path = source.path,
              imports = imports.map(tracable => tracable.transformed),
              parent = parentContext
            )
            val membersOrErrors = source.members.map((member: ast.Source.Units.Interface.Member) =>
              member match {
                case valueDefinition: base.model.Statement.Definition.Value => transform.value.definition(
                  valueDefinition = valueDefinition,
                  context = context
                )
                case _ => compiler.base.model.CompilerBugException(cause = Exception("Not implemented!"))
              }
            )
            return sources.Unit(
              name = source.path.getFileName.toString,
              members = membersOrErrors.filter(item => item.isInstanceOf[sources.Unit.Member])
                .map(item => item.asInstanceOf[sources.Unit.Member]),
              errors = membersOrErrors.filter(item => item.isInstanceOf[CompilerError])
                .map(item => item.asInstanceOf[CompilerError]),
              context = parentContext
            )

        }
      }


    }
  }
}