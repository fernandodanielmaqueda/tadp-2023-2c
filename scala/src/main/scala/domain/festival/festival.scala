package domain.festival

object Main extends App {

  println(s"${new Vikingo(10, 20, 30, 0, null).peso} ${this}")
  println(s"${new Vikingo(-5, 20, 30, 0, null).peso} ${this}")
}

class Item {

}

class SistemaDeVuelo extends Item {

}

class Arma extends Item {

}

class Hacha extends Arma {

}

class Maza extends Arma {

}

class Comestible extends Item {

}

class Vikingo (val peso: kg, val velocidad: km_h, val barbarosidad: Barbarosidad, _nivelDeHambre: Hambre, var item: Item) {
  require(Range.inclusive(0, 100).contains(nivelDeHambre), "El nivelDeHambre debe ser un porcentaje valido")
  // nivelDeHambre >= 0 && nivelDeHambre <= 100

  def nivelDeHambre: Hambre = _nivelDeHambre

  def nivelDeHambre_(nuevoNivelDeHambre: Hambre): Unit = {
    require(nuevoNivelDeHambre >= 0, "El nivel Debe ser positivo")
  }

  //def barbarosidad: Barbarosidad = 1

  def porcentajeDeHambre: String = s"${nivelDeHambre}%"

  def significadoPorcentajeDeHambre: String = {
    if(porcentajeDeHambre == "0%") "panza llena, corazón contento"
    else throw new MyCustomException("El porcentajeDeHambre no tiene un significado asociado");
  }

  def puedeParticiparEnUnaPosta: Boolean = true

  def maximoDeKgDePescadoQuePuedeCargar: kg = (peso / 2) + (2 * barbarosidad)

}

abstract class Posta {

  def participar(competidores: List[Vikingo]): List[Vikingo]

}

object Pesca extends Posta
{

  def maximoDeKgDePescadoQuePuedeCargar(unVikingo: Vikingo): kg = (unVikingo.peso / 2) + (2 * unVikingo.barbarosidad)

  //def participar(competidores: List[Vikingo]): Vikingo = 123


}

object Combate extends Posta
{

}

object Carrera extends Posta
{

}

abstract class Dragon(val peso: kg) {

  def velocidadBase: km_h = velocidadBaseNormal

  def velocidadVuelo: km_h = velocidadBase - peso

  def danioQueProduce: Danio

  def hastaCuantoPuedeCargar: kg = peso * 0.2

  def puedeMontar(unVikingo: Vikingo): Boolean = unVikingo.peso <= this.hastaCuantoPuedeCargar

  //def puedeRemontarVuelo: Boolean =

}

class FuriaNocturna(_peso: kg, val danioQueProduce: Danio) extends Dragon(_peso) {

  override def velocidadVuelo: km_h = 3 * super.velocidadVuelo

}

class NadderMortifero(_peso: kg) extends Dragon(_peso) {

  def danioQueProduce: Danio = 150

}

class Gronckle(_peso: kg) extends Dragon(_peso) {

  override def velocidadBase: km_h = super.velocidadBase / 2

  def danioQueProduce: Danio = 5 * _peso

}

class Torneo(postas: List[Posta], dragones: List[Dragon]) {

}

class MyCustomException(mensaje: String) extends Exception(mensaje) {

}

object Hipo extends Vikingo(80, 10, 5, 50, new SistemaDeVuelo) {

}

object Astrid extends Vikingo(80, 10, 5, 50, new Hacha) {

}

object Patan extends Vikingo(80, 10, 5, 50, new Maza) {

}

object Patapez extends Vikingo(80, 10, 5, 50, new Comestible) {

  // override puedeParticiparEnUnaPosta: Boolean =
}

// val elFestivalDeInvierno = new Torneo([Hipo, Astrid, Patan, Patapez], )
