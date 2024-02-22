package org.alax.ast.base
import org.alax.ast.base.Node
import org.alax.ast.base.Node.Metadata

abstract class Expression(metadata: Node.Metadata) extends Node(metadata = metadata);



object Expression{
  case class Chain(metadata: Metadata) extends Expression(metadata)
}