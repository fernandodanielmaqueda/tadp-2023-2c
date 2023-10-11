module Serialize_Framework

  attr_accessor :label
  attr_writer :ignore

  def ignore?
    @ignore ||= false
  end

end