package festival

// import scala.util.{Try, Success, Failure}

case class Vikingo (peso: kg, velocidad: km_h, barbarosidad: Barbarosidad, _nivelDeHambre: Hambre, item: Item) extends Competidor with Participante {
  require(peso > 0, "El peso debe ser positivo")
  require(velocidad > 0, "La velocidad debe ser positiva")
  require(barbarosidad >= 0, "La barbarosidad no puede ser negativa")
  require(Range.inclusive(0, 100).contains(_nivelDeHambre), "El nivelDeHambre debe ser un porcentaje valido")

  def nivelDeHambre: Hambre = _nivelDeHambre

  def nivelDeHambreAlQueIncrementariaCon(incremento: Hambre): Hambre = Math.min(100, nivelDeHambre + incremento)

  def nivelDeHambreAlQueDecrementariaCon(decremento: Hambre): Hambre = Math.max(0, nivelDeHambre - decremento)


  def incrementarNivelDeHambre(incremento: Hambre): Vikingo = this.copy(_nivelDeHambre = nivelDeHambreAlQueIncrementariaCon(incremento))

  def decrementarNivelDeHambre(decremento: Hambre): Vikingo = this.copy(_nivelDeHambre = nivelDeHambreAlQueDecrementariaCon(decremento))

  def maximoDeKgDePescadoQuePuedeCargar: kg = (peso / 2) + (2 * barbarosidad)

  def daño: Daño = barbarosidad + item.daño

  def tieneUnArmaEquipada: Boolean = item.isInstanceOf[Arma]
  def tieneMontura: Boolean = false

  def tieneItem(unItemEnParticular: Item): Boolean = unItemEnParticular == item

  def montar(unDragon: Dragon): Jinete = {
    if (!unDragon.loPuedeMontar(this)) throw new NoSePudoMontarDragonException("El vikingo no pudo montar al dragón")
    Jinete(this, unDragon)
  }

  def determinarParticipacionEn(unaPosta: Posta, dragonesDisponibles: Set[Dragon]): Option[Competidor] = {
    unaPosta.ordenarPorMejor(
      (
        for (dragonQuePuedeLlegarAMontar <- dragonesDisponibles.toList if dragonQuePuedeLlegarAMontar.loPuedeMontar(this))
        yield this.montar(dragonQuePuedeLlegarAMontar)
      ).appended(this).filter(_.puedeParticiparEn(unaPosta))
    ).headOption
  }

}