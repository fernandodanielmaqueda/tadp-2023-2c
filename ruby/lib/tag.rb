require_relative 'array'

class Tag
  attr_reader :label, :attributes, :children

  def self.with_label(label)
    new(label)
  end

  def self.with_label_and_attributes(label, attributes)
    tag = self.with_label(label)
    tag.with_attributes(attributes)
  end

  def initialize(label)
    @label = label
    @attributes = {}
    @children = []
  end

  # Todos los with_x son setters.

  def with_label(label)
    @label = label
    self
  end

  def with_attribute(label, value)
    raise "La etiqueta #{self.label} ha quedado con dos o mas atributos con el mismo nombre: #{label}" unless @attributes[label].nil?
    @attributes[label] = value
    self
  end

  def with_attributes(attributes)
    attributes.each do |key, value|
      self.with_attribute(key, value)
    end unless attributes == nil

    self
  end

  def with_child(child)
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