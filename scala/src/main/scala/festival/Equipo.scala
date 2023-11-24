package festival

case class Equipo(conjuntoDeVikingos: Set[Vikingo]) extends Participante {
  require(conjuntoDeVikingos.nonEmpty, "El conjuntoDeVikingos no puede ser vacio")

}