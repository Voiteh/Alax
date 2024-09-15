package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.Chain
import org.alax.ast.base.{ParseError, Expression as AstExpression, Statement as AstStatement}
import org.alax.ast.Identifier.{LowerCase, UpperCase, matches}
import org.alax.ast.base.statements.Declaration as AstDeclartion
import org.alax.ast.base.expressions.Literal as AstLiteral
import org.alax.scala.compiler
import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilationError, CompilationErrors, CompilerBugError, CompilerError, Expression, Import, Literal, Reference, Statement, Tracable, Trace, Type}
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.Context

import java.nio.file.Path
import scala.+:
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class AstToModelTransformer {

  object transform {
    private def `wrap results`[Item, Valid, Errors](items: (Seq[Item], Seq[CompilerError]))
                                                   (validWrapper: Seq[Item] => Valid)
                                                   (errorWrapper: Seq[CompilerError] => Errors): Valid | Errors = {
      val (valid, errors) = items;
      if errors.nonEmpty then errorWrapper(errors) else validWrapper(valid)
    }

    def expression(expression: AstExpression): base.model.Expression | CompilerError = {
      expression match {
        case literal: AstLiteral => transform.expression.literal(literal);
        case _ => CompilerBugError(cause = Exception("Not implemented!"));
      }
    }

    object expression {

      private def `chain support`(chain: ast.Chain.Expression): Seq[base.model.Expression] | CompilerError = {
        val result: base.model.Expression | CompilerError = chain.expression match {
          case expression: ast.base.Expression => transform.expression(expression)
          case error: ParseError => CompilationError(
            path = error.metadata.location.unit,
            message = error.message,
            startIndex = error.metadata.location.startIndex,
            endIndex = error.metadata.location.endIndex
          )
        }

        result match {
          case expression: Expression => chain.next match {
            case next: ast.Chain.Expression => `chain support`(next) match {
              case seq: Seq[base.model.Expression] => expression +: seq
              case error: CompilerError => error;
            }
            case null => Seq(expression)
          }
          case compilerError: CompilerError => compilerError
        }
      }

      def chain(chain: ast.Chain.Expression): model.Expression.Chain | CompilerError = {
        `chain support`(chain) match {
          case seq: Seq[base.model.Expression] => model.Expression.Chain(seq)
          case error: CompilerError => error;
        }
      }

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
              message = s"Unknown type: ${typeReference.identifier.text}, did You forget to import?"
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
        //TODO find out if given identifier is a function or value identifier
        def identifier(name: ast.Evaluable.Identifier): model.Routine.Declaration.Identifier | model.Value.Declaration.Identifier | CompilerError = name.text;

      }

      def reference(reference: ast.Evaluable.Reference, context: Contexts.Package | Null = null): model.Evaluable.Reference | CompilerError = {
        val packageReferenceOrError: model.Package.Reference | CompilerError = transform.`package`.reference(reference.container, context)
        val identifierOrError: model.Routine.Declaration.Identifier | model.Value.Declaration.Identifier | CompilerError = transform.evaluable.declaration.identifier(reference.identifier)

        packageReferenceOrError match {
          case reference: model.Package.Reference => identifierOrError match {
            case identifier: (model.Routine.Declaration.Identifier | model.Value.Declaration.Identifier) => model.Evaluable.Reference(
              `package` = reference, identifier = identifier
            )
            case error: CompilerError => error;
          }
          case error: CompilerError => error;
        }

      }
    }

    object value {

      def assignment(assignment: ast.Value.Assignment.Expression): model.Value.Assignment | CompilerError = {
        val referenceOrError: model.Evaluable.Reference | CompilerError = transform.evaluable.reference(assignment.left);
        val chainOrError: model.Expression.Chain | CompilerError = transform.expression.chain(assignment.right)
        referenceOrError match {

          case reference: model.Evaluable.Reference => chainOrError match {

            case chain: model.Expression.Chain => model.Value.Assignment(
              //FIXME we would need to lookup if this is function or value reference
              left = reference.asInstanceOf[model.Value.Reference],
              right = chain
            )
            case error: CompilerError => error
          }
          case error: CompilerError => error
        }
      }

      object declaration {
        //TODO find out if given identifier is a value identifier
        def identifier(id: ast.Evaluable.Identifier): model.Value.Declaration.Identifier | CompilerError = id.value


      }

      def definition(valueDefinition: ast.Value.Definition, context: Contexts.Package | Null
                    ): model.Value.Definition | CompilerError = {
        val imports = context match
          case unit: Contexts.Package => unit.imports
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
                       context: Contexts.Package | Null
                     ): model.Value.Declaration | CompilerError = {
        val imports = context match
          case unit: Contexts.Package => unit.imports
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

    object routine {


      def call(call: ast.Routine.Call.Expression)(context: Context): model.Routine.Call | CompilerError = {
        val referenceOrError: model.Evaluable.Reference | CompilerError = transform.evaluable.reference(call.functionReference)
        referenceOrError match {
          case reference: model.Evaluable.Reference => model.Routine.Call(
            reference = reference,
            arguments = call.arguments.map(argument => transform.routine.call.argument(argument))
          )
          case err: CompilerError => err;
        }
      }


      object call {
        def argument(argument: ast.Routine.Call.Argument): model.Routine.Call.Argument | CompilerError = {
          argument match {
            case positionalArgument: ast.Routine.Call.Positional.Argument => transform.routine.call.argument.positional(positionalArgument)
            case namedArgument: ast.Routine.Call.Named.Argument => transform.routine.call.argument.named(namedArgument)

          }
        }

        object argument {
          //TODO probably we would need to find if parameter we are referencing is contained in given function declaration
          def identifier(id: ast.Identifier.LowerCase): model.Routine.Call.Argument.Identifier | CompilerError = id.text

          def positional(argument: ast.Routine.Call.Positional.Argument): model.Routine.Call.Argument.Positional | CompilerError = {
            val expressionOrError: model.Expression.Chain | CompilerError = transform.expression.chain(argument.expression);
            expressionOrError match {
              case expression: model.Expression.Chain => model.Routine.Call.Argument.Positional(
                expression = expression
              )
              case error: CompilerError => error
            }
          }

          def named(argument: ast.Routine.Call.Named.Argument): model.Routine.Call.Argument.Named | CompilerError = {
            val identifierOrError: model.Routine.Call.Argument.Identifier | CompilerError = transform.routine.call.argument.identifier(argument.identifier)
            val expressionOrError: model.Expression.Chain | CompilerError = transform.expression.chain(argument.expression);
            identifierOrError match {
              case identifier: model.Routine.Call.Argument.Identifier => expressionOrError match {
                case expression: model.Expression.Chain => model.Routine.Call.Argument.Named(
                  identifier = identifier, expression = expression
                )
                case error: CompilerError => error;
              }
              case error: CompilerError => error;
            }
          }
        }
      }

      def declaration(declaration: ast.Routine.Declaration, context: Contexts.Package): model.Routine.Declaration | CompilerError = {
        declaration match {
          case function: ast.Function.Declaration => transform.function.declaration(function)(context)
          case procedure: ast.Procedure.Declaration => transform.procedure.declaration(procedure)(context)
        }
      }


      object declaration {


        //TODO find out if given identifier is a function identifier
        def identifier(id: ast.Evaluable.Identifier): model.Routine.Declaration.Identifier | CompilerError = id.value

        def parameter(parameter: ast.Routine.Declaration.Parameter)(context: Contexts.Routine): model.Routine.Declaration.Parameter | CompilerError = {
          val identifierOrError: String | CompilerError = routine.declaration.parameter.identifier(parameter.identifier)
          val typeReferenceOrError: model.Value.Type.Reference | CompilerError = transform.value.`type`.reference(
            valueTypeReference = parameter.`type`,
            imports = context.parent.imports
          )
          val initializationOrError: base.model.Expression | Null | CompilerError = if (parameter.expression != null)
          then transform.expression(parameter.expression)
          else null;
          identifierOrError match {
            case identifier: String => typeReferenceOrError match {
              case typeReference: model.Value.Type.Reference => initializationOrError match {
                case initialization: base.model.Expression => model.Routine.Declaration.Parameter(
                  identifier = identifier,
                  typeReference = typeReference,
                  initialization = initialization
                )
                case null => model.Routine.Declaration.Parameter(
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
          def identifier(identifier: ast.Identifier.LowerCase): model.Routine.Declaration.Parameter.Identifier = identifier.text
        }
      }

      object definition {

        def body(body: ast.Routine.Definition.Body)(context: Contexts.Routine): model.Routine.Definition.Body | CompilerError = {
          body match {
            case block: ast.Routine.Definition.Block.Body => transform.routine.definition.body.block(block)(context)
            case lambda: ast.Routine.Definition.Lambda.Body => transform.routine.definition.body.lambda(lambda)(context)
          }
        }

        object body {
          def block(body: ast.Routine.Definition.Block.Body)(context: Contexts.Routine): model.Routine.Definition.Block.Body | CompilerError = {
            val result = body.elements.map {
              case chain: Chain.Expression => transform.expression.chain(chain)
              case valueDeclaration: ast.Value.Declaration => transform.value.declaration(
                valueDeclaration = valueDeclaration,
                context = context.parent
              )
              case valueDefinition: ast.Value.Definition => transform.value.definition(
                valueDefinition = valueDefinition,
                context = context.parent
              )
              case valueAssignmentExpression: ast.Value.Assignment.Expression => transform.value.assignment(valueAssignmentExpression)
              case evaluableReference: ast.Evaluable.Reference => transform.evaluable.reference(evaluableReference)
              case functionCallExpression: ast.Routine.Call.Expression => transform.routine.call(call = functionCallExpression)
              case error: ParseError => CompilationError(
                path = error.metadata.location.unit,
                message = error.message,
                startIndex = error.metadata.location.startIndex,
                endIndex = error.metadata.location.endIndex
              )
            }.foldRight((Seq[base.model.Expression | base.model.Statement](), Seq[CompilerError]())) {
              case (result, (valid, errors)) => result match {
                case statement: base.model.Statement => (statement +: valid, errors)
                case expression: base.model.Expression => (expression +: valid, errors)
                case error: CompilerError => (valid, error +: errors)
              }
            }
            transform.`wrap results`(result) {
              items => model.Routine.Definition.Block.Body(items)
            } {
              errors => CompilationErrors("Invalid routine body definition", errors)
            }
          }

          def lambda(body: ast.Routine.Definition.Lambda.Body)(context: Contexts.Routine): model.Routine.Definition.Lambda.Body | CompilerError = {
            val result: compiler.model.Routine.Call
              | compiler.model.Expression.Chain
              | compiler.model.Evaluable.Reference
              | CompilerError = body.element match {
              case chain: Chain.Expression => transform.expression.chain(chain)
              case evaluableReference: ast.Evaluable.Reference => transform.evaluable.reference(evaluableReference)
              case functionCallExpression: ast.Routine.Call.Expression => transform.routine.call(call = functionCallExpression)(context)
              case error: ParseError => CompilationError(
                path = error.metadata.location.unit,
                message = error.message,
                startIndex = error.metadata.location.startIndex,
                endIndex = error.metadata.location.endIndex
              )
            }
            result match {
              case default: (compiler.model.Routine.Call
                | compiler.model.Expression.Chain
                | compiler.model.Evaluable.Reference
                ) => model.Routine.Definition.Lambda.Body(
                expression = default
              )
              case error: CompilerError => error

            }

          }
        }
      }
    }

    object procedure {
      def declaration(declaration: ast.Procedure.Declaration)(context: Contexts.Package): model.Procedure.Declaration | CompilerError = {
        transform.routine.declaration.identifier(declaration.identifier) match {
          case identifier: model.Routine.Declaration.Identifier => {
            val procedureContext: Contexts.Routine = Contexts.Routine(context, identifier)
            val parameters: Seq[model.Routine.Declaration.Parameter | CompilerError] =
              declaration.parameters.map(parameter => transform.routine.declaration.parameter(parameter)(procedureContext)
              )
            model.Procedure.Declaration(
              identifier = identifier,
              parameters = parameters
            )

          }
          case error: CompilerError => error
        }


      }

    }


    object function {

      object declaration {
        //TODO verify if type exists
        def `return type`(returnType: ast.Function.Return.Type)(context: Contexts.Routine): model.Function.Declaration.Return.Type | CompilerError = {
          returnType match {
            case valueTypeReference: ast.Value.Type.Reference => transform.value.`type`.reference(valueTypeReference, context.parent.imports)
          }
        }
      }

      def declaration(declaration: ast.Function.Declaration)(context: Contexts.Package): model.Function.Declaration | CompilerError = {
        val imports: Seq[Import] = context match {
          case unit: Contexts.Package => unit.imports
          case null => Seq[Import]()
        }
        transform.routine.declaration.identifier(declaration.identifier) match {
          case identifier: model.Routine.Declaration.Identifier =>
            declaration.returnTypeReference match {
              case valueType: ast.Value.Type.Reference => value.`type`.reference(valueType, imports);
            } match {
              case returnType: model.Value.Type.Reference => model.Function.Declaration(
                identifier = identifier,
                returnType = returnType,
                parameters = {
                  val routineContext = Contexts.Routine(
                    parent = context, identifier = identifier
                  )
                  declaration.parameters.map(item => transform.routine.declaration.parameter(
                    parameter = item,

                  )(context = routineContext)
                  )
                }
              )
              case error: CompilerError => error
            }
        }

      }


      def definition(definition: ast.Function.Definition, context: Contexts.Package): model.Function.Definition | CompilerError = {
        transform.routine.declaration.identifier(definition.identifier) match {
          case identifier: model.Routine.Declaration.Identifier => {
            val routineContext = Contexts.Routine(
              parent = context, identifier = identifier
            )
            transform.function.declaration.`return type`(definition.returnTypeReference)(routineContext) match {
              case returnType: model.Function.Declaration.Return.Type => model.Function.Definition(
                declaration = model.Function.Declaration(
                  identifier = identifier,
                  parameters = definition.parameters.map(item => transform.routine.declaration.parameter(item)(routineContext)),
                  returnType = returnType
                ),
                body = transform.routine.definition.body(definition.body)(routineContext)
              )
              case returnTypeError: CompilerError => returnTypeError
            }
          }
          case identifierError: CompilerError => identifierError
        }


      }


    }

    object `package` {

      def reference(reference: ast.Package.Reference, context: Contexts.Package | Null = null): model.Package.Reference = {
        model.Package.Reference(
          identifiers = reference.parts.map(item => item.text)
        )
      }

      object declaration {
        def name(name: ast.Package.Identifier): String | CompilationError = {
          return name.text
        }
      }

      def declaration(declaration: ast.Package.Declaration, context: Contexts.Package | Null = null): compiler.model.Package.Declaration | CompilerError = {
        `package`.declaration.name(declaration.identifier) match {
          case string: String => compiler.model.Package.Declaration(
            identifier = string
          )
          case error: CompilerError => error;
        }
      }

      object definition {
        def body(body: ast.Package.Body, context: Contexts.Package | Null = null): compiler.model.Package.Definition.Body | CompilerError = {
          val results = body.elements.map {
            case value: ast.Value.Definition => transform.value.definition(
              valueDefinition = value,
              context = context
            )
          }.foldRight((Seq.empty[model.Value.Definition], Seq.empty[CompilerError])) {
            case (item, (valid, errors)) => item match {
              case valueDefinition: model.Value.Definition => (valueDefinition +: valid, errors)
              case error: CompilerError => (valid, error +: errors)
            }
          }
          transform.`wrap results`(results) {
            items => compiler.model.Package.Definition.Body(items)
          } {
            errors => CompilationErrors("Invalid package definition body", errors)
          }
        }
      }

      def definition(definition: ast.Package.Definition, context: Contexts.Package | Null = null): compiler.model.Package.Definition | CompilerError = {
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

      def declaration(declaration: ast.Module.Declaration, context: Contexts.Package | Null = null): compiler.model.Module.Declaration | CompilerError = {
        return module.declaration.name(declaration.identifier) match {
          case string: String => compiler.model.Module.Declaration(
            identifier = string
          )
          case error: CompilerError => error;
        }
      }

      object definition {
        def body(body: ast.Module.Body, context: Contexts.Package | Null = null): compiler.model.Module.Definition.Body | CompilerError = {
          return compiler.model.Module.Definition.Body(
            elements = body.elements.map {
              case value: ast.Value.Definition => transform.value.definition(valueDefinition = value, context = context)
              case error: ParseError => CompilerBugError(error)
            })
        }
      }

      def definition(definition: ast.Module.Definition, context: Contexts.Package | Null = null): compiler.model.Module.Definition | CompilerError = {
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