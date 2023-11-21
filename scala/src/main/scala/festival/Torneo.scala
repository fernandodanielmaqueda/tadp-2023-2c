package festival

class Torneo(serieDePostas: List[Posta], conjuntoDeDragones: Set[Dragon]) {

  def anotarse(grupoDeVikingos: List[Vikingo]): List[Vikingo] = {
    //require(grupoDeVikingos.nonEmpty, "El grupo de vikingos no puede ser vacio")

    serieDePostas.foldLeft(grupoDeVikingos)((vikingos, posta) =>
      vikingos match {
        case Nil => Nil
        case vikingoGanador :: Nil => List(vikingoGanador)
        case vikingosRestantes =>
          posta.participar(
            vikingosRestantes.foldLeft((List(): List[Competidor], conjuntoDeDragones))((tupla, vikingoActual) => tupla match { case (competidores, dragonesDisponibles) =>
              val (competidorActual, nuevosDragonesDisponibles) = vikingoActual.elegirComoParticipar(dragonesDisponibles)
              (competidores :+ competidorActual, nuevosDragonesDisponibles)
              })._1
            )
            .map {
            case unVikingo: Vikingo => unVikingo
            case unJinete: Jinete => unJinete.vikingo
          }
      }
    )

  }

}