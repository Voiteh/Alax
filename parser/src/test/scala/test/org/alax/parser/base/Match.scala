package test.org.alax.parser.base

import org.alax.ast.base.Node

case class Match(text: String, node: Node) {
  override def toString: String = text
}
