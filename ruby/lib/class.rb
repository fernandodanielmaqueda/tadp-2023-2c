class Class

  def class_associations=(array)
    @class_associations = array
  end

  private

  def class_associations
    @class_associations ||= Array.new
  end

  def method_associations
    @method_associations ||= Hash.new
  end
  def method_associations=(hash)
    @method_associations = hash
  end

end