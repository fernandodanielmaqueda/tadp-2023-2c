package festival

trait ReglasDeTorneo {
  lasReglasDeTorneo =>

  def serieDePostas: List[Posta]
  def conjuntoDeDragones: Set[Dragon]

  class Ronda(postaActual: Posta) {

    def alistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = {
      lasReglasDeTorneo.quienesPasan(postaActual.participar(
        lasReglasDeTorneo.preparacion(vikingosRestantes, conjuntoDeDragones, postaActual)
      ).map {
        case unVikingo: Vikingo => unVikingo
        case unJinete: Jinete => unJinete.vikingo
      })
    }

  }

  // anotarA
  def inscribirA(grupoDeVikingos: List[Vikingo]): Either[String, Vikingo] = {
    require(grupoDeVikingos.nonEmpty, "El grupoDeVikingos no puede ser vacio")

    this.serieDePostas.foldLeft(grupoDeVikingos)((vikingos, postaActual) =>
      vikingos match {
        case Nil => List()
        case vikingoGanador :: Nil => List(vikingoGanador)
        case vikingosRestantes => new Ronda(postaActual).alistarA(vikingosRestantes)
      }
    ) match {
      case Nil => Left("No hubo ningun ganador")
      case vikingoGanador :: Nil => Right(vikingoGanador)
      case vikingosRestantes => Right(lasReglasDeTorneo.decisionGanador(vikingosRestantes))
    }
  }

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