require_relative 'document'

ñLabelñ("tester")
class Test
  ñIgnoreñ
  attr_accessor :testing, :what
  ñLabelñ('boenas')
  attr_accessor :different
  ñLabelñ(5)
  attr_accessor :number

  def initialize
    @testing=10
    @what='asdfs'
    @different='hola'
    @number='hola'
  end

  def tested

  end
end

$associations.each do |key, value|
  #puts "#{ObjectSpace._id2ref(key).inspect} => #{value}"
end