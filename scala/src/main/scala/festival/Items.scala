package festival

trait Item {

  def daño: Daño = 0

  def aplicarSobrePortador(usuario: Competidor): Competidor = usuario

}

object SistemaDeVuelo extends Item

case class Arma(override val daño: Daño) extends Item {
  require(daño >= 0, "El daño no puede ser negativo")

}

object Hacha extends Arma(10)
object Maza extends Arma(20)

case class Comestible(decrementoDeHambre: Hambre) extends Item {
  require(decrementoDeHambre >= 0, "El decrementoDeHambre no puede ser negativo")

  override def aplicarSobrePortador(usuario: Competidor): Competidor = usuario.decrementarNivelDeHambre(decrementoDeHambre)

}