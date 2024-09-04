package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.base.{ParseError, Expression as AstExpression, Statement as AstStatement}
import org.alax.ast.Identifier.{LowerCase, UpperCase, matches}
import org.alax.ast.base.statements.Declaration as AstDeclartion
import org.alax.ast.base.expressions.Literal as AstLiteral
import org.alax.scala.compiler
import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilationError, CompilationErrors, CompilerBugError, CompilerError, Expression, Import, Literal, Reference, Tracable, Trace, Type}
import org.alax.scala.compiler.model

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
          case ast.Literals.Character(value, metadata) => model.Literals.Character(
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
      def reference(identifier: ast.Value.Type.Reference, imports: Seq[Import]): Type.Reference | CompilerError = {
        return transform.`type`.id.value(identifier, imports)
      }

      object id {
        def value(typeReference: ast.Value.Type.Reference, imports: Seq[Import]): model.Value.Type.Reference | CompilerError = {
          val packageReference: model.Package.Reference | CompilationError = if typeReference.`package` != null then
            transform.`package`.reference(typeReference.`package`)
          else imports.find(element =>
            typeReference.identifier.text.equals(element.alias) || typeReference.identifier.text.equals(element.member)
          ).map[model.Package.Reference | CompilationError](`import` => `import`.ancestor)
            .getOrElse(new CompilationError(
              path = typeReference.metadata.location.unit,
              startIndex = typeReference.metadata.location.startIndex,
              endIndex = typeReference.metadata.location.endIndex,
              message = s"Unknown type: ${typeReference.identifier.text}, did You forget to import?: "
            ))
          packageReference match {
            case pkg: model.Package.Reference => model.Value.Type.Reference(
              packageReference = pkg,
              id = base.model.Type.Id(typeReference.identifier.text)
            )
            case error: CompilationError => error
          }
        }
      }
    }

    object evaluable {
      object declaration {
        def identifier(name: ast.Evaluable.Identifier): String | CompilerError = name.text;

      }
    }

    object value {

      object declaration {
        def identifier(id: ast.Evaluable.Identifier): model.Value.Declaration.Identifier | CompilerError = id.value


      }

      def definition(valueDefinition: ast.Value.Definition, context: Contexts.Unit | Null
                    ): model.Value.Definition | CompilerError = {
        val imports = context match
          case unit: Contexts.Unit => unit.imports
          case null => Seq[Import]()

        val typeOrError: model.Value.Type.Reference | CompilerError = transform.`type`.id.value(valueDefinition.typeReference, imports)
        val nameOrError: String | CompilerError = transform.value.declaration.identifier(valueDefinition.identifier)
        val expressionOrError: Expression | CompilerError = transform.expression(valueDefinition.initialization)
        return typeOrError match {
          case tpe: model.Value.Type.Reference =>
            nameOrError match {
              case name: String =>
                expressionOrError match
                  case expression: Expression =>
                    expression match {
                      case valid@(_: base.model.Literal | _: model.Value.Reference) => compiler.model.Value.Definition(
                        declaration = model.Value.Declaration(
                          identifier = name,
                          typeReference = tpe
                        ),
                        meaning = valid
                      )
                      case item => CompilerBugError(cause = Exception(s"Unhandled ${item} !"))
                    }
                  case error: CompilerError => error;
              case error: CompilerError => error;
            }
          case error: CompilerError => error

        }


      }

      object `type` {
        def reference(valueTypeReference: ast.Value.Type.Reference, imports: Seq[Import]): model.Value.Type.Reference | CompilerError = {
          val typeReferenceOrError: Type.Reference | CompilerError = transform.`type`.reference(identifier = valueTypeReference, imports = imports);
          return typeReferenceOrError match {
            case valueTypeReference: model.Value.Type.Reference => valueTypeReference
            case error: CompilerError => error
            case other => CompilerBugError(cause = Exception(f"Unhandled type: $other !"))
          }
        }
      }


      def declaration(
                       valueDeclaration: ast.Value.Declaration,
                       context: Contexts.Unit | Null
                     ): model.Value.Declaration | CompilerError = {
        val imports = context match
          case unit: Contexts.Unit => unit.imports
          case null => Seq[Import]()


        val typeOrError: model.Value.Type.Reference | CompilerError = transform.value.`type`.reference(valueDeclaration.typeReference, imports);
        val nameOrError = transform.evaluable.declaration.identifier(valueDeclaration.identifier);

        return typeOrError match {
          case tpe: model.Value.Type.Reference =>
            nameOrError match {
              case name: String => compiler.model.Value.Declaration(
                identifier = name,
                typeReference = tpe
              )
              case error: CompilerError => error;
            }

          case error: CompilerError => error

        }
      }
    }

    object function {


      def declaration(declaration: ast.Function.Declaration, context: Contexts.Unit | Null=null): model.Function.Declaration | CompilerError = {
        val imports: Seq[Import] = context match {
          case unit: Contexts.Unit => unit.imports
          case null => Seq[Import]()
        }
        val identifierOrError: model.Function.Declaration.Identifier | CompilerError = transform.function.declaration.identifier(declaration.identifier)
        val returnTypeOrError: model.Value.Type.Reference | Null | CompilerError = declaration.returnTypeReference match {
          case valueType: ast.Value.Type.Reference => value.`type`.reference(valueType, imports);
          case null => null
        }
        val parameters: Seq[model.Function.Declaration.Parameter | CompilerError] = declaration.parameters.map(item => transform.function.declaration.parameter(
          parameter = item,
          imports = imports
        ))

        identifierOrError match {
          case identifier: model.Function.Declaration.Identifier => returnTypeOrError match {
            case null => model.Function.Declaration(
              identifier = identifier,
              parameters = parameters
            )
            case returnType: model.Value.Type.Reference => model.Function.Declaration(
              identifier = identifier,
              parameters = parameters,
              returnType = returnType
            )
            case error: CompilerError => error
          }
          case error: CompilerError => error
        }

      }

      object declaration {

        def identifier(id: ast.Evaluable.Identifier): model.Function.Declaration.Identifier | CompilerError = id.value

        def parameter(parameter: ast.Function.Declaration.Parameter, imports: Seq[Import]): model.Function.Declaration.Parameter | CompilerError = {
          val identifierOrError: String | CompilerError = function.declaration.parameter.identifier(parameter.identifier)
          val typeReferenceOrError: model.Value.Type.Reference | CompilerError = transform.value.`type`.reference(
            valueTypeReference = parameter.`type`,
            imports = imports
          )
          val initializationOrError: base.model.Expression | Null | CompilerError = if (parameter.expression != null)
          then transform.expression(parameter.expression)
          else null;
          identifierOrError match {
            case identifier: String => typeReferenceOrError match {
              case typeReference: model.Value.Type.Reference => initializationOrError match {
                case initialization: base.model.Expression => model.Function.Declaration.Parameter(
                  identifier = identifier,
                  typeReference = typeReference,
                  initialization = initialization
                )
                case null => model.Function.Declaration.Parameter(
                  identifier = identifier,
                  typeReference = typeReference
                )
                case error: CompilerError => error
              }
              case error: CompilerError => error
            }
            case error: CompilerError => error
          }
        }

        object parameter {
          def identifier(identifier: ast.Identifier.LowerCase): model.Function.Declaration.Parameter.Identifier = identifier.text
        }
      }

      object definition {

      }

      object call {

      }
    }

    object `package` {

      def reference(reference: ast.Package.Reference, context: Contexts.Unit | Null = null): model.Package.Reference = {
        model.Package.Reference(
          identifiers = reference.parts.map(item => item.text)
        )
      }

      object declaration {
        def name(name: ast.Package.Identifier): String | CompilationError = {
          return name.text
        }
      }

      def declaration(declaration: ast.Package.Declaration, context: Contexts.Unit | Null = null): compiler.model.Package.Declaration | CompilerError = {
        return `package`.declaration.name(declaration.identifier) match {
          case string: String => compiler.model.Package.Declaration(
            identifier = string
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
        return `package`.declaration.name(definition.identifier) match {
          case name: String =>
            `package`.definition.body(definition.body, context) match {
              case body: compiler.model.Package.Definition.Body => compiler.model.Package.Definition(
                declaration = compiler.model.Package.Declaration(
                  identifier = name
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
        def name(name: ast.Module.Identifier): String | CompilationError = {
          return name.text
        }
      }

      def declaration(declaration: ast.Module.Declaration, context: Contexts.Unit | Null = null): compiler.model.Module.Declaration | CompilerError = {
        return module.declaration.name(declaration.identifier) match {
          case string: String => compiler.model.Module.Declaration(
            identifier = string
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
        return module.declaration.name(definition.identifier) match {
          case name: String =>
            module.definition.body(definition.body, context) match {
              case body: compiler.model.Module.Definition.Body => compiler.model.Module.Definition(
                declaration = compiler.model.Module.Declaration(
                  identifier = name
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

    //TODO use streams instead of sequences
    def imports(`import`: ast.Import.Declaration, ancestors: Seq[ast.Import.Identifier] = Seq()): Seq[Tracable[Import]] = {
      `import` match {
        case container: ast.Imports.Nested => container.nestee.flatMap(element => this.imports(element, ancestors :+ container.nest))
        case simple: ast.Imports.Simple => Seq(Tracable(
          trace = trace(simple.metadata.location),
          transformed = if simple.member.parts.size > 1 then compiler.base.model.Import(
            ancestor = model.Package.Reference(identifiers = ancestors.appendedAll(simple.member.prefix)
              .map(item => item.text)
            ),
            member = simple.member.suffix.text) else
            compiler.base.model.Import(
              ancestor = model.Package.Reference(identifiers = ancestors.map(item => item.text)),
              member = simple.member.suffix.text
            )
        ))
        case alias: ast.Imports.Alias => Seq(Tracable(
          trace = trace(alias.metadata.location),
          transformed = if alias.member.parts.size > 1 then compiler.base.model.Import(
            ancestor = model.Package.Reference(identifiers = ancestors.appendedAll(alias.member.prefix)
              .map(item => item.text)
            ),
            member = alias.member.suffix.text,
            alias = alias.alias.text
          ) else compiler.base.model.Import(
            ancestor = model.Package.Reference(identifiers = ancestors.map(item => item.text)),
            member = alias.member.suffix.text,
            alias = alias.alias.text
          )
        ))
      }
    }

  }
}