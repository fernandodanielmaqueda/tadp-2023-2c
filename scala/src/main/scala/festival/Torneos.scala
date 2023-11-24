package festival

class TorneoEstandar(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar

class TorneoEliminacion(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon], N: Int) extends ReglasDeTorneoEstandar {

  override def quienesPasan(competidores: List[Vikingo]): List[Vikingo] = competidores.dropRight(N)

}

class TorneoInverso(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar {

  override def quienesPasan(resultantes: List[Vikingo]): List[Vikingo] = resultantes.drop(resultantes.length / 2)

  override def decisionGanador(restantes: List[Vikingo]): Vikingo = restantes.last

}

class TorneoConVetoDeDragones(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon], condicion: Dragon => Boolean) extends ReglasDeTorneoEstandar {

  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes, conjuntoDeDragones.filter(condicion), postaActual)

}

class TorneoConHandicap(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneoEstandar {

  override def preparacion(vikingosRestantes: List[Vikingo], conjuntoDeDragones: Set[Dragon], postaActual: Posta): List[Competidor] =
    super.preparacion(vikingosRestantes.reverse, conjuntoDeDragones, postaActual)

}

class TorneoPorEquipos(val serieDePostas: List[Posta], val conjuntoDeDragones: Set[Dragon]) extends ReglasDeTorneo[Equipo] {

  def comoAlistarA(equiposRestantes: List[Equipo]): List[Vikingo] = equiposRestantes.map(_.conjuntoDeVikingos.toList).transpose.flatten

  def reagruparA(nuevosVikingos: List[Vikingo], equiposActuales: List[Equipo]): List[Equipo] = equiposActuales.map

  def decisionGanador(restantes: List[Equipo]): Equipo = restantes.maxBy(_.conjuntoDeVikingos.size)

}