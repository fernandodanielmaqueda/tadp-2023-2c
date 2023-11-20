package festival

abstract class Posta {

  def participar(anotados: List[Competidor]): List[Competidor] = {
    ordenarPorMejor(
      for (contendiente <- anotados if cumpleCriteriosDeAdmision(contendiente) && contendiente.puedeCompetirEn(this))
      yield contendiente.competirEn(this)
    )
  }

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean

  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor] = competidores.sortWith(elPrimeroEsMejorQueElSegundo)

  def incremento: Hambre

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean

}

class Pesca(pesoMinimoQueDebeLevantar: kg = 0) extends Posta
{
  require(pesoMinimoQueDebeLevantar >= 0, "El pesoMinimoQueDebeLevantar no puede ser negativo")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.maximoDeKgDePescadoQuePuedeCargar > _.maximoDeKgDePescadoQuePuedeCargar

  def incremento: Hambre = 5

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = competidor.maximoDeKgDePescadoQuePuedeCargar >= pesoMinimoQueDebeLevantar

}

class Combate(gradoDeBarbaridadMinimo: Barbarosidad) extends Posta
{
  require(gradoDeBarbaridadMinimo >= 0, "El gradoDeBarbaridadMinimo no puede ser negativo")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.daño > _.daño

  def incremento: Hambre = 10

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = (competidor.barbarosidad >= gradoDeBarbaridadMinimo) || competidor.tieneUnArmaEquipada

}

class Carrera(kilometrosDeCarrera: km, losParticipantesRequierenMontura: Boolean = false) extends Posta
{
  require(kilometrosDeCarrera > 0, "Los kilometrosDeCarrera deben ser positivos")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.velocidad > _.velocidad

  def incremento: Hambre = 1 * kilometrosDeCarrera

  def cumpleCriteriosDeAdmision(competidor: Competidor): Boolean = !losParticipantesRequierenMontura || competidor.tieneMontura

}