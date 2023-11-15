package domain

import domain.festival.{Barbarosidad, Hambre}

class Jinete(vikingo: Vikingo, dragon: Dragon) extends PosibleCompetidor {

  require(dragon.montablePor(vikingo))

  private val modificadorDeHambreFijo: Hambre = 5

  def desmontar(): Vikingo = vikingo

  override def barbarosidad: Barbarosidad = vikingo.barbarosidad

  override def hambre: Hambre = vikingo.hambre

  override def participarEnPosta(porcentajeDeHambre: Hambre): Unit = vikingo.participarEnPosta(modificadorDeHambreFijo)

  override def tieneObjeto(objeto: Objeto): Boolean = vikingo.objeto == objeto

  override def pescaMaxima(): Double = dragon.pesoSoportable() - vikingo.peso

  override def daño(): Double = dragon.daño() + vikingo.daño()

  override def velocidad(): Double = dragon.velocidad() - vikingo.peso

}
