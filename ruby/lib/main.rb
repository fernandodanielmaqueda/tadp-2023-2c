require_relative 'document'

class Object
  def method_missing(name, *parms)
    super(name, *parms)
    #:asd.call(name, parms)
    #super unless name.to_s.match('✨*✨')
    #ObjectSpace.const_get(name.to_s.gsub('✨', '').to_sym).annotation(parms)
  end
end

class Label
  def annotation(*parms)
    puts parms
  end
end

class Ignore
  def annotation(*parms)
    puts 'nope'
  end
end

class Inline
  def annotation(&parms)
    self.instance_eval(&parms)
  end
end

class Custom

end