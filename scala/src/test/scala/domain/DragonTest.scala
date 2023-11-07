package domain

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class DragonTest extends AnyFreeSpec with Matchers {
  "Furia Nocturna" - {
    "debería tener una velocidad base tres veces mayor que la de la mayoría de los dragones" in {
      val furiaNocturna = new FuriaNocturna(10, CondicionBase, 10)
      val velocidadEsperada = (60 - furiaNocturna.peso) * 3

      furiaNocturna.velocidad() shouldEqual velocidadEsperada
    }

    "debería producir el daño especificado" in {
      val furiaNocturna = new FuriaNocturna(10, CondicionBase, 10)
      val danioEsperado = furiaNocturna.dañoBase

      furiaNocturna.daño() shouldEqual danioEsperado
    }

    "Condicion de Peso" - {
      "debería requerir que el peso del vikingo sea menor a un quinto del peso del dragon" in {
        val furiaNocturna = new FuriaNocturna(10, CondicionBase, 10)
        val vikingoConMenorPeso = new Vikingo(1, 10, 5, 80, null)
        val vikingoConMayorPeso = new Vikingo(3, 10, 5, 20, null)

        furiaNocturna.montablePor(vikingoConMenorPeso) shouldEqual true
        furiaNocturna.montablePor(vikingoConMayorPeso) shouldEqual false

      }
    }

    "Condicion de Barbaridad" - {
      "debería requerir que la barbarosidad del vikingo sea mayor o igual a 10" in {
        val furiaNocturna = new FuriaNocturna(10, new CondicionVikingoBarbaro(10), 10)
        val vikingoConBarbarosidadBaja = new Vikingo(1, 10, 5, 20, SistemaDeVuelo)
        val vikingoConBarbarosidadExacta = new Vikingo(1, 10, 10, 20, null)
        val vikingoConBarbarosidadAlta = new Vikingo(1, 10, 15, 20, null)

        furiaNocturna.montablePor(vikingoConBarbarosidadBaja) shouldEqual false
        furiaNocturna.montablePor(vikingoConBarbarosidadExacta) shouldEqual true
        furiaNocturna.montablePor(vikingoConBarbarosidadAlta) shouldEqual true
      }
    }

    "Chimuelo" - {
      "debería requerir que el vikingo tenga un sistema de vuelo como ítem" in {
        val vikingoConSistemaVuelo = new Vikingo(1, 10, 5, 20, SistemaDeVuelo)
        val vikingoSinSistemaVuelo = new Vikingo(1, 10, 5, 20, null)

        Chimuelo.montablePor(vikingoConSistemaVuelo) shouldEqual true
        Chimuelo.montablePor(vikingoSinSistemaVuelo) shouldEqual false
      }
    }
  }

  "Nadder Mortífero" - {
    "debería tener una velocidad equivalente a su velocidad base menos su propio peso" in {
      val nadderMortifero = new NadderMortifero(10, CondicionBase)
      val velocidadEsperada = 60 - nadderMortifero.peso

      nadderMortifero.velocidad() shouldEqual velocidadEsperada
    }

    "debería producir un daño fijo de 150" in {
      val nadderMortifero = new NadderMortifero(10, CondicionBase)

      nadderMortifero.daño() shouldEqual 150
    }

    "debería requerir que el daño del vikingo no supere el suyo" in {
      val nadderMortifero = new NadderMortifero(10, CondicionBase)
      val vikingoConMenorDanio = new Vikingo(1, 10, 5, 20, Hacha)
      val vikingoConMayorDanio = new Vikingo(1, 10, 50, 20, Maza)

      nadderMortifero.montablePor(vikingoConMenorDanio) shouldEqual true
      nadderMortifero.montablePor(vikingoConMayorDanio) shouldEqual false
    }
  }

  "Gronckle" - {
    "debería tener una velocidad equivalente a la mitad de su velocidad base menos su propio peso" in {
      val gronckle = new Gronckle(10, CondicionBase, 10)
      val velocidadEsperada = (60 - gronckle.peso) / 2

      gronckle.velocidad() shouldEqual velocidadEsperada
    }

    "debería producir un daño de 5 veces su peso" in {
      val gronckle = new Gronckle(10, CondicionBase, 10)
      val danioEsperado = 5 * gronckle.peso

      gronckle.daño() shouldEqual danioEsperado
    }

    "debería requerir que el vikingo no supere un peso determinado" in {
      val gronckle = new Gronckle(1000, CondicionBase, 10)
      val vikingoConMenorPeso = new Vikingo(1, 10, 5, 80, null)
      val vikingoConMayorPeso = new Vikingo(100, 10, 5, 20, null)

      gronckle.montablePor(vikingoConMenorPeso) shouldEqual true
      gronckle.montablePor(vikingoConMayorPeso) shouldEqual false
    }
  }
}
