class Class

  include Class_Serialize_Framework

  attr_writer :annotations_buffer, :class_associations, :instance_methods_associations, :unbound_instance_methods

  def annotations_buffer
    @annotations_buffer ||= Array.new
  end

  def class_associations
    @class_associations ||= Array.new
  end

  def instance_methods_associations
    @instance_methods_associations ||= Hash.new
  end

  def unbound_instance_methods
    @unbound_instance_methods ||= Hash.new
  end

  def apply_class_annotations
    self.class_associations.each do |annotation|
      annotation.apply_to(self)
    end
  end

  def apply_instance_method_annotations(method_symbol)
    (self.instance_methods_associations[method_symbol] || Array.new).each do |annotation|
        annotation.apply_to(self.unbound_instance_methods[method_symbol])
      end
  end

end