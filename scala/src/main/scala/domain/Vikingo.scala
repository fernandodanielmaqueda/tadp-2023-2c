package domain


class Vikingo implements PosibleCompetidor {
  var peso: Double = 0;
  var velocidad: Double = 0;
  var barbarosidad: Double = 0;
  var hambre: Double = 0;
  var objeto: Objeto = SistemaDeVuelo;

  def pescaMaxima(): Double = {
    this.peso * 0.5 + this.barbarosidad * 2;
  }

  def daño(): Double = {
    if (objeto.isInstanceOf[Arma])
      this.barbarosidad + objeto.modificador();
    else
      this.barbarosidad
  }

  def participarEnPosta(porcentajeDeHambre: Double): Unit = {
    this.hambre += this.hambre * porcentajeDeHambre;
  }
}

object Posta {
  def pesca(vikingos: List[Vikingo]): Vikingo = {
    vikingos.foreach(vikingo => vikingo.participarEnPosta(0.05))
    vikingos.maxBy(vikingo => vikingo.pescaMaxima())
  }
  def combate(vikingos: List[Vikingo]): Vikingo = {
    vikingos.foreach(vikingo => vikingo.participarEnPosta(0.10))
    vikingos.maxBy(vikingo => vikingo.daño())
  }
  def carrera(vikingos: List[Vikingo], kilometros: Int): Vikingo = {
    vikingos.foreach(vikingo => vikingo.participarEnPosta(0.01 * kilometros))
    vikingos.maxBy(vikingo => vikingo.velocidad)
  }

}

object Patapez extends Vikingo {
  override def participarEnPosta(porcentajeDeHambre: Double): Unit = {
    super.participarEnPosta(porcentajeDeHambre)
    if (objeto.isInstanceOf[Comestible]) {
      this.hambre -= objeto.modificador()
    }
  }
}