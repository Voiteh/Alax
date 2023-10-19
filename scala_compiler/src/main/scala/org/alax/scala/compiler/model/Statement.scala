package org.alax.scala.compiler.model

import scala.meta.Stat

/**
 * Something that is fully blown language construct, it is final form, statements are separated by semi-colons
 */
abstract class Statement extends ScalaMetaNode {
  def scala: Stat = ??? ;
}
