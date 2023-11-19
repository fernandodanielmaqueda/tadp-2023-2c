package festival

trait Competidor {

  def velocidad: km_h

  def barbarosidad: Barbarosidad

  def puedeCompetirEn(unaPosta: Posta): Boolean = true

  def competirEn(unaPosta: Posta): Competidor

  def maximoDeKgDePescadoQuePuedeCargar: kg

  def daño: Daño

  def tieneUnArmaEquipada: Boolean

  def tieneMontura: Boolean

}
