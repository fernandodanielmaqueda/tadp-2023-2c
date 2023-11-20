package festival

object unCombate extends Combate(0)

object Hipo extends Vikingo(50, 40, 1, 0, SistemaDeVuelo)

object Astrid extends Vikingo(60, 30, 2, 10, Hacha)

object Patan extends Vikingo(70, 20, 3, 20, Maza)

object Patapez extends Vikingo(80, 10, 4, 30, new Comestible(5)) {

  override def puedeCompetirEn(unaPosta: Posta): Boolean = super.puedeCompetirEn(unaPosta) && nivelDeHambre <= 50

  override def cuantaHambreDeberiaIncrementarPorParticiparEn(unaPosta: Posta): Hambre = 2 * unaPosta.incremento

}

//object FestivalDeInvierno extends Torneo(List(new Pesca, new Combate(1), new Carrera(100)), List()).anotarse(List(Hipo, Astrid, Patan, Patapez))