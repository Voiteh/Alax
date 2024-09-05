package test.org.alax.scala.compiler.transformation.model.to.file

import org.alax.scala.compiler.base.model.Virtual
import org.alax.scala.compiler.model.Package
import org.alax.scala.compiler.transformation.model.to.file.{Contexts, ModelToFileTransformer}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.model.to.file
import test.org.alax.scala.compiler.transformation.model.to.file.fixture

import java.io.{File, PrintWriter}
import java.nio.file.attribute.FileAttribute
import java.nio.file.{Files, Path, Paths}

class ModuleToFileTransformationTest extends AnyWordSpec with BeforeAndAfterEach {
  var transformer: ModelToFileTransformer | Null = null;
  var tempDir: File | Null = null;

  override def beforeEach(): Unit = {
    val file = Files.createTempDirectory(null).toFile;
    file.mkdirs()
    assert(file.exists())
    assert(file.canWrite)
    transformer = new ModelToFileTransformer(file.toPath)
    tempDir = file
  }


  override def afterEach(): Unit = {
    import scala.util.Try

//    Delete the directory and its contents recursively
    tempDir match {
      case dir: File => Try(Files.walk(dir.toPath))
        .foreach { stream =>
          try {
            stream.sorted(java.util.Comparator.reverseOrder()).forEach(Files.delete)
          } finally {
            stream.close()
          }
        }
    }


  }

  "model module definition" when {

    "module with value definition" must {
      "transform to file" in {
        val result: Virtual[File] = transformer.transform.module(
          definition = fixture.Model.Module.Definition.`module abc.def { int: scala.lang.Integer; }`
        )
        val file = result.realize;
        inside(tempDir){
          case folder:File => {
            assert(file.toPath.startsWith(folder.toPath()))
            file.toPath.endsWith("abc/def/module.scala") mustBe true
          }
        }

      }
    }
  }
}