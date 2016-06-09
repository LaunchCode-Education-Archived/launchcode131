class Box < Liquid::Tag
  Syntax = /^\s*([^\s]+)(\s+(\d+)\s+(\d+)\s*)?/

  def initialize(tagName, markup, tokens)
    super

    if markup =~ Syntax then
      @id = $1

      if $2.nil? then
          @width = 640
          @height = 360
      else
          @width = $2.to_i
          @height = $3.to_i
      end
    else
      raise "No Box ID provided in the \"box\" tag"
    end
  end

  def render(context)
    "<iframe width=\"#{@width}\" height=\"#{@height}\" src=\"https://wustl.box.com/s/#{@id}\" frameborder=\"0\" allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe>"
    #"<iframe width=\"#{@width}\" height=\"#{@height}\" src=\"https://wustl.box.com/s/{@id}?color=white&theme=light\"></iframe>"
  end

  Liquid::Template.register_tag "box", self
end