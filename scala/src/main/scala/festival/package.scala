package object festival {

  type kg = Double
  type km = Int
  type km_h = Double
  type Barbarosidad = Int
  type Daño = Double
  type Hambre = Int

  val velocidadBaseNormal = 60

  class MyCustomException(mensaje: String) extends Exception(mensaje)

}