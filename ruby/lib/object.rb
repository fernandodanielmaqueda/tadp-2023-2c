Emoticon = "ñ" # ✨

class Object

  def method_missing(annotation, *parameters, &block)
    super if /#{Emoticon}(.+)#{Emoticon}/.match(annotation).nil?

    raise "La clase #{$1} no existe" unless ObjectSpace.const_defined?($1) #NameError: wrong constant name (si el nombre escrito de la clase arranca con minúscula)

    Object.annotations_buffer << ObjectSpace.const_get($1).new(*parameters, &block) # En caso de que la clase no exista o no pueda ser instanciada con los parámetros dados debe lanzarse una excepción acorde: #ArgumentError #TypeError
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
    self.attr_reader_and_accessor(*(symbols.map { |symbol| [symbol, symbol.to_s.insert(-1, "=").to_sym] }.flatten))
    super
  end

  def self.method_added(method_symbol)
    self.instance_methods_associations[method_symbol] = (self.instance_methods_associations[method_symbol] || Array.new) + Object.annotations_buffer
    Object.annotations_buffer = []

    self.unbound_instance_methods[method_symbol] = self.instance_method(method_symbol)

    self.apply_instance_method_annotations(method_symbol)
  end

  private

  def self.attr_reader_and_accessor(*symbols)
    symbols.each do |symbol|
      self.instance_methods_associations[symbol] = (self.instance_methods_associations[symbol] || Array.new) + Object.annotations_buffer
    end
    Object.annotations_buffer = []
  end

end