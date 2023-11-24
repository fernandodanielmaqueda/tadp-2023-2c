package festival

trait ReglasDeTorneo[A <: Participante] {
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

  def comoAlistarA(competidoresRestantes: List[A]): List[Vikingo]

  def reagruparA(nuevosVikingos: List[Vikingo], competidoresActuales: List[A]): List[A]

  // anotarA
  def inscribirA(grupoDeCompetidores: List[A]): Either[String, A] = {
    require(grupoDeCompetidores.nonEmpty, "El grupoDeCompetidores no puede ser vacio")

    this.serieDePostas.foldLeft(grupoDeCompetidores)((competidoresActuales, postaActual) =>
      competidoresActuales match {
        case Nil => List()
        case competidorGanador :: Nil => List(competidorGanador)
        case competidoresRestantes => this.reagruparA(new Ronda(postaActual).alistarA(this.comoAlistarA(competidoresRestantes)), competidoresActuales)
      }
    ) match {
      case Nil => Left("No hubo ningun ganador")
      case competidorGanador :: Nil => Right(competidorGanador)
      case competidoresRestantes => Right(this.decisionGanador(competidoresRestantes))
    }
  }

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

  // quienesContinuan / quienesAvanzan
  def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] =
    resultantes.take(resultantes.length / 2)

  // enCasoDeTerminarElTorneoConVariosParticipantes
  def decisionGanador(competidores: List[A]): A

}

trait ReglasDeTorneoEstandar extends ReglasDeTorneo[Vikingo] {

  def comoAlistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = vikingosRestantes

  def reagruparA(nuevosVikingos: List[Vikingo], vikingosActuales: List[Vikingo]): List[Vikingo] = nuevosVikingos

  def decisionGanador(restantes: List[Vikingo]): Vikingo = restantes.head

}