package org.alax.scala.compiler.base.model
import org.alax.scala.compiler.model.Package
case class Import(ancestor: Package.Reference, member: String, alias: String | Null = null) extends Statement {

  val text: String = if (alias != null) then s"${ancestor.text}.$alias" else s"${ancestor.text}.$member";


  override def toString : String = text;


  override def equals(obj: Any): Boolean = {
    return obj match {
      case Import(ancestor, member, alias) => this.ancestor.equals(ancestor) && 
        Option(this.alias)
          .zip(Option(alias)).map((item:(Any, Any))=> item._1.equals(item._2))
          .getOrElse(this.member.equals(member))
      
      case _ => false;
    }
  }


}
