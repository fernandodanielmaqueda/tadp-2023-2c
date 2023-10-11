require_relative 'anexo'

module Eval_XML_Block
  def method_missing(label, *attributes, &children)

    tag = Tag.with_label_and_attributes(label, attributes[0])

    if children != nil
      Document.new(tag, &children)
    end

    if @parent.nil?
      @root = tag
    else
      @parent.with_child(tag)
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

    if xml_block != nil
      result = self.evaluar(&xml_block)

      if result.class != Tag and parent != nil
        parent.with_child(result)
      end
    end
  end

  def xml
    raise "El documento no tiene un tag raiz" if @root.nil?

    @root.xml
  end


  def self.serialize(parent = nil, object)

    #object.class.apply_class_annotations

    unless object.class.ignore?
      if true # Considerar custom o no
        label = etiqueta_de_la_clase_de(object) || nombre_en_minusculas_de_la_clase_de(object)

        remaining_attributes = atributos_con_getter_de(object)

        #remaining_attributes.each do |getter|
          #puts (object.class.instance_method(:testing) == object.class.instance_method(:testing)).inspect
          # puts getter.inspect
          # puts object.class.method_associations[getter].inspect
          # puts object.class.instance_method(getter).inspect
          # puts "Hola"
          # object.class.instance_method(getter).ignore = true
          # puts object.class.instance_method(getter).ignore?.inspect
          # puts "Chau"
        #(object.class.method_associations[getter] || Array.new).each do |annotation|
              #puts annotation.inspect
        #annotation.apply_to_method(object.class.instance_method(getter))
        # end
          #puts object.class.instance_method(getter).ignore?.inspect
          #end

    #remaining_attributes.filter! do |getter|
          # puts "remaining_attributes.filter! do |getter|"
          # puts object.inspect
          # puts object.method(getter).inspect
          # puts (object.method(getter).ignore?).inspect
          #not object.class.instance_method(getter).ignore?
          #end

        # Lógica de inline (separar/particionar los inline_attributes de remaining_attributes que correspondan)
        # inline_attributes, remaining_attributes = separar_inlines_de_remaining_attributes(remaining_attributes, object)

        label_attributes, remaining_attributes = separar_labels_de_remaining_attributes(remaining_attributes, object)
        array_attributes, remaining_attributes = separar_arrays_de_remaining_attributes(remaining_attributes, object)

        label_attributes = hash_clave_valor_de(label_attributes, object)

        # Verificar aquí que en label_attributes no hayan quedado dos o más atributos con el mismo nombre:
        #raise "La etiqueta #{label} ha quedado con dos atributos con el mismo nombre: #{}"

        tag = Tag.with_label_and_attributes(label, label_attributes)

        self.serializar_arrays(array_attributes, tag, object)
        self.serializar_restantes(remaining_attributes, tag, object)

        if parent.nil?
          self.with_root(tag)
        else
          parent.with_child(tag)
        end
      else
        #  Custom
      end
    end

  end

  private

  def evaluar(&xml_block)
    self.singleton_class.include(Eval_XML_Block)
    result = self.instance_eval(&xml_block)
    self.singleton_class.undef_method(:method_missing)
    result
  end

  def self.etiqueta_de_la_clase_de(object)
    object.class.label
  end

  def self.nombre_en_minusculas_de_la_clase_de(object)
    object.class.to_s.downcase
  end

  def self.atributos_con_getter_de(object)
    con_getter(simbolos_de_atributos_convertidos_a_simbolos_de_metodos_de(object), object)
  end

  def self.simbolos_de_atributos_convertidos_a_simbolos_de_metodos_de(object)
    object.instance_variables.map do |instance_variable_symbol|
      instance_variable_symbol.to_s.sub("@", "").to_sym
    end
  end

  def self.con_getter(method_symbols, object)
    method_symbols.filter do |method_symbol|
      object.methods.any? do |object_method|
        (method_symbol == object_method) and (object.method(object_method).arity == 0)
      end
    end
  end

  def self.separar_labels_de_remaining_attributes(remaining_attributes, object)
    remaining_attributes.partition do |getter|
      [String, TrueClass, FalseClass, Numeric, NilClass].any? {|class_element| object.send(getter).is_a? class_element}
    end
  end

  def self.separar_arrays_de_remaining_attributes(remaining_attributes, object)
    remaining_attributes.partition do |getter|
      object.send(getter).is_a? Array
    end
  end

  def self.hash_clave_valor_de(label_attributes, object)
    Hash[ label_attributes.map { |getter| [getter, object.send(getter)] } ]
  end

  def self.serializar_arrays(array_attributes, tag, object)
    array_attributes.each do |symbol|
      object.send(symbol).each do |element|
        self.serialize(tag, element)
      end
    end unless array_attributes == nil
  end

  def self.serializar_restantes(remaining_attributes, tag, object)
    remaining_attributes.each do |symbol|
      self.serialize(tag, object.send(symbol))
    end unless remaining_attributes == nil
  end

end