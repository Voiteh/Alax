package test.org.alax.scala.complier.transformation.ast.to.model
import org.alax.scala.compiler.model.Tracable
import org.alax.scala.compiler.transformation.ast.to.model.ImportsValidator
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers.{a, have, must, mustBe, size}
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Model

import scala.language.postfixOps

class ImportValidatorTest extends AnyWordSpec{

  "duplicate main ancestor imports" when {
    val imports=Model.Context.Imports.`duplicate main ancestor imports`;
    "wrapped into Tracable and validated, then" must{
      val tracables=imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`,transformed = item ));
      "return errors" in {
        val result=ImportsValidator.validate(tracables);
        result must have size 2;
      }
    }
  }
  "duplicate nested ancestor imports" when {
    val imports = Model.Context.Imports.`duplicate nested ancestor imports`;
    "wrapped into Tracable and validated, then" must {
      val tracables = imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`, transformed = item));
      "return errors" in {
        val result = ImportsValidator.validate(tracables);
        result must have size 2;
      }
    }
  }
  "duplicate nested with different top ancestor imports" when {
    val imports = Model.Context.Imports.`duplicate nested with different top ancestor imports`;
    "wrapped into Tracable and validated, then" must {
      val tracables = imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`, transformed = item));
      "return empty sequence" in {
        val result = ImportsValidator.validate(tracables);
        result must have size 0;
      }
    }
  }


  "duplicate member imports" when {
    val imports = Model.Context.Imports.`duplicate member imports`;
    "wrapped into Tracable and validated, then" must {
      val tracables = imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`, transformed = item));
      "return errors" in {
        val result = ImportsValidator.validate(tracables);
        result must have size 2;
      }
    }
  }
  "mixed non duplicate members, duplicate member imports " when {
    val imports = Model.Context.Imports.`duplicate member imports` ++ Model.Context.Imports.`non duplicating imports ` ;
    "wrapped into Tracable and validated, then" must {
      val tracables = imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`, transformed = item));
      "return errors" in {
        val result = ImportsValidator.validate(tracables);
        result must have size 2;
      }
    }
  }

  "mixed duplicate members with duplicate ancestors" when {
    val imports = Model.Context.Imports.`duplicate member imports` ++ Model.Context.Imports.`duplicate nested ancestor imports`;
    "wrapped into Tracable and validated, then" must {
      val tracables = imports
        .map(item => new Tracable(trace = Model.Context.Trace.`empty trace`, transformed = item));
      "return errors" in {
        val result = ImportsValidator.validate(tracables);
        result must have size 5;
      }
    }
  }

}
