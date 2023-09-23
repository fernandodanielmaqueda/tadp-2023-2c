# frozen_string_literal: true

require_relative '../lib/estado'

class Alumno
  attr_reader :nombre, :legajo, :estado, :telefono
  def initialize(nombre, legajo, telefono, estado)
    @nombre = nombre
    @legajo = legajo
    @telefono = telefono
    @estado = estado
  end
end
