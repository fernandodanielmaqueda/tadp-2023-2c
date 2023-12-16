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

  def inscribirA(grupoDeCompetidores: List[A]): Either[String, List[A]] = { // anotarA
    require(grupoDeCompetidores.nonEmpty, "El grupoDeCompetidores no puede ser vacio")

    this.postasQueSeVanAJugar(serieDePostas).foldLeft(grupoDeCompetidores)((competidoresActuales, postaActual) =>
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
      case competidorGanador :: Nil => Right(List(competidorGanador))
      case competidoresRestantes => Right(reglasDelTorneo.decisionGanador(competidoresRestantes))
    }
  }

  private def hayNuevaLluvia: Boolean = scala.math.random < 0.2

  private def postasQueSeVanAJugar(serieDePostas: List[Posta]): List[Posta] = serieDePostas match {
    case Nil => Nil
    case postasRestantes if this.hayNuevaLluvia => this.postasQueSeVanAJugar(postasRestantes.drop(3))
    case cabeza :: cola => cabeza :: this.postasQueSeVanAJugar(cola)
  }

}