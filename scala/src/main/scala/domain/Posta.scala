package domain

import domain.festival.{Hambre, kg, km}

import scala.Double
import scala.util.{Failure, Success, Try}

abstract class Posta(val modificadorDeHambre: Hambre) {

  def capacidadMedida(competidor: PosibleCompetidor): Double

  def puedeCompetir(competidor: PosibleCompetidor): Boolean =
    condicionHambre(competidor) && condicionPosta(competidor)

  // reverse para que el mejor quede primero.

  def ordenarPorEjecucion(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] =
    competidores.sortBy(competidor => capacidadMedida(competidor)).reverse

  def participarConJinetes(competidores: List[PosibleCompetidor]): List[Vikingo] = ???

  def participar(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {

    val competidoresOrdenados = ordenarPorEjecucion(competidores.filter(puedeCompetir))
    //desmontarJinetes(competidoresOrdenados)
    cansarCompetidores(competidoresOrdenados)
    competidoresOrdenados
  }

  def mejorMontura(vikingo: Vikingo, dragonesMontables: List[Dragon]): PosibleCompetidor = {
    val valorBase: Double = capacidadMedida(vikingo)
    val mejorMontura: Jinete = dragonesMontables.
      filter(_.montablePor(vikingo)).
      map(vikingo.montar).
      maxBy(capacidadMedida)
    if (valorBase < capacidadMedida(mejorMontura))
      mejorMontura
    else
      vikingo
  }

  protected def condicionPosta(competidor: PosibleCompetidor): Boolean

  private def condicionHambre(competidor: PosibleCompetidor): Boolean = competidor.hambre + modificadorDeHambre < 100

  private def cansarCompetidores(competidores: List[PosibleCompetidor]): Unit = {
    competidores.foreach(competidor => competidor.participarEnPosta(modificadorDeHambre))
  }

  private def desmontarJinetes(competidores: List[PosibleCompetidor]): List[Vikingo] = {
    competidores.map {
      case jinete: Jinete => jinete.desmontar()
      case vikingo: Vikingo => vikingo
    }
  }

}

class Pesca(posiblePesoMinimo: Option[kg] = None) extends Posta (5) {

  override protected def condicionPosta(competidor: PosibleCompetidor): Boolean = {
    posiblePesoMinimo match {
      case Some(pesoMinimo) => capacidadMedida (competidor) >= pesoMinimo
      case None => true
    }
  }

  override def capacidadMedida(competidor: PosibleCompetidor): Double = competidor.pescaMaxima()

}

class Combate(posibleArmaOBarbarosidad: Either[Arma, Double]) extends Posta (10) {

  override protected def condicionPosta(competidor: PosibleCompetidor): Boolean =
    posibleArmaOBarbarosidad match {
      case Left(arma) => competidor.tieneObjeto(arma)
      case Right(barbarosidadMinima) => competidor.barbarosidad >= barbarosidadMinima
    }

  override def capacidadMedida(competidor: PosibleCompetidor): Double = competidor.daño()
}

class Carrera (kilometrosDeCarrera: km, monturaRequerida: Boolean = false) extends Posta (kilometrosDeCarrera) {

  override protected def condicionPosta(competidor: PosibleCompetidor): Boolean =
    if (monturaRequerida)
      competidor.isInstanceOf[Jinete]
    else
      true

  override def capacidadMedida(competidor: PosibleCompetidor): Double = competidor.velocidad()

}