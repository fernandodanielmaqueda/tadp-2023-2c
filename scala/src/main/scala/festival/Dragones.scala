package festival

abstract class Dragon(val peso: kg) {

  def velocidadBase: km_h = velocidadBaseNormal

  def velocidadVuelo: km_h = velocidadBase - peso

  def dañoQueProduce: Daño

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  //def puedeRemontarVuelo: Boolean =

  def puedeMontar(unVikingo: Vikingo): Boolean = unVikingo.peso <= this.hastaCuantoPuedeCargar

}

class FuriaNocturna(_peso: kg, val dañoQueProduce: Daño) extends Dragon(_peso) {

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

class NadderMortifero(_peso: kg) extends Dragon(_peso) {

  def dañoQueProduce: Daño = 150

}

class Gronckle(_peso: kg) extends Dragon(_peso) {

  override def velocidadBase: km_h = super.velocidadBase / 2

  def dañoQueProduce: Daño = 5 * _peso

}