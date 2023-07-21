package org.alax.scala.compiler.model

class Tracable[Transformed](
                             val trace: Trace,
                             val transformed: Transformed
                           )
