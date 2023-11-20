package festival

abstract class Dragon(val peso: kg) {
  require(peso > 0, "El peso debe ser positivo")

  def velocidadBase: km_h = velocidadBaseNormal
  def velocidadVuelo: km_h = velocidadBase - peso

  def dañoQueProduce: Daño

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  def puedeRemontarVueloCon(unVikingo: Vikingo): Boolean = unVikingo.peso <= hastaCuantoPuedeCargar

  var restriccionesDeMonturaBasicas = List[RequisitoDeMontura](RequisitoDeMonturaBasico)
  var restriccionesDeMonturaExtras = List[RequisitoDeMontura]()

  def puedeMontar(unVikingo: Vikingo): Boolean = (restriccionesDeMonturaBasicas ::: restriccionesDeMonturaExtras).forall(_.seCumplePor(unVikingo, this))

}

class FuriaNocturna(_peso: kg, val dañoQueProduce: Daño) extends Dragon(_peso) {
  require(_peso > 0, "El peso debe ser positivo")
  require(dañoQueProduce >= 0, "El dañoQueProduce no puede ser negativo")

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

class NadderMortifero(_peso: kg) extends Dragon(_peso) {
  require(_peso > 0, "El peso debe ser positivo")

  def dañoQueProduce: Daño = 150

  restriccionesDeMonturaBasicas :+= RequisitoDeMonturaNoSuperarDañoPropio

}

class Gronckle(_peso: kg, limiteDePesoParaVikingo: kg) extends Dragon(_peso) {
  require(_peso > 0, "El peso debe ser positivo")
  require(limiteDePesoParaVikingo >= 0, "El limiteDePesoParaVikingo no puede ser negativo")

  override def velocidadBase: km_h = super.velocidadBase / 2

  def dañoQueProduce: Daño = 5 * _peso

  restriccionesDeMonturaBasicas :+= new RequisitoDeMonturaNoSuperarPesoDeterminado(limiteDePesoParaVikingo)

}

object Chimuelo extends FuriaNocturna(200, 100) {

  restriccionesDeMonturaExtras :+= new RequisitoDeMonturaTenerItem(SistemaDeVuelo)

}