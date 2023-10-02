require_relative 'anexo'

class Document

  def initialize(parent = nil, &xml_block)
    @parent = parent

    result = self.instance_eval(&xml_block)

    if result.class != Tag and parent != nil
      parent.with_child(result)
    end

  end

  def method_missing(label, *attributes, &children)

    tag = Tag.with_label(label)

    attributes[0].each do |key, value|
      tag.with_attribute(key, value)
    end unless attributes[0] == nil

    if children != nil
      Document.new(tag, &children)
    end

    if @parent.nil?
      @root = tag
    else
      @parent.with_child(tag)
    end

  end

  def xml
    puts @root.xml
  end

  def serialize(clase)
  end

end

# class Object
#   def method_missing(name, *parms)
#     super unless name.to_s.match('✨*✨')
#     ObjectSpace.const_get(name.to_s.gsub('✨', '').to_sym).annotation(parms)
#   end
# end

class Label
  def annotation(*parms)
    puts parms
  end
end

class Ignore
  def annotation(*parms)
    puts 'nope'
  end
end

class Inline
  def annotation(&parms)
    self.instance_eval(&parms)
  end
end

class Custom

end