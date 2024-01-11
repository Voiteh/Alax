package test.org.alax.scala.complier.transformation.model.to.file.fixture

import org.alax.ast
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Import, Trace, Type}
import org.alax.scala.compiler.model.{Literals, Value as CompilerValue}
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.model.to.file.Contexts
import os.Path
import test.org.alax.scala.complier.transformation.model.to.file.fixture.Model.Package.Declaration

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Model {

  object Context {
    val module = Contexts.Module(Module.Declaration.`module abc.def`)
    val `package` = Contexts.Package(Package.Declaration.`package efg`, module);
    val `sub package` = Contexts.Package(Package.Declaration.`package hij`, `package`)
  }

  object Import {
    val `scala.lang.Integer`: Import = compiler.base.model.Import(
      ancestor = "scala.lang",
      member = "Integer"
    );
    val `java.lang.Integer as JInteger`: Import = compiler.base.model.Import(
      ancestor = "java.lang",
      member = "Integer", alias = "JInteger"
    );
    val `scala.lang.Integer as JInteger`: Import = compiler.base.model.Import(
      ancestor = "scala.lang",
      member = "Integer", alias = "JInteger"
    );
  }


  object Value {


    object Declaration {
      val `int: scala.lang.Integer` = compiler.model.Value.Declaration(
        name = "int",
        `type` = compiler.model.Value.Type.Reference(
          id = Type.Id(
            value = "scala.lang.Integer"
          )
        )
      )
    }

    object Definition {
      val `val int: scala.lang.Integer = 4` = compiler.model.Value.Definition(
        declaration = Declaration.`int: scala.lang.Integer`,
        meaning = Literals.Integer(4L)
      )
    }

  }

  object Module {
    object Declaration {
      val `module abc.def` = compiler.model.Module.Declaration(
        name = "abc.def"
      )
    }

    object Definition {
      val `module abc.def { int: scala.lang.Integer; }` = compiler.model.Module.Definition(
        declaration = Declaration.`module abc.def`,
        body = compiler.model.Module.Definition.Body(elements = Seq(
          Model.Value.Definition.`val int: scala.lang.Integer = 4`
        ))
      )
    }
  }

  object Package {
    object Declaration {
      val `package efg` = compiler.model.Package.Declaration(
        name = "efg"
      )
      val `package hij` = compiler.model.Package.Declaration(
        name = "hij"
      )
    }

    object Definition {
      val `package hij { int: scala.lang.Integer; }` = compiler.model.Package.Definition(
        declaration = Declaration.`package hij`,
        body = compiler.model.Package.Definition.Body(elements = Seq(
          Model.Value.Definition.`val int: scala.lang.Integer = 4`
        ))
      )
    }


  }

}