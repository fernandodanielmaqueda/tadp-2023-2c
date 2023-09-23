# frozen_string_literal: true
require_relative './alumno'
require_relative './document'
estado = Estado.new(finales_rendidos = 3, materias_aprobadas = 5, es_regular = true)
fede = Alumno.new(nombre = 'Fede', legajo = 1, telefono = 11, estado = estado)

@document = Document.new do
  alumno nombre: fede.nombre, legajo: fede.legajo do
    telefono { fede.telefono }
    estado es_regular: fede.estado.es_regular do
      finales_rendidos { fede.estado.finales_rendidos }
      materias_aprobadas { fede.estado.materias_aprobadas }
    end
  end
end
=begin
# Deberia ser lo mismo que
#
@document2 = Document.new {
  self.alumno(nombre: @fede.nombre, legajo: @fede.legajo) {
    self.telefono { @fede.telefono }
    self.estado(es_regular: true) {
      finales_rendidos { 3 }
      materias_aprobadas { 5 }
    }
  }
}
=end
