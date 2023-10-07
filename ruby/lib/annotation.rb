require_relative 'document'

EMOTICON = "ñ" # ✨

# class Annotation
#   attr_accessor :duenio
#   def ownerIsClass?
#     self.duenio.is_a?(Class)
#   end
#   def ownerIsClassOf?(object)
#     @duenio.eql?(object.class)
#   end
#   def doAnnotationAction()
#   end
# end

class Label #< Annotation
  attr_accessor :name
  def initialize(name)
    @name = name
  end

  # def doAnnotationAction
  #   super()
  #   @name
  # end
end

class Ignore #< Annotation

  # def doAnnotationAction ()
  #   super
  #   ''
  # end
end

class Inline #< Annotation
  attr_accessor :block
  def initialize(&block)
    @block = block
  end
end

class Custom #< Annotation
  attr_accessor :block
  def initialize(&block)
    @block = block
  end
end

class Class

  def class_associations
    @class_associations ||= Array.new
  end
  def class_associations=(array)
    @class_associations = array
  end

  def method_associations
    @method_associations ||= Hash.new
  end
  def method_associations=(hash)
    @method_associations = hash
  end

end

class Object

  def method_missing(annotation, *parameters, &block)
    super if /#{EMOTICON}(.+)#{EMOTICON}/.match(annotation).nil?

    raise "La clase #{$1} no existe" unless ObjectSpace.const_defined?($1) #NameError: wrong constant name (si el nombre escrito de la clase arranca con minúscula)

    Object.annotations_buffer << ObjectSpace.const_get($1).new(*parameters, &block) # En caso de que la clase no exista o no pueda ser instanciada con los parámetros dados debe lanzarse una excepción acorde: #ArgumentError #TypeError
  end

  def self.inherited(created_subclass)
    puts "self.inherited"
    puts self.inspect
    puts created_subclass.inspect
    puts self.annotations_buffer.inspect
    created_subclass.class_associations = self.annotations_buffer
    self.annotations_buffer = []
    puts "--"
  end

  def self.attr_reader(*symbols)
    super
    self.attr_reader_and_accessor(*symbols)
  end

  def self.attr_accessor(*symbols)
    super
    self.attr_reader_and_accessor(*symbols)
  end

  def self.method_added(method_symbol)
    puts "self.method_added"
    puts self.inspect
    puts method_symbol.inspect
    puts Object.annotations_buffer.inspect
    self.method_associations[method_symbol] = Object.annotations_buffer
    # puts self.instance_method(method_symbol).inspect
    # TODO: Hacer append del array en el value
    Object.annotations_buffer = []
    puts "--"
  end

  def self.annotations_buffer
    @annotations_buffer ||= Array.new
  end
  def self.annotations_buffer=(array)
    @annotations_buffer = array
  end

  private

  def self.attr_reader_and_accessor(*symbols)

    puts "attr_reader / attr_accessor"
    puts self.inspect
    puts symbols.inspect
    puts Object.annotations_buffer.inspect
    symbols.each do |symbol|
      self.method_associations[symbol] = Object.annotations_buffer
      # puts self.instance_method(symbol)
      # TODO: Hacer append del array en el value
    end
    puts self.method_associations.inspect
    Object.annotations_buffer = []
    puts "--"
  end

end