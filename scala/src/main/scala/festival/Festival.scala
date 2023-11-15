package festival

object Hipo extends Vikingo(50, 10, 1, 0, new SistemaDeVuelo) {

}

object Astrid extends Vikingo(60, 20, 2, 10, new Hacha(30)) {

}

object Patan extends Vikingo(70, 30, 3, 20, new Maza(100)) {

}

object Patapez extends Vikingo(80, 40, 4, 30, new Comestible(15)) {

  override def puedeCompetirEn(unaPosta: Posta): Boolean = !(nivelDeHambre > 50)

  // Falta que le da 2 * de hambre que al resto participar en las postas

}

//val elFestivalDeInvierno = new Torneo(List(new Pesca, new Combate(1), new Carrera(100)), List()).anotarse(List(Hipo, Astrid, Patan, Patapez))