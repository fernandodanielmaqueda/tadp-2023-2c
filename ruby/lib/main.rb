require_relative 'alumno'
require_relative 'document'

@document1 = Document.new do
  alumno nombre: "Matias", legajo: "123456-7" do
    telefono { "1234567890" }
    estado es_regular: true do
      finales_rendidos { 3 }
      materias_aprobadas { 5 }
    end
  end
end
@document1.xml

@document2 = Document.new do
  alumno nombre: "Mati", legajo: "123456-7", edad: 27 do
  telefono { "12345678" }
  estado do
    value { "regular" }
  end
  no_children
  end
end
@document2.xml

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
#@document0.xml

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