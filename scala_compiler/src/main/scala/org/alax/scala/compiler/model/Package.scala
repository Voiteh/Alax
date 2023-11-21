package org.alax.scala.compiler.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.{Defn, Self, Stat, Template, Tree}
import scala.meta.Term.Name

object Package {

  case class Declaration(override val name: String) extends base.model.Declaration(name = name) {
    override def scala: Name = {
      return Name(name)
    }
  }

  case class Definition(override val declaration: Declaration, body: Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {


    override def scala: Defn.Object = {
      return Defn.Object(mods = List(), name = declaration.scala, templ = body.scala)
    }

  }

  object Definition {
    class Body(elements: Seq[Body.Element|CompilerError]) extends Scope {
      override def scala: Template = {
        return Template(early = List(), inits = List(),
          self = Self(
            name = Name(""),
            decltpe = Option.empty
          ), stats = elements
            .filter(item => item.isInstanceOf[Body.Element])
            .map(item => item.asInstanceOf[Body.Element].scala)
            .toList, derives = List()
        )
      }
    }

    object Body {
      type Element = Value.Definition
    }
  }
}
