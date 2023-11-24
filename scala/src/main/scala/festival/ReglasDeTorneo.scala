package festival

trait ReglasDeTorneo {
  def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor]
  // quienesContinuan / quienesAvanzan
  def quienesPasan(competidores: List[Vikingo]): List[Vikingo]
  // enCasoDeTerminarElTorneoConVariosParticipantes
  def decisionGanador(competidores: List[Vikingo]): Vikingo
}

trait ReglasDeTorneoEstandar extends ReglasDeTorneo {
  def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] = {
    vikingosRestantes.foldLeft((List[Competidor](), conjuntoDeDragones))((tupla, vikingoActual) => tupla match {
      case (competidoresActuales, dragonesDisponibles) =>
        vikingoActual.determinarParticipacionEn(postaActual, dragonesDisponibles) match {
          case None => (competidoresActuales, dragonesDisponibles)
          case Some(nuevoCompetidor) => nuevoCompetidor match {
            case unVikingo: Vikingo => (competidoresActuales :+ unVikingo, dragonesDisponibles)
            case unJinete: Jinete => (competidoresActuales :+ unJinete, dragonesDisponibles - unJinete.dragon)
          }
        }
    }) match {
      case (competidores, _) => competidores
    }
  }
  def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.take(competidores.length / 2)
  def decisionGanador(competidores: List[Vikingo]): Vikingo = competidores.head
}

object Estandar extends ReglasDeTorneoEstandar

class Eliminacion(N: Int) extends ReglasDeTorneoEstandar {
  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.dropRight(N)
}

object TorneoInverso extends ReglasDeTorneoEstandar {
  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.drop(competidores.length / 2)
  override def decisionGanador(competidores: List[Vikingo]): Vikingo = competidores.last
}

class ConVetoDeDragones(condicion: Dragon => Boolean) extends ReglasDeTorneoEstandar {
  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes, conjuntoDeDragones.filter(condicion), postaActual)
}

object ConHandicap extends ReglasDeTorneoEstandar {
  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes.reverse, conjuntoDeDragones, postaActual)
}

//object PorEquipos extends ReglasDeTorneo {
//
//}