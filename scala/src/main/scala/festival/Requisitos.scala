package festival

abstract class Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean

}

object RequisitoBasico extends Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unDragon.puedeRemontarVueloCon(unVikingo)

}

class RequisitoBarbarosidadMinima(minimoDeterminado: Barbarosidad) extends Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.barbarosidad > minimoDeterminado

}

class RequisitoTenerItem(unItemEnParticular: Item) extends Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.tieneItem(unItemEnParticular)

}

object RequisitoNoSuperarDañoPropio extends Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = unVikingo.daño <= unDragon.dañoQueProduce

}

class RequisitoNoSuperarPesoDeterminado(pesoMaximo: kg) extends Requisito {

  def seCumplePor(unVikingo: Vikingo, unDragon: Dragon): Boolean = pesoMaximo >= unVikingo.peso

}