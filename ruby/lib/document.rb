require_relative 'tag'
require_relative 'eval_xml_block'

class Document

  attr_accessor :root
  attr_reader :parent

  def self.with_root(tag)
    self.new.root = tag
  end

  def initialize(parent = nil, *xml_block_args, &xml_block)
    @parent = parent

    Eval_XML_Block.new(self, *xml_block_args, &xml_block) if xml_block
  end

  def xml
    raise "El documento no tiene un tag raiz" unless @root

    @root.xml
  end

  def self.serialize(parent = nil, tag_label = nil, object)

    if not object.class.ignore?
      tag = Tag.with_label(tag_label || self.nombre_tag(object))

      if not object.class.custom_proc
        attributes = self.atributos_de(object)
        self.serializar_campos(tag, attributes, object)
      else
        self.new(tag, object, &object.class.custom_proc)
      end

      if parent
        parent.with_child(tag)
      else
        self.with_root(tag)
      end

    else
      self.with_root(Tag.new)
    end

  end

  private

  def self.nombre_tag(object)
    self.etiqueta_de_la_clase_de(object) || self.nombre_en_minusculas_de_la_clase_de(object)
  end

  def self.nombre_atributo(object, getter)
    self.label_atributo(object, getter) || getter
  end

  def self.label_atributo(object, getter)
    object.class.unbound_instance_methods[getter].label
  end

  def self.etiqueta_de_la_clase_de(object)
    object.class.label
  end

  def self.nombre_en_minusculas_de_la_clase_de(object)
    object.class.to_s.downcase
  end

  def self.atributos_de(object)
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

  def self.serializar_campos(tag, attributes, object)

    attributes.each do |getter|
      value = object.send(getter)
      inline_proc = object.class.unbound_instance_methods[getter].inline_proc

      if inline_proc
        self.serializar_inline(inline_proc, self.nombre_atributo(object, getter), value, tag)
      elsif self.representable_como_atributo_de_un_tag?(value)
        self.serializar_atributo(tag, self.nombre_atributo(object, getter), value)
      else
        self.serializar_tag(tag, self.label_atributo(object, getter), value)
      end
    end

  end

  def self.representable_como_atributo_de_un_tag?(value)
    [String, TrueClass, FalseClass, Numeric, NilClass].any? {|class_element| value.is_a? class_element}
  end

  def self.serializar_inline(inline_proc, label, value, tag)
    value = inline_proc.call(value)
    raise "Luego de aplicar el bloque de la anotacion Inline sobre el campo del atributo #{label}, el resultado no tiene un tipo representable como un atributo del tag #{tag.label}" unless representable_como_atributo_de_un_tag?(value)
    self.serializar_atributo(tag, label, value)
  end

  def self.serializar_atributo(tag, label, value)
    tag.with_attribute(label, value)
  end

  def self.serializar_tag(tag, label, value)
    if value.is_a? Array
      self.serializar_tag_array(tag, label, value)
    else
      self.serializar_tag_otro_objeto(tag, label, value)
    end
  end

  def self.serializar_tag_array(tag, label, array)
    array.each do |object|
      self.serialize(tag, label, object)
    end
  end

  def self.serializar_tag_otro_objeto(tag, label, object)
    self.serialize(tag, label, object)
  end

end