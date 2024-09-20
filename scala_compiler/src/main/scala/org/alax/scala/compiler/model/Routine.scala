package org.alax.scala.compiler.model

import scala.meta.Term
import scala.meta.{Decl, Defn, Member, Name, Term, Type}
import org.alax.scala.compiler.base
import org.alax.scala.compiler.model
import org.alax.scala.compiler.base.model.Declaration.Identifier
import org.alax.scala.compiler.base.model.{CompilerError, Expression, ScalaMetaNode, Scope, Statement}

object Routine {

  abstract class Declaration(override val identifier: Identifier, parameters: Seq[model.Routine.Declaration.Parameter|CompilerError])
    extends base.model.Declaration(
      identifier = identifier
    )

  abstract class Definition(override val declaration: Declaration, body: Definition.Body|CompilerError) extends base.model.Definition(
    declaration = declaration, meaning = body
  )

  object Definition {

    abstract class Body(expressions: Seq[Expression | Statement | CompilerError]) extends Scope {
      override def scala: Term;

    }

    object Block {
      case class Body(elements: Seq[Expression | Statement]) extends Routine.Definition.Body(expressions = elements) {


        override def scala: Term = Term.Block(
          stats = elements.map {
            case statement: Statement => statement.scala
            case expression: Expression => expression.scala
          }.toList
        )
      }
    }

    object Lambda {
      case class Body(expression: Expression) extends Routine.Definition.Body(expressions = Seq(expression)) {
        override def scala: Term = expression.scala
      }
    }
  }

  object Declaration {
    type Identifier = base.model.Declaration.Identifier

    object Parameter {
      type Identifier = String
    }

    case class Parameter(
                          identifier: Parameter.Identifier,
                          typeReference: Value.Type.Reference,
                          initialization: Expression | Null = null
                        )
      extends ScalaMetaNode {
      override def scala: Term.Param = Term.Param(
        mods = List(),
        name = Name(identifier),
        decltpe = Some(
          Type.Name(typeReference.id.value)
        ),
        default = initialization match {
          case expression: Expression => Some(expression.scala)
          case null => None
        }
      )
    }


  }

  //TODO write tests for that
  case class Call(reference: Evaluable.Reference, arguments: Set[Routine.Call.Argument]= Set())
    extends base.model.Expression {
    override def scala: Term.Apply = Term.Apply(
      fun = reference.scala, argClause = Term.ArgClause(values = arguments.toList
        .map(item => item.scala), mod = None)
    )
  }

  object Call {
    abstract class Argument extends ScalaMetaNode {
      override val scala: Term;
    }



    object Argument {
      type Identifier = String;

      case class Positional(expression: Expression, position:Int) extends Argument() {
        override val scala: Term = expression.scala;
      }

      case class Named(identifier: Argument.Identifier, expression: Expression) extends Argument() {
        override val scala: Term.Assign = Term.Assign(lhs = Term.Name(identifier), rhs = expression.scala)
      }
    }


  }
}
