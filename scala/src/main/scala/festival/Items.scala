package festival

abstract class Item {

  def daño: Daño = 0

  def efecto(): Unit = {}

}

class SistemaDeVuelo extends Item {

}

class Arma(_daño: Daño) extends Item {

  override def daño: Daño = _daño

}

class Hacha(_daño: Daño) extends Arma(_daño) {

}

class Maza(_daño: Daño) extends Arma(_daño) {

}

class Comestible(decrementoDeHambre: Hambre) extends Item {



}