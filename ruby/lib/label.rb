class Label #< Annotation
  attr_accessor :name
  def initialize(name)
    @name = name
  end

  # def doAnnotationAction
  #   super()
  #   @name
  # end
end