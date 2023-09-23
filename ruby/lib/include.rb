# frozen_string_literal: true
require_relative './alumno'
require_relative './document'

@fede = Alumno.new(nombre = 'Fede', legajo = 1, telefono = 11, estado = nil)
@document = Document.new do
  alumno nombre: @fede.nombre, legajo: @fede.legajo do
    telefono { @fede.telefono }
    estado es_regular: @fede.estado.es_regular do
      finales_rendidos { @fede.estado.finales_rendidos }
      materias_aprobadas { @fede.estado.materias_aprobadas }
    end
  end
end
puts @document.block.instance_variables