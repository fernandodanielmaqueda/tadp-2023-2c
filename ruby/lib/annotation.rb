EMOTICON = "ñ" # ✨

$annotations_buffer = []
$associations = Hash.new

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

class Object

  def method_missing(annotation, *parameters, &block)
    super if /#{EMOTICON}(.+)#{EMOTICON}/.match(annotation).nil?

    raise "La clase #{$1} no existe" unless ObjectSpace.const_defined?($1) #NameError: wrong constant name (si el nombre escrito de la clase arranca con minúscula)

    $annotations_buffer << ObjectSpace.const_get($1).new(*parameters, &block) # En caso de que la clase no exista o no pueda ser instanciada con los parámetros dados debe lanzarse una excepción acorde: #ArgumentError #TypeError
  end

  def self.inherited(created_subclass)
    #puts self.inspect
    #puts created_subclass.inspect
    #puts $annotations_buffer.inspect
    #puts "--"
    $associations[created_subclass.object_id] = $annotations_buffer
    $annotations_buffer = []
  end

  def self.method_added(method_symbol)
    #puts self.inspect
    #puts method_symbol.inspect
    #puts $annotations_buffer.inspect
    #puts "--"
    $associations[self.instance_method(method_symbol).object_id] = $annotations_buffer
    # TODO: Hacer append del array en el value
    $annotations_buffer = []
  end

  def self.attr_reader(*symbols)
    super
    # puts "attr_reader"
    # puts self.inspect
    # puts symbols.inspect
    # puts $annotations_buffer.inspect
    # puts "--"
    symbols.each do |symbol|
      $associations[self.instance_method(symbol).object_id] = $annotations_buffer
      # TODO: Hacer append del array en el value
    end
    $annotations_buffer = []
  end

  def self.attr_accessor(*symbols)
    super
    # puts "attr_accessor"
    # puts self.inspect
    # puts symbols.inspect
    # puts $annotations_buffer.inspect
    # puts "--"
    symbols.each do |symbol|
      $associations[self.instance_method(symbol).object_id] = $annotations_buffer
      # TODO: Hacer append del array en el value
    end
    $annotations_buffer = []
  end

end