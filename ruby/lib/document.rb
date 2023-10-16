require_relative 'tag'

module Eval_XML_Block
  def method_missing(label, *attributes, &children)

    tag = Tag.with_label_and_attributes(label, attributes[0])

    Document.new(tag, &children) if children

    if @parent
      @parent.with_child(tag)
    else
      @root = tag
    end

  end
end

class Document

  attr_accessor :root

  def self.with_root(tag)
    self.new.root = tag
  end

  def initialize(parent = nil, &xml_block)
    @parent = parent

    if xml_block
      result = self.evaluar(&xml_block)

      parent.with_child(result) if result.class != Tag and parent
    end
  end

  def xml
    raise "El documento no tiene un tag raiz" unless @root

    @root.xml
  end

  def self.serialize(parent = nil, object)

    if not object.class.ignore?
      if true # Considerar custom o no
        tag = Tag.with_label(self.nombre_tag(object))
        attributes = self.atributos(object)

        self.serializar_attributes(tag, attributes, object)

        if parent
          parent.with_child(tag)
        else
          self.with_root(tag)
        end

      else
        #  Custom
      end

    else
      self.with_root(Tag.new)
    end

  end

  private

  def evaluar(&xml_block)
    self.singleton_class.include(Eval_XML_Block)
    result = self.instance_eval(&xml_block)
    self.singleton_class.undef_method(:method_missing)
    result
  end

  def self.nombre_tag(object)
    self.etiqueta_de_la_clase_de(object) || self.nombre_en_minusculas_de_la_clase_de(object)
  end

  def self.nombre_atributo(object, getter)
    object.class.unbound_instance_methods[getter].label || getter
  end

  def self.etiqueta_de_la_clase_de(object)
    object.class.label
  end

  def self.nombre_en_minusculas_de_la_clase_de(object)
    object.class.to_s.downcase
  end

  def self.atributos(object)
    self.atributos_sin_ignore(self.atributos_con_getter_de(object), object)
  end

  def self.atributos_sin_ignore(attributes, object)
    attributes.filter do |getter|
      not object.class.unbound_instance_methods[getter].ignore?
    end
  end

  def self.atributos_con_getter_de(object)
    self.simbolos_de_metodos_con_getter(self.simbolos_de_atributos_convertidos_a_simbolos_de_metodos_de(object), object)
  end

  def self.simbolos_de_atributos_convertidos_a_simbolos_de_metodos_de(object)
    object.instance_variables.map do |instance_variable_symbol|
      instance_variable_symbol.to_s.sub("@", "").to_sym
    end
  end

  def self.simbolos_de_metodos_con_getter(method_symbols, object)
    method_symbols.filter do |method_symbol|
      object.methods.any? do |object_method|
        (method_symbol == object_method) and (object.method(object_method).arity == 0)
      end
    end
  end

  def self.serializar_attributes(tag, attributes, object)

    attributes.each do |getter|
      value = object.send(getter)
      inline_proc = object.class.unbound_instance_methods[getter].inline_proc

      if inline_proc
        self.serializar_inline(inline_proc, value, getter, tag, object)
      elsif self.representable_como_atributo_de_un_tag?(value)
        self.serializar_atributo(self.nombre_atributo(object, getter), value, tag)
      else
        self.serializar_tag(value, tag)
      end
    end

  end

  def self.representable_como_atributo_de_un_tag?(value)
    [String, TrueClass, FalseClass, Numeric, NilClass].any? {|class_element| value.is_a? class_element}
  end

  def self.serializar_inline(inline_proc, value, getter, tag, object)
    key = nombre_atributo(object, getter)
    value = inline_proc.call(value)
    raise "Luego de aplicar el bloque de la anotacion Inline sobre el campo del atributo #{key}, el resultado no tiene un tipo representable como un atributo del tag #{tag.label}" unless representable_como_atributo_de_un_tag?(value)
    self.serializar_atributo(key, value, tag)
  end

  def self.serializar_atributo(key, value, tag)
    tag.with_attribute(key, value)
  end

  def self.serializar_tag(value, tag)
    if value.is_a? Array
      self.serializar_tag_array(value, tag)
    else
      self.serializar_tag_otro_objeto(value, tag)
    end
  end

  def self.serializar_tag_array(array, tag)
    array.each do |object|
      self.serialize(tag, object)
    end
  end

  def self.serializar_tag_otro_objeto(object, tag)
    self.serialize(tag, object)
  end

end