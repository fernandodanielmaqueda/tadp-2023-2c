package domain

import domain.positive.PositiveDouble

package object festival {

  type kg = PositiveDouble
  type km_h = Double
  type Barbarosidad = Int
  type Hambre = Int
  type Danio = Double

  val velocidadBaseNormal = 60
}

object positive {
  import scala.language.implicitConversions

  class PositiveDouble private (val value: Double) extends AnyVal

  object PositiveDouble {

    def apply(v: Double): PositiveDouble = {
      require(v >= 0, "El valor debe ser positivo")
      new PositiveDouble(v)
    }

    implicit def toPositiveDouble(v: Double): PositiveDouble = PositiveDouble(v)
  }

  implicit def toDouble(nn: PositiveDouble) = nn.value
}