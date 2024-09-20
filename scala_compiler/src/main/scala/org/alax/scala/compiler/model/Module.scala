package org.alax.scala.compiler.model


import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.{Ctor, Decl, Defn, Mod, Name, Pkg, Self, Stat, Template, Tree, Type}
import scala.meta.Type.ParamClause
import scala.meta.contrib.DocToken.Constructor

object Module {
  case class Declaration(override val identifier: String) extends base.model.Declaration(identifier = identifier) {
    override def scala: Decl.Type = {
      return Decl.Type(
        name = Type.Name(identifier), mods = List(),
        tparamClause = ParamClause(List()),
        bounds = Type.Bounds(Option.empty, Option.empty)
      )
    }
  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {
    override def scala: Defn.Class = Defn.Class(
      mods = List(),
      name = Type.Name(declaration.identifier),
      tparamClause = ParamClause(List()),
      ctor = Ctor.Primary(
        mods = List(),
        name = Name.Anonymous(),
        paramClauses = Seq()
      ),
      templ = Template(
        self = Self(name = Name.Anonymous(), decltpe = Option.empty),
        early = List(),
        inits = List(),
        stats = body.scala,
        derives = List()
      )
    )

  }

  object Definition {
    case class Body(elements: Seq[Body.Element]) extends Scope {
      override def scala: List[Stat] = elements.filter(item => item.isInstanceOf[Body.Element])
        .map((item: Body.Element) => item.scala)
        .toList

    }

    object Body {
      type Element = Value.Definition
    }
  }
}
