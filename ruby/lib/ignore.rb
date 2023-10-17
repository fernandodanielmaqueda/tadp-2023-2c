class Ignore

  def apply_to(klass_or_unbound_method)
    klass_or_unbound_method.ignore = true
  end

end

module Ignore_Framework

  attr_writer :ignore

  def ignore?
    @ignore ||= false
  end

end

[Class_Serialize_Framework, Method_Serialize_Framework].each {|serialize_framework| serialize_framework.include(Ignore_Framework)}