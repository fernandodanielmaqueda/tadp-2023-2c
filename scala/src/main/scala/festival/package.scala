package object festival {

  type kg = Double
  type km = Int
  type km_h = Double
  type Barbarosidad = Int
  type Daño = Double
  type Hambre = Int

  val velocidadBaseNormal = 60

  class CompetidorNoPudoParticiparEnPostaException(descripcion: String) extends Exception(descripcion)
  class NoSePudoMontarDragonException(descripcion: String) extends Exception(descripcion)

}