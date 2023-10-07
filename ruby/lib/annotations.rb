$annotations = Array.new

class Annotations
  attr_accessor :owner
  def ownerIsClass?
    self.owner.is_a?(Class)
  end
  def ownerIsClassOf?(object)
    @owner.eql?(object.class)
  end
  def doAnnotationAction()
  end
end


class Label < Annotations
  attr_accessor :param
  def initialize(param)
    @param = param
  end

  def doAnnotationAction
    super()
    @param
  end
end

class Ignore < Annotations
  def initialize
    #puts 'nope'
  end
  def doAnnotationAction ()
    super
    ''
  end
end

class Inline < Annotations
  attr_accessor :block
  def initialize(&block)
    @block = block
  end
end

class Custom < Annotations
  attr_accessor :block
  def initialize(&block)
    @block = block
  end
end

class Class
  attr_accessor :ownAnnotations
end

class Object
  def method_missing(name, *parms, &block)
    super unless name.to_s.match('ñ*ñ')
    new_annotation = ObjectSpace.const_get(name.to_s.gsub('ñ', '').to_sym).new(*parms,&block)
    $annotations.push(new_annotation)
  end
  def self.inherited(aClass)
    aClass.ownAnnotations = $annotations.clone.map do |annotation|
      annotation.owner=aClass
      annotation
    end
    $annotations = []
  end
  def self.method_added(method)
    #puts method
    added_array = $annotations.clone.map do |annotation|
      annotation.owner=method
      annotation
    end
    if self.ownAnnotations.class == Array
      self.ownAnnotations.concat(added_array)
    else
      self.ownAnnotations = added_array
    end
    $annotations = []
  end
end