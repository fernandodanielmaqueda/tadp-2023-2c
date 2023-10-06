class Docente

  attr_accessor :nombre, :cursos

  def initialize(nombre, cursos)
    @nombre = nombre
    @cursos = cursos
  end
end