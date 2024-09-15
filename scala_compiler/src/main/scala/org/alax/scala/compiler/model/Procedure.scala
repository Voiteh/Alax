package org.alax.scala.compiler.model
import scala.meta.{Decl, Defn, Member, Name, Term, Type}
import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, Expression, ScalaMetaNode, Scope, Statement}
import org.alax.scala.compiler.model
object Procedure {
  case class Declaration(override val identifier: Routine.Declaration.Identifier,
                         parameters: Seq[Routine.Declaration.Parameter | CompilerError] = Seq(),
                        ) extends model.Routine.Declaration(
    identifier = identifier,
    parameters = parameters
  ) {
    override def scala: Decl.Def = Decl.Def(
      mods = List(),
      name = Term.Name(identifier),
      decltpe = Type.Name("scala.Unit"),
      paramClauseGroups = List(
        Member.ParamClauseGroup(
          tparamClause = Type.ParamClause(values = List()),
          paramClauses = List(
            Term.ParamClause(
              values = parameters.filter(item => item.isInstanceOf[model.Routine.Declaration.Parameter])
                .map(item => item.asInstanceOf[model.Routine.Declaration.Parameter])
                .map(item => item.scala).toList
            )

          )
        )
      )

    )

  }

  case class Definition(override val declaration: Declaration, body: Routine.Definition.Body) extends model.Routine.Definition(
    declaration = declaration,
    body = body
  ) {
    override def scala: Defn.Def = Defn.Def(
      mods = declaration.scala.mods,
      name = declaration.scala.name,
      paramClauseGroups = declaration.scala.paramClauseGroups,
      decltpe = Some(declaration.scala.decltpe),
      body = body.scala
    )
  }

}
