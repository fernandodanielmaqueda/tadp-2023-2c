class Extra

  def initialize(symbol, &block)
    raise "La anotacion Extra debe recibir un bloque que no espera parametros" unless block and block.arity == 0

    @symbol = symbol.to_sym
    @block = block
  end

  def apply_to(klass_or_unbound_method)
    if klass_or_unbound_method.class != Class
      raise "La anotacion Extra solo puede ser utilizada en clases; no en: #{klass_or_unbound_method.name.to_s}"
    else
      raise "La clase #{klass_or_unbound_method.to_s} ya posee el atributo agregado: #{@symbol.to_s}" if klass_or_unbound_method.extra_symbols[@symbol] or klass_or_unbound_method.instance_methods.include?(@symbol)
      klass_or_unbound_method.extra_symbols[@symbol] = @block
    end
  end

end

module Extra_Framework

  attr_writer :extra_symbols

  def extra_symbols
    @extra_symbols ||= Hash.new
  end

end

Class_Serialize_Framework.include(Extra_Framework)