package org.alax.scala.compiler.base.model

import org.alax.scala.compiler.transformation.Context

/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Source[C <: Context](
                                     val name: String,
                                     val members: Seq[Statement],
                                     val errors: Seq[CompilerError],
                                     val context: Context | Null
                                   ) {


  def scala: meta.Tree = meta.Source(
    stats = members.map(statement => statement.scala).toList
  )
}