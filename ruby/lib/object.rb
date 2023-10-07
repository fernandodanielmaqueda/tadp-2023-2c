EMOTICON = "ñ" # ✨

class Object

  def self.annotations_buffer
    @annotations_buffer ||= Array.new
  end
  def self.annotations_buffer=(array)
    @annotations_buffer = array
  end

  def method_missing(annotation, *parameters, &block)
    super if /#{EMOTICON}(.+)#{EMOTICON}/.match(annotation).nil?

    raise "La clase #{$1} no existe" unless ObjectSpace.const_defined?($1) #NameError: wrong constant name (si el nombre escrito de la clase arranca con minúscula)

    Object.annotations_buffer << ObjectSpace.const_get($1).new(*parameters, &block) # En caso de que la clase no exista o no pueda ser instanciada con los parámetros dados debe lanzarse una excepción acorde: #ArgumentError #TypeError
  end

  def self.inherited(created_subclass)
    # puts "self.inherited"
    # puts self.inspect
    # puts created_subclass.inspect
    # puts @annotations_buffer.inspect
    created_subclass.class_associations = @annotations_buffer
    @annotations_buffer = []
    #puts "--"
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
    # puts "self.method_added"
    # puts self.inspect
    # puts method_symbol.inspect
    # puts Object.annotations_buffer.inspect
    # puts self.method_associations[method_symbol].inspect
    self.method_associations[method_symbol] = (self.method_associations[method_symbol] || Array.new) + Object.annotations_buffer
    Object.annotations_buffer = []
    # puts "--"
  end

  private

  def self.attr_reader_and_accessor(*symbols)
    # puts "attr_reader / attr_accessor"
    # puts self.inspect
    # puts symbols.inspect
    # puts Object.annotations_buffer.inspect
    symbols.each do |symbol|
      self.method_associations[symbol] = (self.method_associations[symbol] || Array.new) + Object.annotations_buffer
    end
    # puts self.method_associations.inspect
    Object.annotations_buffer = []
    # puts "--"
  end

end