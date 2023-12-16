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

  // quienesContinuan / quienesAvanzan
  def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] =
    resultantes.take(resultantes.length / 2)

  // enCasoDeTerminarElTorneoConVariosParticipantes
  def decisionGanador(competidores: List[A]): List[A]

}

trait ReglasDeTorneoConEmpate[A <: Participante] extends ReglasDeTorneo[A] {
  override def decisionGanador(competidores: List[A]): List[A] = competidores
}

trait ReglasDeTorneoEstandar extends ReglasDeTorneo[Vikingo] {

  def comoAlistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = vikingosRestantes

  def reagruparA(vikingosRestantes: List[Vikingo], vikingosActuales: List[Vikingo]): List[Vikingo] = vikingosRestantes

  def decisionGanador(restantes: List[Vikingo]): List[Vikingo] = List(restantes.head)

}

object TorneoEstandarNoEmpatable extends ReglasDeTorneoEstandar

object TorneoEstandarEmpatable extends ReglasDeTorneoEstandar with ReglasDeTorneoConEmpate[Vikingo]

trait TorneoEliminacion extends ReglasDeTorneoEstandar {

  def N: Int

  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.dropRight(N)

}

class TorneoEliminacionNoEmpatable(val N: Int) extends TorneoEliminacion

class TorneoEliminacionEmpatable(val N: Int) extends TorneoEliminacion with ReglasDeTorneoConEmpate[Vikingo]

trait TorneoInverso extends ReglasDeTorneoEstandar {

  override def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] = resultantes.drop(resultantes.length / 2)

  override def decisionGanador(restantes: List[Vikingo]): List[Vikingo] = List(restantes.last)

}

object TorneoInversoNoEmpatable extends TorneoInverso

object TorneoInversoEmpatable extends TorneoInverso with ReglasDeTorneoConEmpate[Vikingo]




trait TorneoConVetoDeDragones extends ReglasDeTorneoEstandar {

  def condicion: Dragon => Boolean

  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes, conjuntoDeDragones.filter(condicion), postaActual)

}

class TorneoConVetoDeDragonesNoEmpatable(val condicion: Dragon => Boolean) extends TorneoConVetoDeDragones

class TorneoConVetoDeDragonesEmpatable(val condicion: Dragon => Boolean) extends TorneoConVetoDeDragones with ReglasDeTorneoConEmpate[Vikingo]

trait TorneoConHandicap extends ReglasDeTorneoEstandar {
  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes.reverse, conjuntoDeDragones, postaActual)
}

object TorneoConHandicapNoEmpatable extends TorneoConHandicap

object TorneoConHandicapEmpatable extends TorneoConHandicap with ReglasDeTorneoConEmpate[Vikingo]

trait TorneoPorEquipos extends ReglasDeTorneo[Equipo] {

  def comoAlistarA(equiposRestantes: List[Equipo]): List[Vikingo] =
    equiposRestantes.map(_.conjuntoDeVikingos.toList).transpose.flatten

  def reagruparA(vikingosRestantes: List[Vikingo], equiposActuales: List[Equipo]): List[Equipo] = for {
    equipo <- equiposActuales.map(_.conjuntoDeVikingos & vikingosRestantes.toSet) if equipo.nonEmpty
  } yield Equipo(equipo)

  def decisionGanador(restantes: List[Equipo]): List[Equipo] =
    List(restantes.maxBy(_.conjuntoDeVikingos.size))

}

object TorneoPorEquiposEmpatable extends TorneoPorEquipos

object TorneoPorEquiposNoEmpatable extends TorneoPorEquipos with ReglasDeTorneoConEmpate[Equipo]