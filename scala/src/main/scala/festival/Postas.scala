package festival

trait Posta {

  def participar(contendientes: List[Competidor]): List[Competidor] =
    this.ordenarPorMejor(contendientes).map(_.participarEn(this))

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean
  def ordenarPorMejor(competidores: List[Competidor]): List[Competidor] = competidores.sortWith(elPrimeroEsMejorQueElSegundo)

  def incremento: Hambre

  def admiteA(competidor: Competidor): Boolean

}

class Pesca(pesoMinimoQueDebeLevantar: kg = 0) extends Posta {
  require(pesoMinimoQueDebeLevantar >= 0, "El pesoMinimoQueDebeLevantar no puede ser negativo")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.maximoDeKgDePescadoQuePuedeCargar > _.maximoDeKgDePescadoQuePuedeCargar

  def incremento: Hambre = 5

  def admiteA(competidor: Competidor): Boolean = competidor.maximoDeKgDePescadoQuePuedeCargar >= pesoMinimoQueDebeLevantar

}

class Combate(gradoDeBarbaridadMinimo: Barbarosidad) extends Posta {
  require(gradoDeBarbaridadMinimo >= 0, "El gradoDeBarbaridadMinimo no puede ser negativo")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.daño > _.daño

  def incremento: Hambre = 10

  def admiteA(competidor: Competidor): Boolean = (competidor.barbarosidad >= gradoDeBarbaridadMinimo) || competidor.tieneUnArmaEquipada

}

class Carrera(kilometrosDeCarrera: km, losParticipantesRequierenMontura: Boolean = false) extends Posta {
  require(kilometrosDeCarrera > 0, "Los kilometrosDeCarrera deben ser positivos")

  def elPrimeroEsMejorQueElSegundo: (Competidor, Competidor) => Boolean = _.velocidad > _.velocidad

  def incremento: Hambre = 1 * kilometrosDeCarrera

  def admiteA(competidor: Competidor): Boolean = !losParticipantesRequierenMontura || competidor.tieneMontura

}