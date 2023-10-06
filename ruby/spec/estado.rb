class Estado
  attr_reader :finales_rendidos, :materias_aprobadas, :es_regular

  def initialize(finales_rendidos, materias_aprobadas, es_regular)
    @finales_rendidos = finales_rendidos
    @materias_aprobadas = materias_aprobadas
    @es_regular = es_regular
  end
end