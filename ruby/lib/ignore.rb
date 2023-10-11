class Ignore

  def apply_to_class(a_class)
    a_class.ignore = true
  end

  def apply_to_method(unbound_method)
    unbound_method.ignore = true
  end
end