package org.alax.ast

import org.alax.ast.base

case object Container {
  type Reference = Value.Type.Reference|Package.Reference;
}
