package org.alax.scala.compiler.model

import scala.meta.Term
import org.alax.scala.compiler.base

object Evaluable {
  //TODO for now we are not able to distinguish if provided reference is value or function, we need to have member list for that.
  case class Reference(`package`: Package.Reference | Null = null, identifier: Value.Declaration.Identifier | Function.Declaration.Identifier)
    extends base.model.Reference {

    override val scala: Term.Name | Term.Select =
      if `package` == null then Term.Name(identifier)
      else Term.Select(qual = `package`.scala, name = Term.Name(identifier))

  }

}
