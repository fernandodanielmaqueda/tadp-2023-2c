class Custom

  attr_reader :xml_block

  def initialize(&xml_block)
    @xml_block = xml_block
  end
end