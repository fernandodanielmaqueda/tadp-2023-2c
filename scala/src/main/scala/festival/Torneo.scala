package festival

//class Torneo(serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneo {
class Torneo(serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon], reglas: ReglasDeTorneo = Estandar) {
  elTorneo =>

  // Otra posible idea: usar inner así:
  //   trait Reglas
  // Además tal vez se podría sumar implicit

  class Ronda(postaActual: Posta) {

    def alistarA(vikingosRestantes: List[Vikingo]): List[Vikingo] = {
      reglas.quienesPasan(postaActual.participar(
        reglas.preparacion(vikingosRestantes, conjuntoDeDragones, postaActual)
      ).map {
        case unVikingo: Vikingo => unVikingo
        case unJinete: Jinete => unJinete.vikingo
      })

    }

  }

  def anotarA(grupoDeVikingos: List[Vikingo]): Either[String, Vikingo] = {
    require(grupoDeVikingos.nonEmpty, "El grupoDeVikingos no puede ser vacio")

    serieDePostas.foldLeft(grupoDeVikingos)((vikingos, postaActual) =>
      vikingos match {
        case Nil => List()
        case vikingoGanador :: Nil => List(vikingoGanador)
        case vikingosRestantes => new Ronda(postaActual).alistarA(vikingosRestantes)
      }
    ) match {
      case Nil => Left("No hubo ningun ganador")
      case vikingoGanador :: Nil => Right(vikingoGanador)
      case vikingosRestantes => Right(reglas.decisionGanador(vikingosRestantes))
    }
  }

}