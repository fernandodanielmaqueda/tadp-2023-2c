class Tag
  attr_reader :label, :attributes, :children

  def self.with_label(label)
    #puts "self.with_label(\"#{label}\")"
    new(label)
  end

  def initialize(label)
    @label = label
    @attributes = {}
    @children = []
  end

  # Todos los with_x son setters.

  def with_label(label)
    #puts "with_label(\"#{label}\")"
    @label = label
    self
  end

  def with_attribute(label, value)
    #puts "with_attribute(\"#{label}\", #{value.inspect})"
    @attributes[label] = value
    self
  end

  def with_child(child)
    #puts "with_child(#{child.inspect}) --- #{self.inspect}"
    @children << child
    self
  end

  # Retorna recursivamente el xml generado.

  def xml(level=0)
    if children.empty?
      "#{"\t" * level}<#{label}#{xml_attributes}/>"
    else
      "#{"\t" * level}<#{label}#{xml_attributes}>\n#{xml_children(level + 1)}\n#{"\t" * level}</#{label}>"
    end
  end

  private

  def xml_children(level)
    self.children.map do |child|
      if child.is_a? Tag
        child.xml(level)
      else
        xml_value(child, level)
      end
    end.join("\n")
  end

  def xml_attributes
    self.attributes.map do |name, value|
      "#{name}=#{xml_value(value, 0)}"
    end.xml_join(' ')
  end

  def xml_value(value, level)
    "\t" * level + if value.is_a? String
                     "\"#{value}\""
                   else
                     value.to_s
                   end
  end
end

# La clase Array es una modificación de la clase Array general para incluirle el método xml_join.

class Array
  def xml_join(separator)
    self.join(separator).instance_eval do
      if !empty?
        "#{separator}#{self}"
      else
        self
      end
    end
  end
end

