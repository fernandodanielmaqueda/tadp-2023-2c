package festival

abstract class Posta {

  def participar(competidores: List[Competidor]): List[Competidor] = {
    ordenarPorMejor(
      competidores.filter(_.puedeCompetirEn(this))
      .filter(competidor => cumpleCriteriosDeAdmision(competidor))
    )
    .map(competidor => competidor.competirEn(this)
    )
  }

  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor]

  def incremento: Hambre

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean

}

class Pesca(pesoMinimoQueDebeLevantar: kg = 0) extends Posta
{

  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor] = competidores.sortBy(_.maximoDeKgDePescadoQuePuedeCargar)

  def incremento: Hambre = 5

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = competidor.maximoDeKgDePescadoQuePuedeCargar >= pesoMinimoQueDebeLevantar

}

class Combate(gradoDeBarbaridadMinimo: Barbarosidad) extends Posta
{

  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor] = competidores.sortBy(_.daño)

  def incremento: Hambre = 10

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = (competidor.barbarosidad >= gradoDeBarbaridadMinimo) || (competidor.tieneUnArmaEquipada)

}

class Carrera(kilometrosDeCarrera: km, losParticipantesRequierenMontura: Boolean = false) extends Posta
{

  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor] = competidores.sortBy(_.velocidad)

  def incremento: Hambre = 1 * kilometrosDeCarrera

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = !losParticipantesRequierenMontura || competidor.tieneMontura

}