package festival

trait ReglasDeTorneo[A <: Participante] {

  def comoAlistarA(competidoresRestantes: List[A]): List[Vikingo]

  def reagruparA(vikingosRestantes: List[Vikingo], competidoresActuales: List[A]): List[A]

  def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] = {
    vikingosRestantes.foldLeft((List[Competidor](), conjuntoDeDragones))((tupla, vikingoActual) => tupla match {
      case (competidoresActuales, dragonesDisponibles) =>
        vikingoActual.determinarParticipacionEn(postaActual, dragonesDisponibles).map {
          case unVikingo: Vikingo => (competidoresActuales :+ unVikingo, dragonesDisponibles)
          case unJinete: Jinete => (competidoresActuales :+ unJinete, dragonesDisponibles - unJinete.dragon)
        }.getOrElse((competidoresActuales, dragonesDisponibles))
    }) match {
      case (competidores, _) => competidores
    }
  }

  def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] = // quienesContinuan / quienesAvanzan
    resultantes.take(resultantes.length / 2)

  def decisionGanador(competidores: List[A]): A // enCasoDeTerminarElTorneoConVariosParticipantes

}

trait ReglasDeTorneoEstandar extends ReglasDeTorneo[Vikingo] {

  def comoAlistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = vikingosRestantes

  def reagruparA(vikingosRestantes: List[Vikingo], vikingosActuales: List[Vikingo]): List[Vikingo] = vikingosRestantes

  def decisionGanador(restantes: List[Vikingo]): Vikingo = restantes.head

}

object TorneoEstandar extends ReglasDeTorneoEstandar

class TorneoEliminacion(N: Int) extends ReglasDeTorneoEstandar {
  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.dropRight(N)
}

object TorneoInverso extends ReglasDeTorneoEstandar {
  override def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] = resultantes.drop(resultantes.length / 2)

  override def decisionGanador(restantes: List[Vikingo]): Vikingo = restantes.last
}

class TorneoConVetoDeDragones(condicion: Dragon => Boolean) extends ReglasDeTorneoEstandar {
  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes, conjuntoDeDragones.filter(condicion), postaActual)
}

object TorneoConHandicap extends ReglasDeTorneoEstandar {
  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes.reverse, conjuntoDeDragones, postaActual)
}

object TorneoPorEquipos extends ReglasDeTorneo[Equipo] {

  def comoAlistarA(equiposRestantes: List[Equipo]): List[Vikingo] =
    equiposRestantes.map(_.conjuntoDeVikingos.toList).transpose.flatten

  def reagruparA(vikingosRestantes: List[Vikingo], equiposActuales: List[Equipo]): List[Equipo] = for {
    equipo <- equiposActuales.map(_.conjuntoDeVikingos & vikingosRestantes.toSet) if equipo.nonEmpty
  } yield Equipo(equipo)

  def decisionGanador(restantes: List[Equipo]): Equipo = {
    restantes.maxBy(_.conjuntoDeVikingos.size)
  }

}