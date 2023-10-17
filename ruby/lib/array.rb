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

end