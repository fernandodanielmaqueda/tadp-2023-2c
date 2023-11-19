package festival

case class Jinete(vikingo: Vikingo, dragon: Dragon) extends Competidor {

  def velocidad: km_h = dragon.velocidadVuelo - 1 * vikingo.peso

  def barbarosidad: Barbarosidad = vikingo.barbarosidad

  def darHambrePorParticiparEnUnaPosta(incremento: Hambre): Jinete = {
    this.copy(vikingo = vikingo.incrementarNivelDeHambre(incremento))
  }

  override def puedeCompetirEn(unaPosta: Posta): Boolean = vikingo.puedeCompetirEn(unaPosta)

  def competirEn(unaPosta: Posta): Jinete = {
    if (!puedeCompetirEn(unaPosta)) throw new MyCustomException("El jinete no puede competir en esa posta")
    //vikingo.item.utilizarSobre(this.darHambrePorParticiparEnUnaPosta(5))
    ???
  }

  def maximoDeKgDePescadoQuePuedeCargar: kg = dragon.hastaCuantoPuedeCargar - vikingo.peso

  def daño: Daño = vikingo.daño + dragon.dañoQueProduce

  def tieneUnArmaEquipada: Boolean = vikingo.tieneUnArmaEquipada

  def tieneMontura: Boolean = true

}