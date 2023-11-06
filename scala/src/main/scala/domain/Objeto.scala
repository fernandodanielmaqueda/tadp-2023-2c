package domain

abstract class Objeto {
  def modificador(): Any
}

object SistemaDeVuelo extends Objeto {
  def modificador(): None.type = {
    None
  }
}

class Comestible extends Object {
  var porcentajeDeSatisfaccion: Double = 0

  def modificador(): Double = {
    porcentajeDeSatisfaccion
  }
}

class Arma extends Objeto {
  var aumentoDeDaño: Double = 0;

  def modificador(): Double = {
    aumentoDeDaño;
  }
}

val Hacha: Arma = new Arma { aumentoDeDaño = 30 }

val Maza: Arma = new Arma { aumentoDeDaño = 100 }

