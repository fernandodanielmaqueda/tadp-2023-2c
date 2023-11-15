import org.scalatest.matchers.should.Matchers._
import org.scalatest.freespec.{AnyFreeSpec}

class ProjectSpec extends AnyFreeSpec {

  "Este proyecto" - {

    "cuando está correctamente configurado" - {
      "debería resolver las dependencias y pasar este test" in {
        //Prueba.materia shouldBe "tadp"
      }
    }
  }

}

// https://docs.scala-lang.org/getting-started/intellij-track/testing-scala-in-intellij-with-scalatest.html
/*
import org.scalatest.funsuite.AnyFunSuite

  class PruebaTest extends AnyFunSuite {
  test("Prueba.materia") {
    assert(Prueba.materia == "tadp")
  }
}*/
