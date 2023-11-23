package festival

trait Competidor {

  def peso: kg
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
  def darHambrePorParticiparEn(unaPosta: Posta): Competidor = this.incrementarNivelDeHambre(nivelDeHambreQueAlcanzariaTrasParticiparEn(unaPosta))

  def cumplePrerequisitosPropiosComoParaParticiparEn(unaPosta: Posta): Boolean = nivelDeHambreQueAlcanzariaTrasParticiparEn(unaPosta) < 100
  def puedeParticiparEn(unaPosta: Posta): Boolean = cumplePrerequisitosPropiosComoParaParticiparEn(unaPosta) && unaPosta.admiteA(this)
  def participarEn(unaPosta: Posta): Competidor = {
    if (!puedeParticiparEn(unaPosta)) throw new CompetidorNoPudoParticiparEnPostaException("El competidor no pudo participar en la posta")
    item.aplicarSobrePortador(this.darHambrePorParticiparEn(unaPosta))
  }

  def maximoDeKgDePescadoQuePuedeCargar: kg
  def daño: Daño

  def tieneUnArmaEquipada: Boolean
  def tieneMontura: Boolean

  def esMejorQue(otroCompetidor: Competidor)(unaPosta: Posta): Boolean = unaPosta.elPrimeroEsMejorQueElSegundo(this, otroCompetidor)

}