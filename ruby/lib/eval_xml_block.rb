class Eval_XML_Block

  def initialize(document, *xml_block_args, &xml_block)
    @document = document

    result = self.instance_exec(*xml_block_args, &xml_block)

    @document.parent.with_child(result) if result.class != Tag and @document.parent
  end

  def method_missing(label, *attributes, &children)

    tag = Tag.with_label_and_attributes(label, attributes[0])

    Document.new(tag, &children) if children

    if @document.parent
      @document.parent.with_child(tag)
    else
      @document.root = tag
    end

  end

end