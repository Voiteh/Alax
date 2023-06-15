package org.alax.ast

package object model {


  class ParseError(
                    val compilationUnit: String,
                    val startIndex: Int,
                    val endIndex: Int,
                    message: String,
                    cause: Throwable | Null = null
                  )
    extends Exception(message, cause)

  abstract class Node;

  abstract class Statement extends Node;

  abstract class Expression extends Node;

  abstract class Partial extends Node;

  abstract class CompilationUnit(val path :String); // for now String but this needs to be system agnostic
  
  object unit{
      
      case class Package(
          path:String, 
      val imports: Seq[statements.Import]   
      )
       extends CompilationUnit(path=path);

      
      case class Type(
          path:String, 
          val imports: Seq[statements.Import]      
      ) extends CompilationUnit(path=path);
      
      
      
  }
  /**
   * Can be used as parts of statements and expressions
   */
  object partials {
    trait Type extends Partial;

    /**
     * Identifiers of the statements and expressions
     */
    trait Name extends Partial;

    object types {
      case class Value(id: String) extends partials.Type;
    }

    object names {
      case class LowerCaseName(val value: String) extends partials.Name;

      case class UpperCaseName(val value: String) extends  partials.Name;
    }

  }


  object statements {
    abstract class Declaration extends Statement;
    class Import() extends Statement;
    
    object declarations{
      case class Value(
        val name: partials.names.LowerCaseName,
        val `type`: partials.Type
      ) extends statements.Declaration;

      case class ValueWithInitialization(
        val name: partials.names.LowerCaseName,
        val `type`: partials.Type,
        val initialization: model.Expression
      ) extends statements.Declaration;
    }
  }

  object expressions {

    abstract class Literal extends Expression;

    object literals {
      case class Boolean(val value: java.lang.Boolean) extends Literal;

      case class Character(val value: java.lang.Character) extends Literal;

      case class Integer(val value: java.lang.Integer) extends Literal;

      case class Float(val value: java.lang.Double) extends Literal;

      case class String(val value: java.lang.String) extends Literal;
    }

  }


}

