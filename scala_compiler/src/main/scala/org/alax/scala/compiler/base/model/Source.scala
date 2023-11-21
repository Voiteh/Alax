package org.alax.scala.compiler.base.model


/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Source(
                       val name: String,
                       val members: Seq[Statement],
                       val errors: Seq[CompilerError],
                     ) {


  def scala: meta.Tree = meta.Source(
    stats = members.map(statement => statement.scala).toList
  )
}