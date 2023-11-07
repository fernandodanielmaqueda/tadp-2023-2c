package domain

import domain.festival.{Hambre, km}

import scala.util.Try

abstract class Posta(val modificadorDeHambre: Hambre) {

  def ordenarPorEjecucion(competidores: List[PosibleCompetidor]): List[PosibleCompetidor]

  def participarConJinetes(competidores: List[PosibleCompetidor]): List[Vikingo] = ???
  def participar(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {
    cansarCompetidores(competidores)
    //val competidoresOrdenados: List[PosibleCompetidor] =
    ordenarPorEjecucion(competidores)
    //desmontarJinetes(competidoresOrdenados)
  }

  private def cansarCompetidores(competidores: List[PosibleCompetidor]): Unit = {
    competidores.foreach(competidor => competidor.participarEnPosta(modificadorDeHambre))
  }

  //private def desmontar(jinete: Jinete): Try[Vikingo] = Try(jinete.desmontar())

  private def desmontarJinetes(competidores: List[PosibleCompetidor]): List[Vikingo] = {
    competidores.map {
      case jinete: Jinete => jinete.desmontar()
    }
  }

}

// reverse para que el mejor quede primero.

object Pesca extends Posta (5) {
  override def ordenarPorEjecucion(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {
    competidores.sortBy(competidor => competidor.pescaMaxima()).reverse
  }
}

object Combate extends Posta (10) {
  override def ordenarPorEjecucion(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {
    competidores.sortBy(competidor => competidor.daño()).reverse
  }
}

class Carrera (kilometrosDeCarrera: km) extends Posta (kilometrosDeCarrera) {
  override def ordenarPorEjecucion(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {
    competidores.sortBy(competidor => competidor.velocidad()).reverse
  }
}