class Label

  attr_reader :name

  def initialize(name)
    @name = name
  end

  def apply_to_class(a_class)
    a_class.label = @name
  end

  def apply_to_method(unbound_method)
    unbound_method.label = @name
  end
end