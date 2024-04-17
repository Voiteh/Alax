package org.alax.scala.compiler.model

import scala.meta.{Decl, Defn, Member, Name, Term, Type}
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Expression, ScalaMetaNode}

object Function {



  case class Call(reference: Evaluable.Reference, arguments: Seq[Function.Call.Argument]) extends ScalaMetaNode {
    override def scala: Term.Apply = Term.Apply(
      fun = reference.scala, argClause = Term.ArgClause(values = arguments.toList.map(item => item.scala), mod = None)
    )
  }

  object Call {
    abstract class Argument() extends ScalaMetaNode{
      override val scala: Term;
    }

    object Argument {
      type Identifier = String;
    }

    object Arguments {
      case class Positional(expression: Expression) extends Argument() {
        override val scala: Term = expression.scala;
      }

      case class Named(name: Argument.Identifier, expression: Expression) extends Argument() {
        override val scala: Term.Assign = Term.Assign(lhs = Term.Name(name), rhs = expression.scala)
      }
    }

  }

  object Declaration {
    type Identifier = model.Declaration.Identifier
  }

  case class Declaration(override val identifier: Function.Declaration.Identifier,
                         parameters: Seq[Parameter],
                         returnType: Value.Type.Reference | Null = null) extends model.Declaration(
    identifier = identifier
  ) {
    override def scala: Decl.Def = Decl.Def(
      mods = List(),
      name = Term.Name(identifier),
      decltpe = Type.Name(returnType.id.value),
      paramClauseGroups = List(
        Member.ParamClauseGroup(
          tparamClause = Type.ParamClause(values = List()),
          paramClauses = List(
            Term.ParamClause(
              values = parameters.map(item => item.scala).toList
            )

          )
        )
      )

    )

  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends model.Definition(
    declaration = declaration,
    meaning = body
  ) {
    override def scala: Defn.Def = Defn.Def(
      mods = declaration.scala.mods,
      name = declaration.scala.name,
      paramClauseGroups = declaration.scala.paramClauseGroups,
      decltpe = Some(declaration.scala.decltpe),
      body = body.scala
    )
  }

  object Parameter {
    type Identifier = String
  }

  case class Parameter(identifier: Parameter.Identifier, `type`: Value.Type.Reference, initialization: Expression | Null = null) extends ScalaMetaNode {
    override def scala: Term.Param = Term.Param(
      mods = List(),
      name = Name(identifier),
      decltpe = Some(
        Type.Name(`type`.id.value)
      ),
      default = initialization match {
        case expression: Expression => Some(expression.scala)
        case null => None
      }
    )
  }

  object Definition {

    abstract class Body(expressions: Seq[Expression]) extends ScalaMetaNode {
      override def scala: Term;
    }

    object Bodies {
      case class BlockBody(expressions: Seq[Expression]) extends Function.Definition.Body(expressions = expressions) {
        override def scala: Term = Term.Block(
          stats = expressions.map(item => item.scala).toList
        )
      }

      case class LambdaBody(expression: Expression) extends Function.Definition.Body(expressions = Seq(expression)) {
        override def scala: Term = expression.scala
      }
    }
  }
}
