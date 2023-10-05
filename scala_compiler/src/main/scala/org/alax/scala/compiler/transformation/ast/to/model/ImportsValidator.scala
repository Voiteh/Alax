package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.model.{CompilationError, Import, Tracable}

object ImportsValidator {


  def validate(imports: Seq[Tracable[Import]]): Seq[CompilationError] = {
    val ancestralGroups = imports.groupBy(tracable => tracable.transformed.ancestor);
    val memberAliasGroups = imports.groupBy(tracable => if tracable.transformed.alias != null
    then tracable.transformed.alias else tracable.transformed.member)
    val ancestorsErrors = ancestralGroups.filter(tuple => tuple._2.length > 1)
      .flatMap(tuple => tuple._2)
      .map(tracable => new CompilationError(
        path = tracable.trace.unit, startIndex = tracable.trace.startIndex, endIndex = tracable.trace.endIndex, message = f"Duplicate ancestor import definition for: ${tracable.transformed.ancestor} "
      ));
    val membersAliasesErrors = memberAliasGroups.filter(tuple => tuple._2.length > 1)
      .flatMap(tuple => tuple._2).map(tracable => new CompilationError(
      path = tracable.trace.unit,
      startIndex = tracable.trace.startIndex,
      endIndex = tracable.trace.endIndex,
      message = f"Duplicate ${if tracable.transformed.alias != null then "alias: " + tracable.transformed.alias else "member: " + tracable.transformed.member} definition for ancestor: ${tracable.transformed.ancestor}"
    ));
    return ancestorsErrors.toSeq ++ membersAliasesErrors.toSeq;
  }


}
