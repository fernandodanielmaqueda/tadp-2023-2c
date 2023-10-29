Emoticon = '✨'
$annotations_buffer = Array.new

require_relative 'serialize_framework'
require_relative 'class'
require_relative 'object'
require_relative 'unboundmethod'

require_relative 'document'
require_relative 'label'
require_relative 'ignore'
require_relative 'inline'
require_relative 'custom'
require_relative 'extra'

✨Extra✨(:global) { (@fuerza + @velocidad) / 2}
class Guerrero
  attr_accessor :salud

  def initialize(fuerza, velocidad, salud)
    @fuerza = fuerza
    @velocidad = velocidad
    @salud = salud
  end
end

@atila = Guerrero.new(50, 70, 100)

puts Document.serialize(@atila).xml