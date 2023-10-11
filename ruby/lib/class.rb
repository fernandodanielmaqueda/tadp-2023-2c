class Class

  include Serialize_Framework

  attr_writer :annotations_buffer, :method_values, :class_associations, :method_associations

  def annotations_buffer
    @annotations_buffer ||= Array.new
  end

  def unbound_instance_methods
    @unbound_methods ||= Hash.new
  end

  def class_associations
    @class_associations ||= Array.new
  end

  def instance_methods_associations
    @method_associations ||= Hash.new
  end

  def apply_class_annotations
    self.class_associations.each do |annotation|
      annotation.apply_to_class(self)
    end
  end

  def apply_instance_methods_annotations(method_symbol)
    # puts "def apply_method_annotations(method_symbol)"
    # puts self.inspect
    # puts method_symbol.inspect
    # puts self.method_associations.inspect
    # puts (self.method_associations[method_symbol] || Array.new).inspect
    #(self.method_associations[method_symbol] || Array.new).each do |annotation|
      #puts annotation.inspect
      #puts self.method(method_symbol).inspect
      #annotation.apply_to_method(self.unbound_methods[method_symbol])
      #end
  end

end