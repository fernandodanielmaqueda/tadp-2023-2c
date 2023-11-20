package festival

abstract class RequisitoDeMontura {
  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean
}

object RequisitoDeMonturaBasico extends RequisitoDeMontura {
  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unDragon.puedeRemontarVueloCon(unVikingo)
}

class RequisitoDeMonturaBarbarosidadMinima(minimoDeterminado: Barbarosidad) extends RequisitoDeMontura {
  require(minimoDeterminado >= 0, "El minimoDeterminado no puede ser negativo")

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.barbarosidad > minimoDeterminado
}

class RequisitoDeMonturaTenerItem(unItemEnParticular: Item) extends RequisitoDeMontura {
  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.tieneItem(unItemEnParticular)
}

object RequisitoDeMonturaNoSuperarDañoPropio extends RequisitoDeMontura {
  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.daño <= unDragon.dañoQueProduce
}

class RequisitoDeMonturaNoSuperarPesoDeterminado(pesoMaximo: kg) extends RequisitoDeMontura {
  require(pesoMaximo > 0, "El pesoMaximo debe ser positivo")

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = pesoMaximo >= unVikingo.peso
}