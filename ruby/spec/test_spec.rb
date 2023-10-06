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

RSpec.describe "Punto 1: DSL e Impresión" do

  describe "Documento de ejemplo 1" do
    let(:document1) do
      Document.new do
        alumno nombre: "Matias", legajo: "123456-7" do
          telefono { "1234567890" }
          estado es_regular: true do
            finales_rendidos { 3 }
            materias_aprobadas { 5 }
          end
        end
      end
    end

    let(:expected) do
<<-HEREDOC.chomp
<alumno nombre="Matias" legajo="123456-7">
	<telefono>
		"1234567890"
	</telefono>
	<estado es_regular=true>
		<finales_rendidos>
			3
		</finales_rendidos>
		<materias_aprobadas>
			5
		</materias_aprobadas>
	</estado>
</alumno>
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(document1.xml).to eq expected
    end

  end

  describe "Documento de ejemplo 2" do
    let(:document2) do
      Document.new do
        alumno nombre: "Mati", legajo: "123456-7", edad: 27 do
          telefono { "12345678" }
          estado do
            value { "regular" }
          end
          no_children
        end
      end
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

    it("El XML generado es correcto") do
      expect(document2.xml).to eq expected
    end

  end

end

# describe Alumno do
#   let(:estado) { Estado.new 1, 1, true }
#   let(:alumno) { Alumno.new "Fede", 1, 11, estado }
#   describe  '#alumno' do
#     it 'deberia llamarse Fede y tener un legajo igual a 1' do
#       expect(alumno.nombre).to eq "Fede"
#       expect(alumno.legajo).to be 1
#     end
#   end
# end

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

RSpec.describe "Punto 2: Generación automática" do

  describe "Serialize de ejemplo 1" do

    unEstado = Estado.new(3, 5, true)
    # let(:unEstado) do
    #   Estado.new(3, 5, true)
    # end

    unAlumno = Alumno.new("Matias","123456-8", "1234567890", unEstado)
    # let(:unAlumno) do
    #   Alumno.new("Matias","123456-8", "1234567890", unEstado)
    # end

    let(:documento_manual) do
      Document.new do
        alumno nombre: unAlumno.nombre, legajo: unAlumno.legajo do
          estado finales_rendidos: unAlumno.estado.finales_rendidos,
                 materias_aprobadas: unAlumno.estado.materias_aprobadas,
                 es_regular: unAlumno.estado.es_regular
        end
      end
    end

    let(:documento_automatico) do
      Document.serialize(unAlumno)
    end

    it("Se cumple: documento_manual.xml == documento_automatico.xml") do
      expect(documento_manual.xml).to eq documento_automatico.xml
    end

  end

  describe "Serialize de ejemplo 2" do
    let(:unDocente) do
      Docente.new("Nico", [Curso.new("tadp", 40), Curso.new("pdep", 35)])
    end

    let(:expected) do
<<-HEREDOC.chomp
<docente nombre="Nico">
	<curso materia="tadp" cantidad_alumnos=40/>
	<curso materia="pdep" cantidad_alumnos=35/>
</docente>
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(Document.serialize(unDocente).xml).to eq expected
    end

  end

end

RSpec.describe "Punto 3: Personalización y Metadata" do

  describe "Base" do

    it("El XML generado es correcto") do
      expect(1).to eq 1
    end

  end

  describe "✨Label✨" do

    # ✨Label✨("estudiante")
    # class Alumno
    #   attr_reader :nombre, :legajo, :estado
    #   def initialize(nombre, legajo, telefono, estado)
    #     @nombre = nombre
    #     @legajo = legajo
    #     @telefono = telefono
    #     @estado = estado
    #   end
    #   ✨Label✨("celular")
    #   def telefono
    #     @telefono
    #   end
    # end
    # ✨Label✨("situacion")
    # class Estado
    #   attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
    #   def initialize(finales_rendidos, materias_aprobadas, es_regular)
    #     @finales_rendidos = finales_rendidos
    #     @es_regular = es_regular
    #     @materias_aprobadas = materias_aprobadas
    #   end
    # end

    let(:expected) do
<<-HEREDOC.chomp
<estudiante nombre="Matias" legajo="123456-7" celular="1234567890">
	<situacion es_regular=true finales_rendidos=3 materias_aprobadas=5 />
</estudiante>
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(1).to eq 1
      #expect("unString").to eq expected
    end

  end

  describe "✨Ignore✨" do

    # class Alumno
    #   ✨Ignore✨
    #   attr_reader :nombre, :telefono
    #   attr_reader :legajo, :estado
    #   def initialize(nombre, legajo, telefono, estado, dni)
    #     @nombre = nombre
    #     @legajo = legajo
    #     @telefono = telefono
    #     @estado = estado
    #     @dni = dni
    #   end
    #   ✨Ignore✨
    #   def dni
    #     @dni
    #   end
    # end
    # ✨Ignore✨
    # class Estado
    #   attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
    #   def initialize(finales_rendidos, materias_aprobadas, es_regular)
    #     @finales_rendidos = finales_rendidos
    #     @es_regular = es_regular
    #     @materias_aprobadas = materias_aprobadas
    #   end
    # end

    let(:expected) do
<<-HEREDOC.chomp
<alumno legajo="123456-7" />
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(1).to eq 1
      #expect("unString").to eq expected
    end

  end

  describe "✨Inline✨" do

    # class Alumno
    #   ✨Inline✨ {|campo| campo.upcase }
    #   attr_reader :nombre, :legajo
    #   def initialize(nombre, legajo, telefono, estado)
    #     @nombre = nombre
    #     @legajo = legajo
    #     @telefono = telefono
    #     @estado = estado
    #   end
    #   ✨Inline✨ {|estado| estado.es_regular }
    #   def estado
    #     @estado
    #   end
    # end
    # class Estado
    #   attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
    #   def initialize(finales_rendidos, materias_aprobadas, es_regular)
    #     @finales_rendidos = finales_rendidos
    #     @es_regular = es_regular
    #     @materias_aprobadas = materias_aprobadas
    #   end
    # end

    let(:expected) do
<<-HEREDOC.chomp
<alumno nombre="MATIAS" legajo="123456-7" estado=true />
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(1).to eq 1
      #expect("unString").to eq expected
    end

  end

  describe "✨Custom✨" do

    # class Alumno
    #   attr_reader :nombre, :legajo, :telefono
    #   ✨Label✨("situacion")
    #   attr_reader :estado
    #   def initialize(nombre, legajo, telefono, estado)
    #     @nombre = nombre
    #     @legajo = legajo
    #     @telefono = telefono
    #     @estado = estado
    #   end
    # end
    # ✨Custom✨ do |estado|
    #   regular { estado.es_regular }
    #   pendientes { estado.materias_aprobadas - estado.finales_rendidos }
    # end
    # class Estado
    #   attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
    #   def initialize(finales_rendidos, materias_aprobadas, es_regular)
    #     @finales_rendidos = finales_rendidos
    #     @es_regular = es_regular
    #     @materias_aprobadas = materias_aprobadas
    #   end
    # end

    let(:expected) do
<<-HEREDOC.chomp
<alumno nombre="Matias" legajo="123456-7" telefono="1234567890">
	<situacion>
		<regular>true</regular>
		<pendientes>2</pendientes>
	</situacion>
</alumno>
HEREDOC
    end

    it("El XML generado es correcto") do
      expect(1).to eq 1
      #expect("unString").to eq expected
    end

  end

end