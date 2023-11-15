package domain

class Torneo (val postas: List[Posta], val dragones: List[Dragon], reglamento: ReglasDeTorneo) {

  def conseguirGanador(competidores: List[PosibleCompetidor]): PosibleCompetidor =
    reglamento.condicionDeGanador(ejecutar(competidores))

  def ejecutar(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] = {
    competidores match {
      case Nil => throw new MyCustomException("No hubo ningun ganador")
      case ganador :: Nil => List[PosibleCompetidor](ganador)
      case restantes =>
        if (postas.isEmpty)
          restantes
        else
          (new Torneo(postas.tail, dragones)).ejecutar(postas.head.participar(restantes))
    }
  }
}

abstract class ReglasDeTorneo {
  def condicionDeGanador(competidores: List[PosibleCompetidor]): PosibleCompetidor = competidores.head

  def preparacionParaPosta(competidores: List[PosibleCompetidor], posta: Posta): List[PosibleCompetidor] = ???

  def filtroCompetidores(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] =
    competidores.take(competidores.size / 2)

}

object Estandar extends ReglasDeTorneo

class Eliminacion(n: Int) extends ReglasDeTorneo {
  override def filtroCompetidores(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] =
    competidores.take(competidores.size - n)

}

class TorneoInverso extends ReglasDeTorneo {

  override def condicionDeGanador(competidores: List[PosibleCompetidor]): PosibleCompetidor =
    competidores.reverse.head

  override def filtroCompetidores(competidores: List[PosibleCompetidor]): List[PosibleCompetidor] =
    competidores.takeRight(competidores.size / 2)

}

class VetoDragones(condicion: (Dragon) => Boolean) extends ReglasDeTorneo {

  override def preparacionParaPosta(competidores: List[PosibleCompetidor], posta: Posta): List[PosibleCompetidor] =
    ???

}

class Handicap extends ReglasDeTorneo {

  override def preparacionParaPosta(competidores: List[PosibleCompetidor], posta: Posta): List[PosibleCompetidor] =
    ???

}