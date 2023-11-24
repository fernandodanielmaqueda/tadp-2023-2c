package festival

trait Dragon {

  def peso: kg

  def velocidadBase: km_h = velocidadBaseNormal
  def velocidadVuelo: km_h = velocidadBase - peso

  def dañoQueProduce: Daño

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  def puedeRemontarVueloCon(unVikingo: Vikingo): Boolean = unVikingo.peso <= hastaCuantoPuedeCargar

  protected def restriccionesDeMonturaBasicas: Set[RequisitoDeMontura] = Set(RequisitoDeMonturaBasico)
  protected def restriccionesDeMonturaExtras: Set[RequisitoDeMontura]
  def restriccionesDeMontura: Set[RequisitoDeMontura] =
    restriccionesDeMonturaBasicas ++ restriccionesDeMonturaExtras

  def loPuedeMontar(unVikingo: Vikingo): Boolean =
    restriccionesDeMontura.forall(_(unVikingo, this))

}

case class FuriaNocturna(peso: kg, protected val restriccionesDeMonturaExtras: Set[RequisitoDeMontura], dañoQueProduce: Daño) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")
  require(dañoQueProduce >= 0, "El dañoQueProduce no puede ser negativo")

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

case class NadderMortifero(peso: kg, protected val restriccionesDeMonturaExtras: Set[RequisitoDeMontura]) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")

  def dañoQueProduce: Daño = 150

  override def restriccionesDeMonturaBasicas: Set[RequisitoDeMontura] =
    super.restriccionesDeMontura + RequisitoDeMonturaNoSuperarDañoPropio

}

case class Gronckle(peso: kg, protected val restriccionesDeMonturaExtras: Set[RequisitoDeMontura], limiteDePesoParaVikingo: kg) extends Dragon {
  require(peso > 0, "El peso debe ser positivo")
  require(limiteDePesoParaVikingo >= 0, "El limiteDePesoParaVikingo no puede ser negativo")

  override def velocidadBase: km_h = super.velocidadBase / 2

  def dañoQueProduce: Daño = 5 * peso

  override def restriccionesDeMonturaBasicas: Set[RequisitoDeMontura] =
    super.restriccionesDeMontura + new RequisitoDeMonturaNoSuperarPesoDeterminado(limiteDePesoParaVikingo)

}