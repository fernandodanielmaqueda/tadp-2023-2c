require_relative 'serialize_framework'
require_relative 'unboundmethod'
require_relative 'class'
require_relative 'object'

require_relative 'annotation'
require_relative 'label'
require_relative 'ignore'
require_relative 'inline'
require_relative 'custom'
# require_relative 'class'
# require_relative 'object'

ñLabelñ("tester")
class Test
  ñIgnoreñ
  ñIgnoreñ
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

puts Document.serialize(Test.new).xml

#puts Test.class_associations.inspect

#Test.new.method(:what).label

# Para solucionar el warning de redefinir method_missing en Object:
#Mensaje: unObjeto.respond_to? | Método: def respond_to_missing(nombre_mensaje, include_private = false)


#puts Object.method(:new).instance_variable.inspect
#clone name original_name owner receiver source_location super_method Method
# singleton_class define_singleton_method instance_variables instance_variable_set singleton_method singleton_methods methods extend Object

class Prueba

  def self.clase

  end
  def instancia
  end
end