require_relative 'anexo'
require_relative 'annotations'

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
ñLabelñ(4)
ñLabelñ(4)
ñLabelñ(4)
ñInlineñ
ñLabelñ(4)
class A

end


class Document

  attr_accessor :root

  def self.with_root(tag)
    self.new.root=(tag)
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
    if @root.nil?
      raise "El documento no tiene un tag raiz"
    end

    @root.xml
  end


  def self.serialize(parent = nil, object)
    label = nombre_en_minusculas_de_la_clase_de(object)


    remaining_attributes = atributos_con_getter_de(object)

    label_attributes, remaining_attributes = separar_labels_de_remaining_attributes(remaining_attributes, object)
    array_attributes, remaining_attributes = separar_arrays_de_remaining_attributes(remaining_attributes, object)


    label = self.applyAnnotationsOnLabel(object, label)
    label_attributes = hash_clave_valor_de(label_attributes, object)
    label_attributes = self.applyAnnotationsOnLabelAttributes(label_attributes, object)


    tag = Tag.with_label_and_attributes(label, label_attributes)


    self.serializar_arrays(array_attributes, tag, object)
    self.serializar_restantes(remaining_attributes, tag, object)

    if parent.nil?
      self.with_root(tag)
    else
      parent.with_child(tag)
    end

  end


  private
  def self.applyAnnotationsOnLabelAttributes(label_attributes, object)
    annotations_list_attributes = []
    label_attributes.each do |key, value|
      annotation = object.class.ownAnnotations.find { |ann| ann.owner.eql?(key) }
      annotations_list_attributes << annotation if annotation
    end
    label_attributes = self.match_and_modify_attributes(annotations_list_attributes, label_attributes)
    label_attributes
  end
  def self.applyAnnotationsOnLabel(object, label)
    objectAnnotations = self.onlySelectedObjectAnnotations(object)
    label = self.doForEveryAnnotationAnAction(objectAnnotations, label) unless objectAnnotations.size == 0
    label
  end
  def self.match_and_modify_attributes(annotations, label_attributes)
    newHash = []
    temporalList = label_attributes.to_a
    annotations.each do |annotation|
      clave = annotation.owner
      temporalList.each_with_index do |par, index|
        key,value = par
        if(key == clave)
          temporalList.delete_at(index)
          newHashElement = annotation.doAnnotationActionOnAttribute(key, value)
          newHash << newHashElement unless newHashElement.nil?
      end
    end
    end
    newHash += temporalList
    newHash.to_h
    end
  def self.onlySelectedObjectAnnotations(object)
    object.class.ownAnnotations.filter do |annotation|
      annotation.ownerIsClassOf?(object)
    end
  end
  def self.doForEveryAnnotationAnAction(annotations, label)
    label = annotations.inject(label) { |label,annotation |annotation.doAnnotationAction(label)  }
    return label
  end

  def evaluar(&xml_block)
    self.singleton_class.include(Eval_XML_Block)
    result = self.instance_eval(&xml_block)
    self.singleton_class.undef_method(:method_missing)
    result
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

ñLabelñ("tester")
class TestA
  ñIgnoreñ
  attr_reader :testing, :what
  ñLabelñ('boenas')
  attr_reader :different
  ñLabelñ(5)
  attr_reader :number
  #
  def initialize
    @testing=10
    @what='asdfs'
    @different='hola'
    @number='hola'
  end
  # def tested
  #
  # end
end

ñIgnoreñ
class TestB
end
Document.serialize(TestA.new).xml
Document.serialize(TestB.new).xml

ñLabelñ("TEST1")
class Estado
  ñLabelñ("TEST2")
  attr_reader :es_regular
  ñIgnoreñ
  attr_reader :materias_aprobadas
  attr_reader :finales_rendidos

  def initialize(finales_rendidos, materias_aprobadas, es_regular)
    @finales_rendidos = finales_rendidos
    @materias_aprobadas = materias_aprobadas
    @es_regular = es_regular
  end
end

ñLabelñ("TEST4")
class TestC
  ñLabelñ("TEST5")
  attr_reader :nombre
  ñIgnoreñ
  attr_reader :legajo
  ñLabelñ("Hola")
  attr_reader :estado
  def initialize(nombre, legajo, telefono, estado)
    @nombre = nombre
    @legajo = legajo
    @telefono = telefono
    @estado = estado
  end

end
unTest =TestC.new("Jorge",4325662,3242652,Estado.new(3,6,true))
Document.serialize(unTest)