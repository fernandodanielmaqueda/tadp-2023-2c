package domain

import domain.festival.Hambre

class Jinete(vikingo: Vikingo, dragon: Dragon) extends PosibleCompetidor {

  private val modificadorDeHambreFijo: Hambre = 5

  def desmontar(): Vikingo = vikingo

  override def participarEnPosta(porcentajeDeHambre: Hambre): Unit = vikingo.participarEnPosta(modificadorDeHambreFijo)

  override def pescaMaxima(): Double = dragon.pesoSoportable() - vikingo.peso

  override def daño(): Double = dragon.daño() + vikingo.daño()

  override def velocidad(): Double = dragon.velocidad() - vikingo.peso

}
