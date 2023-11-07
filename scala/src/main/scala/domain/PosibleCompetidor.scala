package domain

import domain.festival.{Daño, Hambre, km_h}

trait PosibleCompetidor {

  def participarEnPosta(porcentajeDeHambre: Hambre): Unit

  def pescaMaxima(): Double

  def daño(): Daño

  def velocidad(): km_h

}
