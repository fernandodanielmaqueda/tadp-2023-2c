package domain

import domain.festival.{Barbarosidad, Daño, Hambre, km_h}

trait PosibleCompetidor {

  def esMejorQue(competidor: PosibleCompetidor)(posta: Posta): Boolean =
    posta.capacidadMedida(this) > posta.capacidadMedida(competidor)

  def participarEnPosta(porcentajeDeHambre: Hambre): Unit

  def hambre: Hambre

  def barbarosidad: Barbarosidad

  def pescaMaxima(): Double

  def daño(): Daño

  def velocidad(): km_h

  def tieneObjeto(objeto: Objeto): Boolean

}
