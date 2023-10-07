class Inline #< Annotation
  attr_accessor :block
  def initialize(&block)
    @block = block
  end
end