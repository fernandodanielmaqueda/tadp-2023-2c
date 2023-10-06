class Curso

  attr_accessor :materia, :cantidad_alumnos

  def initialize(materia, cantidad_alumnos)
    @materia = materia
    @cantidad_alumnos = cantidad_alumnos
  end
end