package org.alax.scala.compiler.model


import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.Type
import scala.meta.{Defn, Pkg, Self, Stat, Template, Tree}
import scala.meta.Term.Name

object Module {
  case class Declaration(override val name: String) extends base.model.Declaration(name = name) {
    override def scala: Name = {
      return Name(name)
    }
  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {
    override def scala: Defn.Object = Defn.Object(
      mods = List(),
      name = declaration.scala,
      templ = Template(
        self = Self(name = declaration.scala, decltpe = Option.empty),
        early = List(),
        inits = List(),
        stats = body.scala,
        derives = List(
          Type.With(lhs = Type.Name(value = "mill.RootModule"), rhs = Type.Name(value = "mill.ScalaModule "))
        )
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
