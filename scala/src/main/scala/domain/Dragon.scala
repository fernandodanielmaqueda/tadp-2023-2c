package domain

abstract class Dragon {

  val velocidadBase: Double = 60

  val porcentajeDePesoSoportable: Double = 0.2

  var peso: Double = 0

  var condicionParaMontar: CondicionParaMontar = CondicionBase

  def velocidad(): Double = velocidadBase - peso

  def daño(): Double

  def montablePor(vikingo: Vikingo): Boolean =
    vikingo.peso <= this.peso * porcentajeDePesoSoportable &&
      condicionParaMontar.call(vikingo)

}

class FuriaNocturna extends Dragon {

  var dañoBase: Double = 0

  val porcentajeDeVelocidad = 3

  override def velocidad(): Double = super.velocidad() * porcentajeDeVelocidad

  override def daño(): Double = dañoBase

}

class NadderMortifero extends Dragon {

  val dañoBase: Double = 150

  override def daño(): Double = dañoBase

  override def montablePor(vikingo: Vikingo): Boolean = {
    super.montablePor(vikingo) &&
      vikingo.daño() < daño()
  }

}

class Gronckle extends Dragon {

  val porcentajeDeVelocidad: Double = 0.5

  val porcentajeDeDaño: Double = 5

  var pesoSoportableMaximo: Double = 0;

  override def velocidad(): Double = super.velocidad() * porcentajeDeVelocidad

  override def daño(): Double = peso * porcentajeDeDaño

  override def montablePor(vikingo: Vikingo): Boolean = {
    super.montablePor(vikingo) &&
      vikingo.peso < pesoSoportableMaximo
  }

}

abstract class CondicionParaMontar {
  def call(vikingo: Vikingo): Boolean
}

object CondicionBase extends CondicionParaMontar {
  override def call(vikingo: Vikingo): Boolean = true
}

class CondicionVikingoBarbaro extends CondicionParaMontar {

  var barbarosidadMinima: Double = 0

  override def call(vikingo: Vikingo): Boolean = vikingo.barbarosidad >= barbarosidadMinima

}

class CondicionObjetoEspecifico extends CondicionParaMontar {

  var objetoDeseado: Objeto = SistemaDeVuelo

  override def call(vikingo: Vikingo): Boolean = vikingo.objeto == objetoDeseado

}

