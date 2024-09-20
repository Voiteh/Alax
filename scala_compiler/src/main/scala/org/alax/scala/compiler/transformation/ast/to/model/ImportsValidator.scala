package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base.model.{CompilationError, CompilerError, Import, Tracable}

object ImportsValidator {


  def validate(imports: Seq[Tracable[Import]]): Seq[CompilerError] = {
    val ancestralGroups = imports.groupBy(tracable => tracable.transformed.ancestor);
    val memberAliasGroups = imports.groupBy(tracable => if tracable.transformed.alias != null
    then tracable.transformed.alias else tracable.transformed.member)
    val ancestorsErrors = ancestralGroups.filter(tuple => tuple._2.length > 1)
      .flatMap(tuple => tuple._2)
      .map(tracable => CompilationError(
        trace = tracable.trace, message = f"Duplicate ancestor import definition for: ${tracable.transformed.ancestor} "
      ));
    val membersAliasesErrors = memberAliasGroups.filter(tuple => tuple._2.length > 1)
      .flatMap(tuple => tuple._2).map(tracable => new CompilationError(
        trace = tracable.trace,
        message = f"Duplicate ${if tracable.transformed.alias != null 
        then f"alias: ${tracable.transformed.alias}" 
        else s"member: ${tracable.transformed.member}" } definition for ancestor: ${tracable.transformed.ancestor}"
      ));
    return ancestorsErrors.toSeq ++ membersAliasesErrors.toSeq;
  }


}
