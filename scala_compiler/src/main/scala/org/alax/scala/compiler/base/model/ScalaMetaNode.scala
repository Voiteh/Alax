package org.alax.scala.compiler.base.model

import scala.meta.{Stat, Tree,Type}

trait ScalaMetaNode {
  def scala: Type.Ref|Tree|List[Tree]|List[Type.Ref];


}
