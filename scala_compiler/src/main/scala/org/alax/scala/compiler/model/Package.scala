package org.alax.scala.compiler.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{CompilerError, ScalaMetaNode, Scope}

import scala.meta.{Defn, Pkg, Self, Stat, Template, Term, Tree}
import scala.meta.Term.Name

object Package {

  class Reference(identifiers: Seq[Package.Declaration.Identifier]) extends base.model.Reference {


    def select(current: Package.Declaration.Identifier, rest: Seq[Package.Declaration.Identifier]): Term.Select | Term.Name =
      if rest.isEmpty then Term.Name(current) else Term.Select(
        qual = select(rest.find(_ => true).get, rest),
        name = Term.Name(current)
      );

    override val scala: Term.Select | Term.Name = this.select(
      current = identifiers.find(_ => true).get,
      rest = identifiers.drop(1)
    )
  }

  object Declaration {
    type Identifier = base.model.Declaration.Identifier;
  }

  case class Declaration(override val identifier: Package.Declaration.Identifier) extends base.model.Declaration(identifier = identifier) {
    override def scala: Name = Name(identifier)
  }

  case class Definition(override val declaration: Package.Declaration, body: Package.Definition.Body) extends base.model.Definition(declaration = declaration, meaning = body) {
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