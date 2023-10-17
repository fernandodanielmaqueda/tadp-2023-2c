class Object

  def method_missing(annotation, *parameters, &block)
    super if /#{Emoticon}(.+)#{Emoticon}/.match(annotation).nil?

    raise "La clase #{$1} no existe" unless ObjectSpace.const_defined?($1)

    Object.annotations_buffer << ObjectSpace.const_get($1).new(*parameters, &block)
  end

  def self.inherited(created_subclass)
    created_subclass.class_associations = @annotations_buffer
    @annotations_buffer = []

    created_subclass.apply_class_annotations
  end

  def self.attr_reader(*symbols)
    self.attr_reader_and_accessor(*symbols)
    super
  end

  def self.attr_accessor(*symbols)
    self.attr_reader_and_accessor(*self.agregar_simbolos_setter(symbols))
    super
  end

  def self.method_added(method_symbol)
    self.asociar_instance_method_con_annotations(method_symbol)
    Object.annotations_buffer = []

    self.unbound_instance_methods[method_symbol] = self.instance_method(method_symbol)

    self.apply_instance_method_annotations(method_symbol)
  end

  private

  def self.agregar_simbolos_setter(symbols)
    symbols.map { |symbol| [symbol, symbol.to_s.insert(-1, "=").to_sym] }.flatten
  end

  def self.attr_reader_and_accessor(*symbols)
    symbols.each do |symbol|
      self.asociar_instance_method_con_annotations(symbol)
    end

    Object.annotations_buffer = []
  end

  def self.asociar_instance_method_con_annotations(method_symbol)
    self.instance_methods_associations[method_symbol] = (self.instance_methods_associations[method_symbol] || Array.new) + Object.annotations_buffer
  end

end