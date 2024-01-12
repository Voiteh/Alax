package org.alax.scala.compiler.model


import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.Type
import scala.meta.{Defn, Pkg, Self, Stat, Template, Tree, Ctor, Mod, Name}
import scala.meta.Type.ParamClause
import scala.meta.contrib.DocToken.Constructor

object Module {
  case class Declaration(override val name: String) extends base.model.Declaration(name = name) {
    override def scala: Type.Name = {
      return Type.Name(name)
    }
  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {
    override def scala: Defn.Class = Defn.Class(
      mods = List(),
      name = declaration.scala,
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
    case class Body(elements: Seq[Body.Element | CompilerError]) extends Scope {
      override def scala: List[Stat] = elements.filter(item => item.isInstanceOf[Body.Element])
        .map(item => item.asInstanceOf[Body.Element])
        .map((item: Body.Element) => item.scala)
        .toList


    }

    object Body {
      type Element = Value.Definition
    }
  }
}
