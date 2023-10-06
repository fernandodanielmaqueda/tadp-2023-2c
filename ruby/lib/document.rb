require_relative 'anexo'
require_relative 'annotations'

module Eval_XML_Block
  def method_missing(label, *attributes, &children)

    tag = Tag.with_label(label)

    attributes[0].each do |key, value|
      tag.with_attribute(key, value)
    end unless attributes[0] == nil

    if children != nil
      Document.new(tag, nil, &children)
    end

    if @parent.nil?
      @root = tag
    else
      @parent.with_child(tag)
    end

  end
end

class Document

  self.binding
  def initialize(parent = nil, root = nil, &xml_block)
    @parent = parent
    @root = root

    self.singleton_class.include(Eval_XML_Block)
      result = self.instance_eval(&xml_block)
    self.singleton_class.undef_method(:method_missing)

    if result.class != Tag and parent != nil
      parent.with_child(result)
    end

  end

  # def method_missing(label, *attributes, &children)
  #
  #   tag = Tag.with_label(label)
  #
  #   attributes[0].each do |key, value|
  #     tag.with_attribute(key, value)
  #   end unless attributes[0] == nil
  #
  #   if children != nil
  #     Document.new(tag, nil, &children)
  #   end
  #
  #   if @parent.nil?
  #     @root = tag
  #   else
  #     @parent.with_child(tag)
  #   end
  #
  # end

  def xml
    @root.xml
  end

  def self.serialize(parent = nil, object)

    possible_names = object.class.ownAnnotations.filter do |annotation|
      annotation.owner == object.class && annotation.class == Label
    end.map do |annotation| annotation.param end
    if possible_names.size != 0
      label = possible_names.last
    else
      label = object.class.to_s.downcase
    end


    remaining_attributes = object.instance_variables.map do |instance_variable_symbol|
      instance_variable_symbol.to_s.sub("@", "").to_sym
    end.filter do |getter|
      object.methods.any? do |method_symbol|
        (getter == method_symbol) and (object.method(method_symbol).arity == 0)
      end
    end

    label_attributes, remaining_attributes = remaining_attributes.partition do |getter|
      [String, TrueClass, FalseClass, Numeric, NilClass].any? {|class_element| object.send(getter).is_a? class_element}
    end

    array_attributes, remaining_attributes = remaining_attributes.partition do |getter|
      object.send(getter).is_a? Array
    end

    label_attributes = Hash[ label_attributes.map { |getter| [getter, object.send(getter)] } ]

    # A partir de acá se puede abstraer con method_missing
    tag = Tag.with_label(label)

    Array[label_attributes][0].each do |key, value|
      tag.with_attribute(key, value)
    end unless Array[label_attributes][0] == nil

    array_attributes.each do |symbol|
      object.send(symbol).each do |element|
        self.serialize(tag, element)
      end
    end unless array_attributes == nil

    remaining_attributes.each do |symbol|
      self.serialize(tag, object.send(symbol))
    end unless remaining_attributes == nil

    if parent.nil?
      self.with_root(tag)
    else
      parent.with_child(tag)
    end

  end

  def self.with_root(tag)
    self.new(nil, tag) {}
  end

end