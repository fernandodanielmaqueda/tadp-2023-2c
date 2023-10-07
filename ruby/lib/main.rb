require_relative 'annotation'
require_relative 'label'
require_relative 'ignore'
require_relative 'inline'
require_relative 'custom'
require_relative 'class'
require_relative 'object'

ñLabelñ("tester")
class Test
  ñIgnoreñ
  attr_accessor :testing, :what
  ñLabelñ('boenas')
  attr_accessor :different
  ñLabelñ(5)
  attr_accessor :number

  def initialize
    @testing=10
    @what='asdfs'
    @different='hola'
    @number='hola'
  end

  ñLabelñ('prueba')
  def testing
    @testing
  end
end

#Mensaje: unObjeto.respond_to? | Método: def respond_to_missing(nombre_mensaje, include_private = false)