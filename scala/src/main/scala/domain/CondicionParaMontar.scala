package domain

import domain.festival.Barbarosidad

abstract class CondicionParaMontar {
  def call(vikingo: Vikingo): Boolean
}

object CondicionBase extends CondicionParaMontar {
  override def call(vikingo: Vikingo): Boolean = true
}

class CondicionVikingoBarbaro(val barbarosidadMinima: Barbarosidad) extends CondicionParaMontar {

  override def call(vikingo: Vikingo): Boolean = vikingo.barbarosidad >= barbarosidadMinima

}

class CondicionObjetoEspecifico(val objetoDeseado: Objeto) extends CondicionParaMontar {

  override def call(vikingo: Vikingo): Boolean = vikingo.objeto == objetoDeseado

}
