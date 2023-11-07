package domain

import domain.festival.{Barbarosidad, Daño, Hambre, kg, km_h}


class Vikingo(val peso: kg, val velocidad: km_h, val barbarosidad: Barbarosidad, var hambre: Hambre, val objeto: Objeto) extends PosibleCompetidor {
  require(Range.inclusive(0, 100).contains(hambre), "El nivelDeHambre debe ser un porcentaje valido")
  def pescaMaxima(): Double = peso * 0.5 + this.barbarosidad * 2

  def daño(): Daño = {
    objeto match {
      case arma: Arma => this.barbarosidad + arma.modificador()
      case _ => this.barbarosidad
    }
  }

  def participarEnPosta(porcentajeDeHambre: Hambre): Unit = {
    require(porcentajeDeHambre >= 0, "El nivel Debe ser positivo")
    hambre += porcentajeDeHambre
    if (hambre >= 100)
      hambre = 100
  }

  def montar(dragon: Dragon): PosibleCompetidor = {
    try {
      new Jinete(this, dragon)
    }
    catch {
      case error: Exception => this
    }
    ???
  }

  def porcentajeDeHambre: String = s"${hambre}%"

  def significadoPorcentajeDeHambre: String = {
    if (porcentajeDeHambre == "0%") "panza llena, corazón contento"
    else throw new MyCustomException("El porcentajeDeHambre no tiene un significado asociado");
  }
}

object Hipo extends Vikingo(70, 8, 3, 0, SistemaDeVuelo)

object Astrid extends Vikingo(70, 10, 2, 0, Hacha)

object Patán extends Vikingo(80, 6, 3, 0, Maza)

object Patapez extends Vikingo(100, 5, 1, 0, new Comestible(5)) {
  override def participarEnPosta(porcentajeDeHambre: Hambre): Unit = {
    val modificadorDeHambre: Int = 2
    super.participarEnPosta(porcentajeDeHambre * modificadorDeHambre)
    objeto match {
      case comestible: Comestible => hambre -= comestible.modificador()
    }
  }
}

//val ListaVikingos = List[Vikingo](Hipo, Astrid, Patán, Patapez)