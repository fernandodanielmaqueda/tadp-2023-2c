class Label

  attr_reader :name

  def initialize(name)
    @name = name
  end

  def apply_to(klass_or_unbound_method)
    klass_or_unbound_method.label = @name
  end

end

module Label_Framework

  attr_accessor :label

end

[Class_Serialize_Framework, Method_Serialize_Framework].each {|serialize_framework| serialize_framework.include(Label_Framework)}