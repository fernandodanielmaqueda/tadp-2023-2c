require_relative 'document'

# Anexo
# puts Tag
#   .with_label('alumno')
#   .with_attribute('nombre', 'Mati')
#   .with_attribute('legajo', '123456-7')
#   .with_attribute('edad', 27)
#   .with_child(
#     Tag.with_label('telefono')
#        .with_child('12345678')
#   ) .
#   with_child(
#     Tag
#       .with_label('estado')
#       .with_child(
#         Tag
#           .with_label('value')
#           .with_child('regular')
#       )
#   ) .
#   with_child(Tag.with_label('no_children'))
#   .xml

# Punto 1
# @document1 = Document.new do
#   alumno nombre: "Matias", legajo: "123456-7" do
#     telefono { "1234567890" }
#     estado es_regular: true do
#       finales_rendidos { 3 }
#       materias_aprobadas { 5 }
#     end
#   end
# end
# #puts "@document1: #{@document1.inspect}"
# puts @document1.xml

# @document2 = Document.new do
#   alumno nombre: "Mati", legajo: "123456-7", edad: 27 do
#   telefono { "12345678" }
#   estado do
#     value { "regular" }
#   end
#   no_children
#   end
# end
#puts @document2.xml

#estado = Estado.new(finales_rendidos = 3, materias_aprobadas = 5, es_regular = true)
#fede = Alumno.new(nombre = 'Fede', legajo = 1, telefono = 11, estado = estado)

# @document0 = Document.new do
#   alumno nombre: fede.nombre, legajo: fede.legajo do
#     telefono { fede.telefono }
#     estado es_regular: fede.estado.es_regular do
#       finales_rendidos { fede.estado.finales_rendidos }
#       materias_aprobadas { fede.estado.materias_aprobadas }
#     end
#   end
# end
#puts @document0.xml

=begin
# Deberia ser lo mismo que
#
@document0 = Document.new {
  self.alumno(nombre: @fede.nombre, legajo: @fede.legajo) {
    self.telefono { @fede.telefono }
    self.estado(es_regular: true) {
      finales_rendidos { 3 }
      materias_aprobadas { 5 }
    }
  }
}
=end

# Punto 2
class Alumno
  attr_reader :nombre, :legajo, :estado
  def initialize(nombre, legajo, telefono, estado)
    @nombre = nombre
    @legajo = legajo
    @telefono = telefono
    @estado = estado
  end
end

class Estado
  attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
  def initialize(finales_rendidos, materias_aprobadas, es_regular)
    @finales_rendidos = finales_rendidos
    @materias_aprobadas = materias_aprobadas
    @es_regular = es_regular
  end
end

unEstado = Estado.new(3, 5, true)
unAlumno = Alumno.new("Matias","123456-8", "1234567890", unEstado)

documento_manual1 = Document.new do
  alumno nombre: unAlumno.nombre, legajo: unAlumno.legajo do
    estado finales_rendidos: unAlumno.estado.finales_rendidos,
           materias_aprobadas: unAlumno.estado.materias_aprobadas,
           es_regular: unAlumno.estado.es_regular
  end
end

documento_automatico1 = Document.serialize(unAlumno)

puts documento_automatico1.xml
puts "Coinciden manual y automatico: #{documento_manual1.xml == documento_automatico1.xml}" # Esto debe cumplirse

class Curso
  attr_accessor :materia, :cantidad_alumnos

  def initialize(materia, cantidad_alumnos)
    @materia = materia
    @cantidad_alumnos = cantidad_alumnos
  end
end

class Docente
  attr_accessor :nombre, :cursos

  def initialize(nombre, cursos)
    @nombre = nombre
    @cursos = cursos
  end
end

unDocente = Docente.new("Nico", [Curso.new("tadp", 40), Curso.new("pdep", 35)])

documento_automatico2 = Document.serialize(unDocente)
puts documento_automatico2.xml