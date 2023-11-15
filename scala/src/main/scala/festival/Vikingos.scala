package festival

class Vikingo (val peso: kg, val velocidad: km_h, val barbarosidad: Barbarosidad, private var _nivelDeHambre: Hambre, val item: Item) {
  require(peso > 0, "El peso debe ser positivo")
  require(velocidad > 0, "La velocidad debe ser positiva")
  require(barbarosidad >= 0, "La barbarosidad debe ser mayor o igual a 0")
  require(Range.inclusive(0, 100).contains(_nivelDeHambre), "El nivelDeHambre debe ser un porcentaje valido")

  def nivelDeHambre: Hambre = _nivelDeHambre

  def nivelDeHambreAlQueIncrementaria(incremento: Hambre): Hambre = Math.min(100, _nivelDeHambre + incremento)

  def incrementarNivelDeHambre(incremento: Hambre): Unit = {
    _nivelDeHambre = nivelDeHambreAlQueIncrementaria(incremento)
  }

  def decrementarNivelDeHambre(decremento: Hambre): Unit = {
    _nivelDeHambre = Math.max(0, _nivelDeHambre - decremento)
  }

  def porcentajeDeHambre: String = s"${nivelDeHambre}%"

  def significadoPorcentajeDeHambre: String = {
    if(nivelDeHambre == 0) "panza llena, corazón contento"
    else throw new MyCustomException("El porcentajeDeHambre no tiene un significado asociado");
  }

  def competir(unaPosta: Posta): Unit = {
    if (!puedeCompetirEn(unaPosta)) throw new MyCustomException("El vikingo no puede competir en esa posta")

  }

  def puedeCompetirEn(unaPosta: Posta): Boolean = true

  def maximoDeKgDePescadoQuePuedeCargar: kg = (peso / 2) + (2 * barbarosidad)

  def daño: Daño = barbarosidad + item.daño

  def montar(unDragon: Dragon): Jinete = ???

}