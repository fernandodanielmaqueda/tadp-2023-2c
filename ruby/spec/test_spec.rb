RSpec.describe Prueba do
  let(:prueba) { Prueba.new }

  describe "#materia" do
    it("debería pasar este test") do
      expect(prueba.materia).to be :tadp
    end
  end
end

RSpec.describe "Anexo" do
  let(:tag) do
    Tag
      .with_label('alumno')
      .with_attribute('nombre', 'Mati')
      .with_attribute('legajo', '123456-7')
      .with_attribute('edad', 27)
      .with_child(
        Tag
          .with_label('telefono')
          .with_child('12345678')
      )
      .with_child(
        Tag
          .with_label('estado')
          .with_child(
            Tag
              .with_label('value')
              .with_child('regular')
          )
      )
      .with_child(Tag.with_label('no_children'))
      .xml
  end

  let(:expected) do
<<-HEREDOC.chomp
<alumno nombre="Mati" legajo="123456-7" edad=27>
	<telefono>
		"12345678"
	</telefono>
	<estado>
		<value>
			"regular"
		</value>
	</estado>
	<no_children/>
</alumno>
HEREDOC
  end

  describe "Ejemplo de uso" do
    it("El XML generado es correcto") do
      expect(tag).to eq expected
    end
  end
end

describe Alumno do
  let(:estado) { Estado.new 1, 1, true }
  let(:alumno) { Alumno.new "Fede", 1, 11, estado }
  describe  '#alumno' do
    it 'deberia llamarse Fede y tener un legajo igual a 1' do
      expect(alumno.nombre).to eq "Fede"
      expect(alumno.legajo).to be 1
    end
  end
end

# Punto 1
# @document1 = Document.new do
#   alumno nombre: "Matias", legajo: "123456-7" do
#     telefono { "1234567890" }
#     estado es_regular: true do
#       finales_rendidos { 3 }
#       materias_aprobadas { 5 }
#     end
#   end
# end
# #puts "@document1: #{@document1.inspect}"
# puts @document1.xml

# @document2 = Document.new do
#   alumno nombre: "Mati", legajo: "123456-7", edad: 27 do
#   telefono { "12345678" }
#   estado do
#     value { "regular" }
#   end
#   no_children
#   end
# end
#puts @document2.xml

#estado = Estado.new(finales_rendidos = 3, materias_aprobadas = 5, es_regular = true)
#fede = Alumno.new(nombre = 'Fede', legajo = 1, telefono = 11, estado = estado)

# @document0 = Document.new do
#   alumno nombre: fede.nombre, legajo: fede.legajo do
#     telefono { fede.telefono }
#     estado es_regular: fede.estado.es_regular do
#       finales_rendidos { fede.estado.finales_rendidos }
#       materias_aprobadas { fede.estado.materias_aprobadas }
#     end
#   end
# end
#puts @document0.xml

=begin
# Deberia ser lo mismo que
#
@document0 = Document.new {
  self.alumno(nombre: @fede.nombre, legajo: @fede.legajo) {
    self.telefono { @fede.telefono }
    self.estado(es_regular: true) {
      finales_rendidos { 3 }
      materias_aprobadas { 5 }
    }
  }
}
=end

# Punto 2
#
# unEstado = Estado.new(3, 5, true)
# unAlumno = Alumno.new("Matias","123456-8", "1234567890", unEstado)
#
# documento_manual1 = Document.new do
#   alumno nombre: unAlumno.nombre, legajo: unAlumno.legajo do
#     estado finales_rendidos: unAlumno.estado.finales_rendidos,
#            materias_aprobadas: unAlumno.estado.materias_aprobadas,
#            es_regular: unAlumno.estado.es_regular
#   end
# end
#
# documento_automatico1 = Document.serialize(unAlumno)
#
# puts documento_automatico1.xml
# puts "Coinciden manual y automatico: #{documento_manual1.xml == documento_automatico1.xml}" # Esto debe cumplirse
#

#
# unDocente = Docente.new("Nico", [Curso.new("tadp", 40), Curso.new("pdep", 35)])
#
# documento_automatico2 = Document.serialize(unDocente)
# puts documento_automatico2.xml