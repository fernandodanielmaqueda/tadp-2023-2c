package festival

abstract class Dragon(val peso: kg) {

  def velocidadBase: km_h = velocidadBaseNormal

  def velocidadVuelo: km_h = velocidadBase - peso

  def dañoQueProduce: Daño

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  def puedeRemontarVueloCon(unVikingo: Vikingo): Boolean = unVikingo.peso <= hastaCuantoPuedeCargar

  def restriccionesBasicas: List[Requisito] = List(RequisitoBasico)

  def restriccionesExtra: List[Requisito] = List()

  def puedeMontar(unVikingo: Vikingo): Boolean = (restriccionesBasicas ::: restriccionesExtra).forall(_.seCumplePor(unVikingo, this))

}

class FuriaNocturna(_peso: kg, val dañoQueProduce: Daño) extends Dragon(_peso) {

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

class NadderMortifero(_peso: kg) extends Dragon(_peso) {

  def dañoQueProduce: Daño = 150

  override def restriccionesBasicas: List[Requisito] = super.restriccionesBasicas :+ RequisitoNoSuperarDañoPropio

}

class Gronckle(_peso: kg, limiteDePesoParaVikingo: kg) extends Dragon(_peso) {

  override def velocidadBase: km_h = super.velocidadBase / 2

  def dañoQueProduce: Daño = 5 * _peso

  override def restriccionesBasicas: List[Requisito] = super.restriccionesBasicas :+ new RequisitoNoSuperarPesoDeterminado(limiteDePesoParaVikingo)

}

object Chimuelo extends FuriaNocturna(200, 100) {

}