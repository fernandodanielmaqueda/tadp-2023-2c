package domain

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class VikingoTest extends AnyFreeSpec with Matchers {
  "un vikingo tiene los atributos deseados (peso, velocidad, barbarosidad, hambre, y un objeto)" in {
    val vikingo = new Vikingo(1, 1, 1, 1, null)
    assert(vikingo.peso !== None)
    assert(vikingo.velocidad !== None)
    assert(vikingo.barbarosidad !== None)
    assert(vikingo.hambre !== None)
    assert(vikingo.objeto !== None)
  }

  "un vikingo no puede tener peso negativo" in {
    assertThrows[IllegalArgumentException] {
      new Vikingo(-1, 1, 1, 1, null)
    }
  }

  "un vikingo no puede superar el 100% de hambre" in {
    val vikingo = new Vikingo(1, 1, 1, 1, null)
    vikingo.participarEnPosta(1000)
    assert(vikingo.hambre == 100)
  }

  "Hipo" - {
    "debería llevar un sistema de vuelo" in {
      Hipo.objeto shouldEqual SistemaDeVuelo
    }
  }

  "Astrid" - {
    "debería llevar un hacha que aumenta el daño en 30 puntos" in {

      Astrid.objeto shouldEqual Hacha
      Astrid.barbarosidad shouldEqual 2
      Astrid.daño() shouldEqual 32 // 2 (barbarosidad) + 30 (bonus del hacha)
    }
  }

  "Patán" - {
    "debería llevar una maza que suma 100 puntos de daño" in {
      Patán.objeto shouldEqual Maza
      Patán.daño() shouldEqual 103 // 3 (barbarosidad) + 100 (bonus de la maza)
    }
  }

  "Patapez" - {
    "debería llevar un ítem comestible y reducir su hambre al participar en una posta" in {
      Patapez.hambre = 0
      val carrera = new Carrera(10)

      carrera.participar(List(Patapez))

      assert(Patapez.objeto.isInstanceOf[Comestible])
      Patapez.objeto.modificador() shouldEqual 5
      Patapez.hambre shouldEqual 15 // 20 - 5 (reducción de hambre por el pan)
    }

    "debería incrementar su hambre el doble al participar de una posta" in {

      Patapez.hambre = 0
      Combate.participar(List(Patapez))
      Patapez.hambre shouldEqual 15 // 20 - 5 = 15
    }
  }

}
