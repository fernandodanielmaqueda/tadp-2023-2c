package festival

object Main extends App {

  val unCombate = new Combate(0)

  val Hipo = Vikingo(50, 40, 1, 0, SistemaDeVuelo)
  val Astrid = Vikingo(60, 30, 2, 10, Hacha)
  val Patan = Vikingo(70, 20, 3, 20, Maza)

  object Patapez extends Vikingo(80, 10, 4, 30, new Comestible(5)) {

    override def cumplePrerequisitosPropiosComoParaParticiparEn(unaPosta: Posta): Boolean =
      super.cumplePrerequisitosPropiosComoParaParticiparEn(unaPosta) && nivelDeHambre <= 50

    override def cuantaHambreDeberiaIncrementarPorParticiparEn(unaPosta: Posta): Hambre =
      2 * unaPosta.incremento

  }

  val Chimuelo = FuriaNocturna(300, Set(new RequisitoDeMonturaTenerItem(SistemaDeVuelo)), 100)

  val FestivalDeInvierno = new Torneo(List(new Pesca, new Combate(1), new Carrera(5)), Set(Chimuelo), TorneoEstandarNoEmpatable)

  FestivalDeInvierno.inscribirA(List(Hipo, Astrid, Patan, Patapez))

}