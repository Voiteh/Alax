package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.base
import org.alax.ast.base.{ParseError, Partial, Expression as AstExpression, Statement as AstStatement}
import org.alax.ast.partial.Identifier.{LowerCase, Qualified, UpperCase}
import org.alax.ast.base.statements.Declaration as AstDeclartion
import org.alax.ast.base.expressions.Literal as AstLiteral
import org.alax.ast.partial.Identifier
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{CompilationError, CompilationErrors, CompilerBugError, CompilerError, Expression, Import, Literal, Reference, Tracable, Trace, Type}
import org.alax.scala.compiler.model.{Literals, Value}

import java.nio.file.Path
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class AstToModelTransformer {

  object transform {
    def expression(expression: AstExpression): Expression | CompilerError = {
      expression match {
        case literal: AstLiteral => transform.expression.literal(literal);
        case _ => CompilerBugError(cause = Exception("Not implemented!"));
      }
    }

    object expression {
      def literal(literal: AstLiteral): compiler.base.model.Literal | CompilerError = {
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
      def reference(typeReference: Partial.Type.Reference, imports: Seq[Import]): Type.Reference | CompilerError = {
        return typeReference match {
          case valueTypeReference: ast.Value.Type.Reference => transform.`type`.reference.value(valueTypeReference, imports);
          case other => CompilerBugError(cause = Exception(s"Not implemented - $other !"))
        }
      }

      object reference {
        def value(valueTypeReference: ast.Value.Type.Reference, imports: Seq[Import]): Value.Type.Reference | CompilerError = {
          return valueTypeReference.id match {
            case Identifier.UpperCase(value, _) => imports.find(element =>
              value.equals(element.alias) || value.equals(element.member)
            ).map(element => s"${element.ancestor}.${element.member}")
              .map(result => Value.Type.Reference(id =
                Type.Id(value = result)
              ))
              .getOrElse(
                new CompilationError(
                  path = valueTypeReference.metadata.location.unit,
                  startIndex = valueTypeReference.metadata.location.startIndex,
                  endIndex = valueTypeReference.metadata.location.endIndex,
                  message = s"Unknown type: ${valueTypeReference.id}, did You forget to import?: "
                ))
            case ast.partial.Identifier.Qualified(value: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase], _) =>
              compiler.model.Value.Type.Reference(
                id = Type.Id(
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

      def definition(valueDefinition: ast.Value.Definition, context: Contexts.Unit | Null
                    ): Value.Definition | CompilerError = {
        val imports = context match
          case unit: Contexts.Unit => unit.imports
          case null => Seq[Import]()

        val typeOrError: Value.Type.Reference | CompilerError = transform.`type`.reference.value(valueDefinition.typeReference, imports)
        val nameOrError: String | CompilerError = transform.value.declaration.name(name = valueDefinition.name)
        val expressionOrError: Expression | CompilerError = transform.expression(valueDefinition.initialization)
        return typeOrError match {
          case tpe: Value.Type.Reference =>
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
                        meaning = valid
                      )
                      case _ => CompilerBugError(cause = Exception("Unhandled !"))
                    }
                  case error: CompilerError => error;
              case error: CompilerError => error;
            }
          case error: CompilerError => error

        }


      }


      object declaration {
        def name(name: ast.Value.Name): String | CompilerError = name.text();

        object `type` {
          def reference(valueTypeReference: ast.Value.Type.Reference, imports: Seq[Import]): Value.Type.Reference | CompilerError = {
            val typeReferenceOrError: Type.Reference | CompilerError = transform.`type`.reference(typeReference = valueTypeReference, imports = imports);
            return typeReferenceOrError match {
              case valueTypeReference: Value.Type.Reference => valueTypeReference
              case error: CompilerError => error
              case other => CompilerBugError(cause = Exception(f"Unhandled type: $other !"))
            }
          }
        }
      }

      def declaration(
                       valueDeclaration: ast.Value.Declaration,
                       context: Contexts.Unit | Null
                     ): Value.Declaration | CompilerError = {
        val imports = context match
          case unit: Contexts.Unit => unit.imports
          case null => Seq[Import]()


        val typeOrError: Value.Type.Reference | CompilerError = transform.value.declaration.`type`.reference(valueDeclaration.typeReference, imports);
        val nameOrError = transform.value.declaration.name(valueDeclaration.name);

        return typeOrError match {
          case tpe: Value.Type.Reference =>
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

    object `package` {

      object declaration {
        def name(name: ast.Package.Name): String | CompilationError = {
          return name.text()
        }
      }

      def declaration(declaration: ast.Package.Declaration, context: Contexts.Unit | Null = null): compiler.model.Package.Declaration | CompilerError = {
        return `package`.declaration.name(declaration.name) match {
          case string: String => compiler.model.Package.Declaration(
            name = string
          )
          case error: CompilerError => error;
        }
      }

      object definition {
        def body(body: ast.Package.Body, context: Contexts.Unit | Null = null): compiler.model.Package.Definition.Body | CompilerError = {
          return compiler.model.Package.Definition.Body(
            elements = body.elements.map {
              case value: ast.Value.Definition => transform.value.definition(valueDefinition = value, context = context)
              case error: ParseError => CompilerBugError(error)
            })
        }
      }

      def definition(definition: ast.Package.Definition, context: Contexts.Unit | Null = null): compiler.model.Package.Definition | CompilerError = {
        return `package`.declaration.name(definition.name) match {
          case name: String =>
            `package`.definition.body(definition.body,context) match {
              case body: compiler.model.Package.Definition.Body => compiler.model.Package.Definition(
                declaration = compiler.model.Package.Declaration(
                  name = name
                ),
                body = body
              )
              case error: CompilerError => error
            }
          case error: CompilerError => error;
        }
      }

    }
    object module {
      object declaration {
        def name(name: ast.Module.Name): String | CompilationError = {
          return name.text()
        }
      }

      def declaration(declaration: ast.Module.Declaration, context: Contexts.Unit | Null = null): compiler.model.Module.Declaration | CompilerError = {
        return module.declaration.name(declaration.name) match {
          case string: String => compiler.model.Module.Declaration(
            name = string
          )
          case error: CompilerError => error;
        }
      }

      object definition {
        def body(body: ast.Module.Body, context: Contexts.Unit | Null = null): compiler.model.Module.Definition.Body | CompilerError = {
          return compiler.model.Module.Definition.Body(
            elements = body.elements.map {
              case value: ast.Value.Definition => transform.value.definition(valueDefinition = value, context = context)
              case error: ParseError => CompilerBugError(error)
            })
        }
      }

      def definition(definition: ast.Module.Definition, context: Contexts.Unit | Null = null): compiler.model.Module.Definition | CompilerError = {
        return module.declaration.name(definition.name) match {
          case name: String =>
            module.definition.body(definition.body, context) match {
              case body: compiler.model.Module.Definition.Body => compiler.model.Module.Definition(
                declaration = compiler.model.Module.Declaration(
                  name = name
                ),
                body = body
              )
              case error: CompilerError => error
            }
          case error: CompilerError => error;
        }
      }
    }
    def trace(location: ast.base.Node.Location): Trace = compiler.base.model.Trace(
      unit = location.unit,
      lineNumber = location.startLine,
      startIndex = location.startIndex,
      endIndex = location.endIndex
    )

    private def foldNames(names: Seq[ast.base.Partial.Identifier]): String = {
      return names.foldLeft(mutable.StringBuilder())((acc: mutable.StringBuilder, ancestor: base.Partial.Identifier) =>
        if acc.isEmpty then acc.append(ancestor.text())
        else acc.append("." + ancestor.text()))
        .toString()
    }


    //TODO use streams instead of sequences
    def imports(`import`: ast.Import.Declaration, ancestors: Seq[ast.partial.Identifier.Qualified] = Seq()): Seq[Tracable[Import]] = {
      return `import` match {
        case container: ast.Imports.Nested => container.nestee.flatMap(element => this.imports(element, ancestors :+ container.nest))
        case simple: ast.Imports.Simple => Seq(Tracable(
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
        case alias: ast.Imports.Alias => Seq(Tracable(
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

  }
}