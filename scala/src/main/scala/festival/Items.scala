package festival

abstract class Item {

  def daño: Daño = 0

  //def aplicacionPropia(usuario: Competidor): Competidor = usuario
  def aplicacionPropia[A <: Competidor](usuario: A): A = usuario

}

object SistemaDeVuelo extends Item

case class Arma(_daño: Daño) extends Item {
  require(daño >= 0, "El daño no puede ser negativo")

  override def daño: Daño = _daño

}

object Hacha extends Arma(10)

object Maza extends Arma(20)

case class Comestible(decrementoDeHambre: Hambre) extends Item {
  require(decrementoDeHambre >= 0, "El decrementoDeHambre no puede ser negativo")

  //override def aplicacionPropia(usuario: Competidor): Competidor = usuario.decrementarNivelDeHambre(decrementoDeHambre)
  override def aplicacionPropia[A <: Competidor](usuario: A): A = usuario.decrementarNivelDeHambre(decrementoDeHambre)
  //override def aplicacionPropia[A](usuario: A)(implicit ev: Competidor): A = ev.decrementarNivelDeHambre(decrementoDeHambre)

}