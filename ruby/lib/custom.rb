class Custom

  attr_reader :xml_block

  def initialize(&xml_block)
    raise "La anotacion Custom debe recibir un bloque que espera un solo parametro" if xml_block.nil? or xml_block.arity != 1

    @xml_block = xml_block
  end

  def apply_to(klass_or_unbound_method)
    if klass_or_unbound_method.class != Class
      raise "La anotacion Custom solo puede ser utilizada en clases; no en: #{klass_or_unbound_method.name.to_s}"
    else
      klass_or_unbound_method.custom_block = @xml_block
    end
  end

end

module Custom_Framework

  attr_accessor :custom_block

end

Class_Serialize_Framework.include(Custom_Framework)