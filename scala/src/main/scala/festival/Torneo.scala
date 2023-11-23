package festival

class Torneo(serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon], reglas: ReglasDeTorneo = ReglasDeTorneoEstandar) {
//class Torneo(serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneo {
  elTorneo =>

  // Otra opción: usar inner
  //  trait Reglas{
  //
  //  }
  // Además se podría: Usar implicit

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
    ???

//    serieDePostas.foldLeft(grupoDeVikingos)((vikingos, postaActual) =>
//      vikingos match {
//        case Nil => return Left("No hubo ningún ganador")
//        case vikingoGanador :: Nil => return Right(vikingoGanador)
//        case vikingosRestantes => new Ronda(postaActual).alistarA(vikingosRestantes)
//      }
//    ) match {
//      case Nil => Left("No hubo ningún ganador")
//      case vikingoGanador :: Nil => Right(vikingoGanador)
//      case vikingosRestantes => reglas.queHacerEnCasoDeTerminarElTorneoConVariosParticipantes(vikingosRestantes)
//    }
  }

}