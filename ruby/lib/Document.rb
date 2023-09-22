require_relative '../lib/base'

class Document

  def initialize(*parametros)

  end
  def xml
    Tag.with_label(tags.sacarDe(tags))
  end
end




/
documento.new {datos}  -> initialize(dato1....datoN  - contenido1..... - contenidoN)


3 tipos de bloques
-los que no tienen nada
-los que son raiz
-los que son hijos

*/

/
Cada bloque tiene un nombre obligatorio y una lista opcional de clave-valores

*/