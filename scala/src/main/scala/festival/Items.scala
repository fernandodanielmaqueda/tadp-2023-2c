package festival

abstract class Item {

  def daño: Daño = 0

  def utilizarSobre(unVikingo: Vikingo): Vikingo = unVikingo

}

object SistemaDeVuelo extends Item

case class Arma(_daño: Daño) extends Item {

  override def daño: Daño = _daño

}

object Hacha extends Arma(10)

object Maza extends Arma(20)

case class Comestible(decrementoDeHambre: Hambre) extends Item {

  override def utilizarSobre(unVikingo: Vikingo): Vikingo = unVikingo.decrementarNivelDeHambre(decrementoDeHambre)

}