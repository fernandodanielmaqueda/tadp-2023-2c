# La clase Array es una modificación de la clase Array general para incluirle el método xml_join.

class Array

  def xml_join(separator)
    self.join(separator).instance_eval do
      if !empty?
        "#{separator}#{self}"
      else
        self
      end
    end
  end

  # Agregado propio
  def duplicates
    map = Hash.new
    duplicates = Array.new

    self.each do |element|
      map[element] = (map[element] || 0 ) + 1

      if map[element] == 2
        duplicates << element
      end
    end

    duplicates
  end

end