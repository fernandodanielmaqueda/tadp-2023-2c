package festival

case class Jinete(vikingo: Vikingo, dragon: Dragon) extends Competidor {

  //def peso: kg = vikingo.peso + dragon.peso
  def velocidad: km_h = dragon.velocidadVuelo - 1 * vikingo.peso
  def barbarosidad: Barbarosidad = vikingo.barbarosidad
  def item: Item = vikingo.item

  def nivelDeHambre: Hambre = vikingo.nivelDeHambre

  def nivelDeHambreAlQueIncrementariaCon(incremento: Hambre): Hambre = vikingo.nivelDeHambreAlQueIncrementariaCon(incremento)
  def nivelDeHambreAlQueDecrementariaCon(decremento: Hambre): Hambre = vikingo.nivelDeHambreAlQueDecrementariaCon(decremento)

  def incrementarNivelDeHambre(incremento: Hambre): Jinete = {
    this.copy(vikingo = vikingo.incrementarNivelDeHambre(incremento))
  }
  def decrementarNivelDeHambre(decremento: Hambre): Jinete = {
    this.copy(vikingo = vikingo.decrementarNivelDeHambre(decremento))
  }

  override def cuantaHambreDeberiaIncrementarPorParticiparEn(unaPosta: Posta): Hambre = 5

  override def puedeCompetirEn(unaPosta: Posta): Boolean = vikingo.puedeCompetirEn(unaPosta)

  def maximoDeKgDePescadoQuePuedeCargar: kg = dragon.hastaCuantoPuedeCargar - vikingo.peso
  def daño: Daño = vikingo.daño + dragon.dañoQueProduce

  def tieneUnArmaEquipada: Boolean = vikingo.tieneUnArmaEquipada
  def tieneMontura: Boolean = true

}