package festival

class Torneo[A <: Participante](serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon], reglasDelTorneo: ReglasDeTorneo[A]) {
  elTorneo =>

  class Ronda(postaActual: Posta) {

    def alistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = {
      reglasDelTorneo.quienesPasan(postaActual.participar(
        reglasDelTorneo.preparacion(vikingosRestantes, conjuntoDeDragones, postaActual)
      ).map {
        case unVikingo: Vikingo => unVikingo
        case unJinete: Jinete => unJinete.vikingo
      })
    }

  }

  def inscribirA(grupoDeCompetidores: List[A]): Either[String, A] = { // anotarA
    require(grupoDeCompetidores.nonEmpty, "El grupoDeCompetidores no puede ser vacio")

    this.serieDePostas.foldLeft(grupoDeCompetidores)((competidoresActuales, postaActual) =>
      competidoresActuales match {
        case Nil => List()
        case competidorGanador :: Nil => List(competidorGanador)
        case competidoresRestantes =>
          reglasDelTorneo.reagruparA(
            new Ronda(postaActual).alistarA(reglasDelTorneo.comoAlistarA(competidoresRestantes)), competidoresActuales
          )
      }
    ) match {
      case Nil => Left("No hubo ningun ganador")
      case competidorGanador :: Nil => Right(competidorGanador)
      case competidoresRestantes => Right(reglasDelTorneo.decisionGanador(competidoresRestantes))
    }
  }

}