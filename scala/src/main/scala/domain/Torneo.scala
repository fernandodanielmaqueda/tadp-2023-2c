package domain

class Torneo (val postas: List[Posta], val dragones: List[Dragon], var competidores: List[PosibleCompetidor] = List[PosibleCompetidor]()) {

  def anotarCompetidores(_competidores: List[PosibleCompetidor]): Unit =
    competidores = _competidores

  def ejecutar(): Unit = {
    postas.foreach(posta => competidores = posta.participar(competidores))
  }
}
