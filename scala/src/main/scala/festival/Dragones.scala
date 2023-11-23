package festival

trait Dragon {

  def peso: kg

  def velocidadBase: km_h = velocidadBaseNormal
  def velocidadVuelo: km_h = velocidadBase - peso

  def dañoQueProduce: Daño

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  def puedeRemontarVueloCon(unVikingo: Vikingo): Boolean = unVikingo.peso <= hastaCuantoPuedeCargar

  def restriccionesDeMontura: List[RequisitoDeMontura] = restriccionesDeMonturaBasicas ::: restriccionesDeMonturaExtras
  protected def restriccionesDeMonturaBasicas: List[RequisitoDeMontura] = List(RequisitoDeMonturaBasico)
  protected def restriccionesDeMonturaExtras: List[RequisitoDeMontura]

  def loPuedeMontar(unVikingo: Vikingo): Boolean =
    restriccionesDeMontura.forall(_.seCumplePor(unVikingo, this))

}

case class FuriaNocturna(peso: kg, protected val restriccionesDeMonturaExtras: List[RequisitoDeMontura], dañoQueProduce: Daño) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")
  require(dañoQueProduce >= 0, "El dañoQueProduce no puede ser negativo")

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

case class NadderMortifero(peso: kg, protected val restriccionesDeMonturaExtras: List[RequisitoDeMontura]) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")

  def dañoQueProduce: Daño = 150

  override def restriccionesDeMonturaBasicas: List[RequisitoDeMontura] = super.restriccionesDeMontura :+ RequisitoDeMonturaNoSuperarDañoPropio

}

case class Gronckle(peso: kg, protected val restriccionesDeMonturaExtras: List[RequisitoDeMontura], limiteDePesoParaVikingo: kg) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")
  require(limiteDePesoParaVikingo >= 0, "El limiteDePesoParaVikingo no puede ser negativo")

  override def velocidadBase: km_h = super.velocidadBase / 2

  def dañoQueProduce: Daño = 5 * peso

  override def restriccionesDeMonturaBasicas: List[RequisitoDeMontura] = super.restriccionesDeMontura :+ new RequisitoDeMonturaNoSuperarPesoDeterminado(limiteDePesoParaVikingo)

}