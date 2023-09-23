require_relative '../lib/base'

class Object
  def method_missing(name, *parms)
    super unless name.to_s.match('✨*✨')
    ObjectSpace.const_get(name.to_s.gsub('✨', '').to_sym).annotation(parms)
  end
end

class Document
  attr_accessor :block

  def initialize(&block)
    xml = self.instance_exec(&block)
    puts xml.xml
  end

  def xml

  end

  def method_missing(name, *parms, &block)
    tag = Tag.with_label(name.to_s)
    parms[0].each do |key, value|
      tag.with_attribute key.to_s, value.to_s
    end unless parms[0] == nil
    tag.with_child(self.instance_eval(&block))
    puts tag.xml
    tag
  end
end

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
=begin
documento.new {datos}  -> initialize(dato1....datoN  - contenido1..... - contenidoN)

3 tipos de bloques
-los que no tienen nada
-los que son raiz
-los que son hijos

Cada bloque tiene un nombre obligatorio y una lista opcional de clave-valores

=end