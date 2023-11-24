package festival

class TorneoEstandar(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar

class TorneoEliminacion(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon], N: Int) extends ReglasDeTorneoEstandar {

  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.dropRight(N)

}

class TorneoInverso(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar {

  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.drop(competidores.length / 2)

  override def decisionGanador(competidores: List[Vikingo]): Vikingo = competidores.last

}

class TorneoConVetoDeDragones(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon], condicion: Dragon => Boolean) extends ReglasDeTorneoEstandar {

  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes, conjuntoDeDragones.filter(condicion), postaActual)

}

class TorneoConHandicap(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar {

  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes.reverse, conjuntoDeDragones, postaActual)

}

//class TorneoPorEquipos(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneo {
//
//  def inscribirA(grupoDeEquipos: List[Equipo]): Either[String, Equipo] = {
//    require(grupoDeEquipos.nonEmpty, "El grupoDeEquipos no puede ser vacio")
//
//    this.serieDePostas.foldLeft(grupoDeEquipos)((participantes, postaActual) =>
//      participantes match {
//        case Nil => List()
//        case participanteGanador :: Nil => List(participanteGanador)
//        case participantesRestantes => elTorneo.nuevaRonda(postaActual).alistarA(
//          participantesRestantes match {
//            //case grupoDeVikingos: List[Vikingo] => grupoDeVikingos
//            case grupoDeEquipos: List[Equipo] => grupoDeEquipos.map(_.conjuntoDeVikingos.toList).transpose.flatten
//            //case _ => throw new
//          }
//        )
//      }
//    ) match {
//      case Nil => Left("No hubo ningun ganador")
//      case participanteGanador :: Nil => Right(vikingoGanador)
//      case vikingosRestantes => Right(reglas.decisionGanador(vikingosRestantes))
//    }
//  }
//
//  def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] = {
//    vikingosRestantes.foldLeft((List[Competidor](), conjuntoDeDragones))((tupla, vikingoActual) => tupla match {
//      case (competidoresActuales, dragonesDisponibles) =>
//        vikingoActual.determinarParticipacionEn(postaActual, dragonesDisponibles) match {
//          case None => (competidoresActuales, dragonesDisponibles)
//          case Some(nuevoCompetidor) => nuevoCompetidor match {
//            case unVikingo: Vikingo => (competidoresActuales :+ unVikingo, dragonesDisponibles)
//            case unJinete: Jinete => (competidoresActuales :+ unJinete, dragonesDisponibles - unJinete.dragon)
//          }
//        }
//    }) match {
//      case (competidores, _) => competidores
//    }
//  }
//
//  def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.take(competidores.length / 2)
//
//  def decisionGanador(competidores: List[Vikingo]): Vikingo = competidores.head
//
//}