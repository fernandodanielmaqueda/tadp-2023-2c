package domain

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class PostaTest extends AnyFreeSpec with Matchers {

  "Pesca" - {
    val vikingo1 = new Vikingo(100, 10, 5, 20, null)
    val vikingo2 = new Vikingo(120, 8, 7, 15, null)

    "debería incrementar el hambre en un 5%" in {
      Pesca.participar(List(vikingo1))

      vikingo1.hambre shouldEqual 25 // 20 + 5 = 25
    }

    "debería evaluar al mejor pescador" in {
      val ganador = Pesca.participar(List(vikingo1, vikingo2)).head

      ganador shouldEqual vikingo2
    }
  }

  "Combate" - {
    val vikingo1 = new Vikingo(100, 10, 5, 20, null)
    val vikingo2 = new Vikingo(120, 8, 7, 15, null)

    "debería incrementar el hambre en un 10%" in {
      Combate.participar(List(vikingo1))

      vikingo1.hambre shouldEqual 30 // 20 + 10 = 30
    }

    "debería evaluar al mejor luchador" in {
      val ganador = Combate.participar(List(vikingo1, vikingo2)).head

      ganador shouldEqual vikingo2
    }
  }

  "Carrera" - {
    val vikingo1 = new Vikingo(100, 10, 5, 20, null)
    val vikingo2 = new Vikingo(120, 8, 7, 15, null)
    val carrera = new Carrera(10) // Carrera de 10 km

    "debería incrementar el hambre en función de la distancia recorrida" in {
      carrera.participar(List(vikingo1))

      vikingo1.hambre shouldEqual 30 // 20 + 10 = 30
    }

    "debería evaluar al vikingo más veloz" in {
      val ganador = carrera.participar(List(vikingo1, vikingo2)).head

      ganador shouldEqual vikingo1
    }
  }
}
