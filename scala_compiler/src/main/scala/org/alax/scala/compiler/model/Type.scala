package org.alax.scala.compiler.model

object Type {

  case class Parameter(identifier: Parameter.Identifier )

  object Parameter {
     type Identifier = String;

     abstract class Variance

     object Variances{
       //FIXME from scala perspective this should be called differently
       case class In() extends Variance;
       //FIXME from scala perspective this should be called differently
       case class Out() extends Variance;
     }

  }
}
