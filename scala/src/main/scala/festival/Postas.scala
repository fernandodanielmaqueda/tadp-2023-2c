package festival

abstract class Posta {

  def participar(competidores: List[Vikingo]): List[Vikingo]

}

class Pesca(pesoMinimoQueDebeLevantar: kg = 0) extends Posta
{

  def participar(competidores: List[Vikingo]): List[Vikingo] = {
    competidores.foreach(competidor => competidor.incrementarNivelDeHambre(5))
    competidores.sortBy(_.maximoDeKgDePescadoQuePuedeCargar)
    //competidores.sorted
    //competidores.sortWith()
  }

}

class Combate(gradoDeBarbaridadMinimo: Barbarosidad) extends Posta
{

  def participar(competidores: List[Vikingo]): List[Vikingo] = {
    competidores.foreach(competidor => competidor.incrementarNivelDeHambre(10))
    competidores.sortBy(_.daño)
  }

}

class Carrera(kilometrosDeCarrera: km, losParticipantesRequierenMontura: Boolean = false) extends Posta
{

  def participar(competidores: List[Vikingo]): List[Vikingo] = {
    competidores.foreach(competidor => competidor.incrementarNivelDeHambre(1 * kilometrosDeCarrera))
    competidores.sortBy(_.velocidad)
  }

}