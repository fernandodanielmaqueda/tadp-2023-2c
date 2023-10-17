class Custom

  attr_reader :block

  def initialize(&xml_block)
    raise "La anotacion Custom debe recibir un bloque que espera un solo parametro" unless xml_block and xml_block.arity == 1

    @xml_block = xml_block
  end

  def apply_to(klass_or_unbound_method)
    if klass_or_unbound_method.class != Class
      raise "La anotacion Custom solo puede ser utilizada en clases; no en: #{klass_or_unbound_method.name.to_s}"
    else
      klass_or_unbound_method.custom_proc = @xml_block
    end
  end

end

module Custom_Framework

  attr_accessor :custom_proc

end

Class_Serialize_Framework.include(Custom_Framework)