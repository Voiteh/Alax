package org.alax.scala.compiler.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.{Defn, Pkg, Self, Stat, Template, Tree}
import scala.meta.Term.Name

object Package {

  case class Declaration(override val name: String) extends base.model.Declaration(name = name) {
    override def scala: Name = {
      return Name(name)
    }
  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {
    override def scala: Pkg = Pkg(declaration.scala, body.scala)

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