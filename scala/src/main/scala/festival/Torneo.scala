package festival

class Torneo(postas: List[Posta], dragones: List[Dragon]) {

  def anotarse(grupoDeVikingos: List[Vikingo]): List[Vikingo] = {
    //require(grupoDeVikingos.nonEmpty, "El grupo de vikingos no puede ser vacio")

    postas.foldLeft(grupoDeVikingos)((vikingos, posta) =>
      vikingos match {
        case Nil => Nil
        case vikingoGanador :: Nil => List(vikingoGanador)
        case vikingosRestantes =>
          posta.participar(
            vikingosRestantes.foldLeft((List(): List[Competidor], dragones))((tupla, vikingoActual) => tupla match { case (competidores, dragonesDisponibles) =>
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