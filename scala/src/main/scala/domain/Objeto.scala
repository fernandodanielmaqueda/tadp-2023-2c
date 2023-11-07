package domain

import domain.festival.{Daño, Hambre}
import domain.positive.PositiveDouble

abstract class Objeto {
  def modificador(): Any
}

object SistemaDeVuelo extends Objeto { override def modificador(): None.type = None }

class Comestible(modificadorDeHambre: Hambre) extends Objeto {
  def modificador(): Hambre = modificadorDeHambre
}

class Arma(modificadorDeDaño: Daño) extends Objeto {
  def modificador(): Daño = modificadorDeDaño
}

object Hacha extends Arma(30)

object Maza extends Arma(100)

