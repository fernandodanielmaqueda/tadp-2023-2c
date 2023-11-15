package festival

class Torneo(postas: List[Posta], dragones: List[Dragon]) {

  def anotarse(grupoDeVikingos: List[Vikingo]): List[Vikingo] = {
    //require(grupoDeVikingos.nonEmpty, "El grupo de vikingos no puede ser vacio")

    grupoDeVikingos match {
      case Nil => throw new MyCustomException("No hubo ningun ganador")
      case ganador :: Nil => List(ganador)
      case restantes =>
        if(postas.isEmpty)
          restantes
        else
          (new Torneo(postas.tail, dragones)).anotarse(postas.head.participar(restantes))
    }

  }

}