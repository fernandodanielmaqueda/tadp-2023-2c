require_relative '../lib/base'

class Object
  def method_missing(name, *parms)
    super unless name.to_s.match('✨*✨')
    ObjectSpace.const_get(name.to_s.gsub('✨', '').to_sym).annotation(parms)
  end
end

class Document
  attr_accessor :tags

  def initialize(&block)
    @tags = Array.new
    if(block.eql?(nil))
      @bloqueRecibido = Tag.with_label ''
    else
      value = self.instance_exec(&block)
      if tags[0].class != Tag and value.class != Array
        tags[0] = value
      end
    end
  end

  def xml
    puts tags[0].xml
  end

  def method_missing(name, *parms, &block)
    #super(name,parms,&block)
    tag = Tag.with_label(name)
    parms[0].each do |key, value|
      tag.with_attribute key, value
    end unless parms[0] == nil
    if block != nil
      childDocument = Document.new(&block)
      childDocument.tags.each do |tempTag|
        tag.with_child(tempTag)
      end
    end
    @tags << tag
  end

  def serialize(clase)
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