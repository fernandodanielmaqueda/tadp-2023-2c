package festival

trait Competidor {

  //def peso: kg
  def velocidad: km_h
  def barbarosidad: Barbarosidad
  def item: Item

  def nivelDeHambre: Hambre

  def nivelDeHambreAlQueIncrementariaCon(incremento: Hambre): Hambre
  def nivelDeHambreAlQueDecrementariaCon(decremento: Hambre): Hambre

  def incrementarNivelDeHambre(incremento: Hambre): Competidor
  def decrementarNivelDeHambre(decremento: Hambre): Competidor

  def cuantaHambreDeberiaIncrementarPorParticiparEn(unaPosta: Posta): Hambre = unaPosta.incremento
  def nivelDeHambreQueAlcanzariaTrasParticiparEn(unaPosta: Posta): Hambre = nivelDeHambreAlQueIncrementariaCon(cuantaHambreDeberiaIncrementarPorParticiparEn(unaPosta))
  def darHambrePorParticiparEn(unaPosta: Posta): Competidor = {
    this.incrementarNivelDeHambre(nivelDeHambreQueAlcanzariaTrasParticiparEn(unaPosta))
  }

  def puedeCompetirEn(unaPosta: Posta): Boolean = nivelDeHambreQueAlcanzariaTrasParticiparEn(unaPosta) < 100
  def competirEn(unaPosta: Posta): Competidor = {
    if (!puedeCompetirEn(unaPosta)) throw new MyCustomException("El competidor no puede competir en la posta")
    item.aplicacionPropia(this.darHambrePorParticiparEn(unaPosta))
  }

  def maximoDeKgDePescadoQuePuedeCargar: kg
  def daño: Daño

  def tieneUnArmaEquipada: Boolean
  def tieneMontura: Boolean

  def esMejorQue(otroCompetidor: Competidor)(unaPosta: Posta): Boolean = unaPosta.elPrimeroEsMejorQueElSegundo(this, otroCompetidor)

}