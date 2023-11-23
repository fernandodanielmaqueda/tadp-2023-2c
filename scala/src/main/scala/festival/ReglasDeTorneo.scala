package festival

trait ReglasDeTorneo {

  def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] = {
    vikingosRestantes.foldLeft((List(): List[Competidor], conjuntoDeDragones))((tupla, vikingoActual) => tupla match {
      case (competidores, dragonesDisponibles) =>
        vikingoActual.determinarParticipacionEn(postaActual, dragonesDisponibles) match {
          case None => (competidores, dragonesDisponibles)
          case Some(competidorActual) => competidorActual match {
            case unVikingo: Vikingo => (competidores, dragonesDisponibles)
            case unJinete: Jinete => (competidores :+ unJinete, dragonesDisponibles.excl(unJinete.dragon))
          }
        }
    })._1
  }

  //def quienesPasan(competidores: List[Competidor]): List[Competidor]
  def quienesPasan(competidores: List[Vikingo]): List[Vikingo]

  //def queHacerEnCasoDeTerminarElTorneoConVariosParticipantes

}

object ReglasDeTorneoEstandar extends ReglasDeTorneo {

  def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = ???

}

//object asd extends ReglasDeTorneoEstandar {
//
//}

//object ReglasDeTorneoPorEquipos extends ReglasDeTorneo {
//
//}