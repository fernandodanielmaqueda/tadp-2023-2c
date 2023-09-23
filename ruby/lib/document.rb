require_relative '../lib/base'

class Document
  attr_accessor :block

  def initialize(&block)
    @block = block
  end
  def xml
    tag = Tag.new block
    tag.xml
  end
end

class Proc
  def armar_xml
    tag = Tag.new self.source_location
    tag.xml
  end
end

=begin
documento.new {datos}  -> initialize(dato1....datoN  - contenido1..... - contenidoN)

3 tipos de bloques
-los que no tienen nada
-los que son raiz
-los que son hijos

Cada bloque tiene un nombre obligatorio y una lista opcional de clave-valores

=end