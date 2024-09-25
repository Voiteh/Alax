package org.alax.scala.compiler.transformation.ast.to.model

case class UnionAccumulator[Left, Right](left: Seq[Left] = Seq.empty, right: Seq[Right] = Seq.empty) {

}
