package test.org.alax.scala.complier.transformer

import org.alax.ast.model as ast
import ast.statements.declarations.Value as ValueDeclaration
import org.alax.scala.compiler as compiler
import scala.annotation.targetName

object fixture {
  object statement {
    object `import` {
      val `scala.lang.Integer`: compiler.model.Import = compiler.model.Import(
        items = Seq("scala.lang.Integer")
      );
      val `java.lang.Integer`: compiler.model.Import = compiler.model.Import(
        items = Seq("java.lang.Integer")
      );
    }
  }

  object value {
    object declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.partials.names.LowerCase(
          value = "int",
          metadata = ast.node.Metadata(ast.node.Location.unknown)
        ),
        `type` = ast.partials.types.Value(
          id = ast.partials.names.UpperCase("Integer", ast.node.Metadata(ast.node.Location.unknown)),
          metadata = ast.node.Metadata(ast.node.Location.unknown)
        ),
        metadata = ast.node.Metadata(ast.node.Location.unknown)
      )
    }
  }

}
