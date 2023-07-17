package test.org.alax.scala.complier.transformer

import org.alax.ast.model as ast
import ast.statements.declarations.Value as ValueDeclaration
import org.alax.scala.compiler as compiler
import scala.annotation.targetName

object fixture {

  object context {
    val emptyModule = compiler.model.Context.Module(compiler.model.Context.Project())
    val emptyPackage = compiler.model.Context.Package(emptyModule)
    val emptyUnit = compiler.model.Context.Unit(emptyPackage)
    val unitWithImport = compiler.model.Context.Unit(emptyPackage, Seq(statement.`import`.`scala.lang.Integer`))
    val unitWithImportAndAlias = compiler.model.Context.Unit(emptyPackage, Seq(
      statement.`import`.`scala.lang.Integer`,
      statement.`import`.`java.lang.Integer as JInteger`
    ))
  }

  object statement {
    object `import` {
      val `scala.lang.Integer`: compiler.model.Import = compiler.model.Import(
        `package` = "scala.lang",
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: compiler.model.Import = compiler.model.Import(
        `package` = "java.lang",
        member = "Integer",alias = "JInteger"
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
