class Inline

  attr_reader :block

  def initialize(&block)
    raise "La anotacion Inline debe recibir un bloque que espera un solo parametro" if block.nil? or block.arity != 1

    @block = block
  end

  def apply_to(klass_or_unbound_method)
    if klass_or_unbound_method.class == Class
      raise "No se puede utilizar la anotacion Inline en clases: #{klass_or_unbound_method.inspect}"
    else
      klass_or_unbound_method.inline_proc = @block
    end
  end

end

module Inline_Framework

  attr_accessor :inline_proc

end

Method_Serialize_Framework.include(Inline_Framework)