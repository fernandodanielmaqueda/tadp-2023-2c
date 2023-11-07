package domain

import domain.festival.{Barbarosidad, Daño, kg, km_h, velocidadBaseNormal}

abstract class Dragon(val peso: kg, val condicionParaMontar: CondicionParaMontar) {

  private val porcentajeDePesoSoportable: Double = 0.2

  def velocidad(): km_h = velocidadBaseNormal - peso

  def daño(): Daño

  def montablePor(vikingo: Vikingo): Boolean = condicionPeso(vikingo) && condicionParaMontar.call(vikingo)

  def pesoSoportable(): kg = peso * porcentajeDePesoSoportable

  private def condicionPeso(vikingo: Vikingo): Boolean = vikingo.peso <= pesoSoportable()

}

class FuriaNocturna(peso: kg, condicionParaMontar: CondicionParaMontar, val dañoBase: Daño) extends Dragon(peso, condicionParaMontar) {

  private val modificadorDeVelocidad: Int = 3

  override def velocidad(): km_h = super.velocidad() * modificadorDeVelocidad

  override def daño(): Daño = dañoBase

}

object Chimuelo extends FuriaNocturna(300, new CondicionObjetoEspecifico(SistemaDeVuelo), 100)

class NadderMortifero(peso: kg, condicionParaMontar: CondicionParaMontar) extends Dragon(peso, condicionParaMontar) {

  private val dañoBase: Daño = 150

  override def daño(): Daño = dañoBase

  override def montablePor(vikingo: Vikingo): Boolean = super.montablePor(vikingo) && vikingo.daño() < daño()

}

class Gronckle(peso: kg, condicionParaMontar: CondicionParaMontar, pesoSoportableMaximo: kg) extends Dragon(peso, condicionParaMontar) {

  private val modificadorDeVelocidad: Double = 0.5

  private val modificadorDeDaño: Int = 5

  override def velocidad(): km_h = super.velocidad() * modificadorDeVelocidad

  override def daño(): Daño = peso * modificadorDeDaño

  override def montablePor(vikingo: Vikingo): Boolean = super.montablePor(vikingo) && vikingo.peso < pesoSoportableMaximo

}

