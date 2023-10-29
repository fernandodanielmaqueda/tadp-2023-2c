RSpec.describe Prueba do

  let(:prueba) { Prueba.new }

  describe "#materia" do
    it("debería pasar este test") do
      expect(prueba.materia).to be :tadp
    end
  end

end

RSpec.describe "Anexo" do

  describe "Ejemplo de uso" do

    it("El XML generado es correcto") do

      tag = Tag
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

      expected = <<-HEREDOC.chomp
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

      expect(tag).to eq expected

    end

  end

end

RSpec.describe "Punto 1: DSL e Impresión" do

  describe "Documento de ejemplo 1" do

    it("El XML generado es correcto") do

      document1 = Document.new do
        alumno nombre: "Matias", legajo: "123456-7" do
          telefono { "1234567890" }
          estado es_regular: true do
            finales_rendidos { 3 }
            materias_aprobadas { 5 }
          end
        end
      end

      expected = <<-HEREDOC.chomp
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

      expect(document1.xml).to eq expected

    end

  end

  describe "Documento de ejemplo 2" do

    it("El XML generado es correcto") do

      document2 = Document.new do
        alumno nombre: "Mati", legajo: "123456-7", edad: 27 do
          telefono { "12345678" }
          estado do
            value { "regular" }
          end
          no_children
        end
      end

      expected = <<-HEREDOC.chomp
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

      expect(document2.xml).to eq expected

    end

  end

end

RSpec.describe "Punto 2: Generación automática" do

  describe "Serialize de ejemplo 1" do

    it("Se cumple: documento_manual.xml == documento_automatico.xml") do

      class Estado
        attr_reader :finales_rendidos, :materias_aprobadas, :es_regular

        def initialize(finales_rendidos, materias_aprobadas, es_regular)
          @finales_rendidos = finales_rendidos
          @materias_aprobadas = materias_aprobadas
          @es_regular = es_regular
        end
      end

      class Alumno
        attr_reader :nombre, :legajo, :estado

        def initialize(nombre, legajo, telefono, estado)
          @nombre = nombre
          @legajo = legajo
          @telefono = telefono
          @estado = estado
        end
      end

      unEstado = Estado.new(3, 5, true)
      unAlumno = Alumno.new("Matias","123456-8", "1234567890", unEstado)

      documento_manual = Document.new do
        alumno nombre: unAlumno.nombre, legajo: unAlumno.legajo do
          estado finales_rendidos: unAlumno.estado.finales_rendidos,
                 materias_aprobadas: unAlumno.estado.materias_aprobadas,
                 es_regular: unAlumno.estado.es_regular
        end
      end

      documento_automatico = Document.serialize(unAlumno)

      expect(documento_manual.xml).to eq documento_automatico.xml

      Object.send(:remove_const, :Estado)
      Object.send(:remove_const, :Alumno)

    end

  end

  describe "Serialize de ejemplo 2" do

    it("El XML generado es correcto") do

      class Docente

        attr_accessor :nombre, :cursos

        def initialize(nombre, cursos)
          @nombre = nombre
          @cursos = cursos
        end
      end

      class Curso

        attr_accessor :materia, :cantidad_alumnos

        def initialize(materia, cantidad_alumnos)
          @materia = materia
          @cantidad_alumnos = cantidad_alumnos
        end
      end

      unDocente = Docente.new("Nico", [Curso.new("tadp", 40), Curso.new("pdep", 35)])

      expected = <<-HEREDOC.chomp
<docente nombre="Nico">
	<curso materia="tadp" cantidad_alumnos=40/>
	<curso materia="pdep" cantidad_alumnos=35/>
</docente>
      HEREDOC

      expect(Document.serialize(unDocente).xml).to eq expected

      Object.send(:remove_const, :Docente)
      Object.send(:remove_const, :Curso)

    end

  end

end

RSpec.describe "Punto 3: Personalización y Metadata" do

  describe "Base" do

    context "Principal" do

      it("Emoticon = '✨'") do
        expect(Emoticon).to eq "✨"
      end

      it("Match de annotations") do
        expect { eval("#{Emoticon}Foo_Annotation#{Emoticon}") }.not_to raise_error(NameError)
      end

    end

    context "Excepciones" do

      it("Si el nombre es una constante errónea se lanza una excepción acorde") do
        expect { eval("#{Emoticon}foo_annotation#{Emoticon}") }.to raise_error(NameError)
      end

      it("En caso de que la clase no exista se lanza una excepción acorde") do
        expect { eval("#{Emoticon}Foo_Annotation#{Emoticon}") }.to raise_error(RuntimeError, "La clase Foo_Annotation no existe")
      end

      it("En caso de que la clase no pueda ser instanciada con los parámetros dados se lanza una excepción acorde") do
        class Foo_Annotation
          def initialize(bar)

          end

        end

        expect { eval("#{Emoticon}Foo_Annotation#{Emoticon}") }.to raise_error(ArgumentError)

        Object.send(:remove_const, :Foo_Annotation)
      end

    end

    context "Instanciación" do

      it("Se puede instanciar una clase sin parámetros ni bloque") do
        class Foo_Annotation
          def apply_to(foo)

          end
        end

        expect { eval("#{Emoticon}Foo_Annotation#{Emoticon}") }.to_not raise_error

        Object.send(:remove_const, :Foo_Annotation)
      end

      it("Se puede instanciar una clase con n parámetros más bloque") do
        class Foo_Annotation
          def initialize(*args, &block)

          end

          def apply_to(declaration)

          end
        end

        expect { eval("#{Emoticon}Foo_Annotation#{Emoticon}(1, 2, 3) {}") }.to_not raise_error

        Object.send(:remove_const, :Foo_Annotation)
      end

    end

    context "Asociación" do

      it("Se puede asociar annotations a definiciones de clases") do

        eval <<-HEREDOC.chomp
        
          class Foo_Annotation
            def apply_to(declaration)
  
            end
          end

          class Bar_Annotation
            def apply_to(declaration)
  
            end
          end
          
          #{Emoticon}Foo_Annotation#{Emoticon}
          #{Emoticon}Bar_Annotation#{Emoticon}
          #{Emoticon}Foo_Annotation#{Emoticon}
          class Foo_Class

          end

        HEREDOC

        expect(Foo_Class.class_associations.map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])

        Object.send(:remove_const, :Foo_Annotation)
        Object.send(:remove_const, :Bar_Annotation)
        Object.send(:remove_const, :Foo_Class)

      end

      it("Se puede asociar annotations a definiciones de métodos") do

        eval <<-HEREDOC.chomp
        
          class Foo_Annotation
            def apply_to(declaration)
  
            end
          end

          class Bar_Annotation
            def apply_to(declaration)
  
            end
          end
          
          class Foo_Class

            def initialize(foo)
              @foo = foo
            end

            #{Emoticon}Foo_Annotation#{Emoticon}
            #{Emoticon}Bar_Annotation#{Emoticon}
            #{Emoticon}Foo_Annotation#{Emoticon}
            def foo
              @foo
            end

          end

        HEREDOC

        expect(Foo_Class.instance_methods_associations.size).to eq 2
        expect(Foo_Class.instance_methods_associations[:initialize]).to eq([])
        expect(Foo_Class.instance_methods_associations[:foo].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])

        Object.send(:remove_const, :Foo_Annotation)
        Object.send(:remove_const, :Bar_Annotation)
        Object.send(:remove_const, :Foo_Class)

      end

      it("Se puede asociar annotations a llamados a attr_reader") do

        eval <<-HEREDOC.chomp
        
          class Foo_Annotation
            def apply_to(declaration)
  
            end
          end

          class Bar_Annotation
            def apply_to(declaration)
  
            end
          end
          
          class Foo_Class

            #{Emoticon}Foo_Annotation#{Emoticon}
            #{Emoticon}Bar_Annotation#{Emoticon}
            #{Emoticon}Foo_Annotation#{Emoticon}
            attr_reader :foo, :bar

            def initialize(foo)
              @foo = foo
            end

          end

        HEREDOC

        expect(Foo_Class.instance_methods_associations.size).to eq 3
        expect(Foo_Class.instance_methods_associations[:initialize]).to eq([])
        expect(Foo_Class.instance_methods_associations[:foo].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])
        expect(Foo_Class.instance_methods_associations[:bar].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])

        Object.send(:remove_const, :Foo_Annotation)
        Object.send(:remove_const, :Bar_Annotation)
        Object.send(:remove_const, :Foo_Class)

      end

      it("Se puede asociar annotations a llamados a attr_accessor") do

        eval <<-HEREDOC.chomp
        
          class Foo_Annotation
            def apply_to(declaration)
  
            end
          end

          class Bar_Annotation
            def apply_to(declaration)
  
            end
          end
          
          class Foo_Class

            #{Emoticon}Foo_Annotation#{Emoticon}
            #{Emoticon}Bar_Annotation#{Emoticon}
            #{Emoticon}Foo_Annotation#{Emoticon}
            attr_accessor :foo, :bar

            def initialize(foo)
              @foo = foo
            end

          end

        HEREDOC

        expect(Foo_Class.instance_methods_associations.size).to eq 5
        expect(Foo_Class.instance_methods_associations[:initialize]).to eq([])
        expect(Foo_Class.instance_methods_associations[:foo].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])
        expect(Foo_Class.instance_methods_associations[:foo=].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])
        expect(Foo_Class.instance_methods_associations[:bar].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])
        expect(Foo_Class.instance_methods_associations[:bar=].map {|element| element.class}).to eq([Foo_Annotation, Bar_Annotation, Foo_Annotation])

        Object.send(:remove_const, :Foo_Annotation)
        Object.send(:remove_const, :Bar_Annotation)
        Object.send(:remove_const, :Foo_Class)

      end

    end

  end

  describe "#{Emoticon}Label#{Emoticon}" do

    context "Instanciación" do

      it "La constante de la clase Label debería existir" do
        expect(ObjectSpace.const_defined?("Label")).to be(true)
      end

      it "Initialize debería ser de aridad 1" do
        expect(Label.instance_method(:initialize).arity).to eq(1)
      end

      it "El parámetro de initialize debería ser requerido" do
        expect(Label.instance_method(:initialize).parameters[0][0]).to be(:req)
      end

    end

    context "Serialización de ejemplo" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp
    
          #{Emoticon}Label#{Emoticon}("estudiante")
          class Alumno
      
            attr_reader :nombre, :legajo, :estado
      
            def initialize(nombre, legajo, telefono, estado)
              @nombre = nombre
              @legajo = legajo
              @telefono = telefono
              @estado = estado
            end
      
            #{Emoticon}Label#{Emoticon}("celular")
            def telefono
              @telefono
            end
      
          end
      
          #{Emoticon}Label#{Emoticon}("situacion")
          class Estado
      
            attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
      
            def initialize(finales_rendidos, materias_aprobadas, es_regular)
              @finales_rendidos = finales_rendidos
              @es_regular = es_regular
              @materias_aprobadas = materias_aprobadas
            end
      
          end
  
        HEREDOC

        serializacion = Document.serialize(Alumno.new("Matias", "123456-7", "1234567890", Estado.new(3, 5, true)))

        expected = <<-HEREDOC.chomp
<estudiante nombre="Matias" legajo="123456-7" celular="1234567890">
	<situacion finales_rendidos=3 es_regular=true materias_aprobadas=5/>
</estudiante>
        HEREDOC

        expect(serializacion.xml).to eq expected

        Object.send(:remove_const, :Alumno)
        Object.send(:remove_const, :Estado)

      end

    end

    context "Excepciones" do

      it "Si por utilizar un label algún XML quedara con dos atributos con el mismo nombre, el serializador debe fallar adecuadamente" do

        eval <<-HEREDOC.chomp

          class Persona
      
            attr_reader :nombre, :dni, :telefono
      
            def initialize(nombre, dni, telefono, celular)
              @nombre = nombre
              @dni = dni
              @telefono = telefono
              @celular = celular
            end
      
            #{Emoticon}Label#{Emoticon}("telefono")
            def celular
              @celular
            end
      
          end
  
        HEREDOC

        expect{Document.serialize(Persona.new("Juan", "123456-7", "1234567890", "9876543210"))}.to raise_error(RuntimeError, "La etiqueta persona ha quedado con dos o mas atributos con el mismo nombre: telefono")

        Object.send(:remove_const, :Persona)

      end

    end

  end

  describe "#{Emoticon}Ignore#{Emoticon}" do

    context "Instanciación" do

      it "La constante de la clase Ignore debería existir" do
        expect(ObjectSpace.const_defined?("Ignore")).to be(true)
      end

      it "Initialize debería ser de aridad 0" do
        expect(Ignore.instance_method(:initialize).arity).to eq(0)
      end

    end

    context "Serialización de ejemplo" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp
  
          class Alumno
            
            #{Emoticon}Ignore#{Emoticon}
            attr_reader :nombre, :telefono
      
            attr_reader :legajo, :estado
      
            def initialize(nombre, legajo, telefono, estado, dni)
              @nombre = nombre
              @legajo = legajo
              @telefono = telefono
              @estado = estado
              @dni = dni
            end
      
            #{Emoticon}Ignore#{Emoticon}
            def dni
              @dni
            end
      
          end
      
          #{Emoticon}Ignore#{Emoticon}
          class Estado
      
            attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
      
            def initialize(finales_rendidos, materias_aprobadas, es_regular)
              @finales_rendidos = finales_rendidos
              @es_regular = es_regular
              @materias_aprobadas = materias_aprobadas
            end
      
          end
  
        HEREDOC

        serializacion = Document.serialize(Alumno.new("Matias", "123456-7", "1234567890", Estado.new(3, 5, true), "9876543210"))

        expected = <<-HEREDOC.chomp
<alumno legajo="123456-7"/>
        HEREDOC

        expect(serializacion.xml).to eq expected

        Object.send(:remove_const, :Alumno)
        Object.send(:remove_const, :Estado)
      end

    end

    context "Serializar una clase anotada con ignore produce un string vacío" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp

          #{Emoticon}Ignore#{Emoticon}
          class Docente
      
            attr_accessor :nombre, :cursos
      
            def initialize(nombre, cursos)
              @nombre = nombre
              @cursos = cursos
            end
          end
      
          class Curso
      
            attr_accessor :materia, :cantidad_alumnos
      
            def initialize(materia, cantidad_alumnos)
              @materia = materia
              @cantidad_alumnos = cantidad_alumnos
            end
          end

        HEREDOC

        serializacion = Document.serialize(Docente.new("Nico", [Curso.new("tadp", 40), Curso.new("pdep", 35)]))

        expect(serializacion.xml).to eq ""

        Object.send(:remove_const, :Docente)
        Object.send(:remove_const, :Curso)

      end

    end

  end

  describe "#{Emoticon}Inline#{Emoticon}" do

    context "Instanciación" do

      it "La constante de la clase Inline debería existir" do
        expect(ObjectSpace.const_defined?("Inline")).to be(true)
      end

      it "Initialize debería ser de aridad 0" do
        expect(Inline.instance_method(:initialize).arity).to eq(0)
      end

      it "El parámetro de initialize debería ser un bloque" do
        expect(Inline.instance_method(:initialize).parameters[0][0]).to be(:block)
      end

      it "Si no se recibe un bloque se lanza una excepción acorde" do
        expect{Inline.new}.to raise_error(RuntimeError, "La anotacion Inline debe recibir un bloque que espera un solo parametro")
      end

      it "Si se recibe un bloque de aridad = 0 se lanza una excepción acorde" do
        expect{Inline.new {}}.to raise_error(RuntimeError, "La anotacion Inline debe recibir un bloque que espera un solo parametro")
      end

      it "Si se recibe un bloque de aridad = 1 no se lanza ninguna excepción" do
        expect{Inline.new {|campo|}}.to_not raise_error
      end

      it "Si se recibe un bloque de aridad > 1 se lanza una excepción acorde" do
        expect{Inline.new {|foo, bar|}}.to raise_error(RuntimeError, "La anotacion Inline debe recibir un bloque que espera un solo parametro")
      end

    end

    context "Serialización de ejemplo" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp
  
          class Alumno
      
            #{Emoticon}Inline#{Emoticon} {|campo| campo.upcase }
            attr_reader :nombre, :legajo
      
            def initialize(nombre, legajo, telefono, estado)
              @nombre = nombre
              @legajo = legajo
              @telefono = telefono
              @estado = estado
            end
      
            #{Emoticon}Inline#{Emoticon} {|estado| estado.es_regular }
            def estado
              @estado
            end
      
          end
      
          class Estado
      
            attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
      
            def initialize(finales_rendidos, materias_aprobadas, es_regular)
              @finales_rendidos = finales_rendidos
              @es_regular = es_regular
              @materias_aprobadas = materias_aprobadas
            end
      
          end
  
        HEREDOC

        serializacion = Document.serialize(Alumno.new("Matias", "123456-7", "1234567890", Estado.new(3, 5, true)))

        expected = <<-HEREDOC.chomp
<alumno nombre="MATIAS" legajo="123456-7" estado=true/>
        HEREDOC

        expect(serializacion.xml).to eq expected

        Object.send(:remove_const, :Alumno)
        Object.send(:remove_const, :Estado)

      end

    end

    context "Excepciones" do

      it "No se debe permitir utilizar la annotation Inline en clases" do

        actual = <<-HEREDOC.chomp

          #{Emoticon}Inline#{Emoticon}{|campo| campo.upcase}
          class Persona
      
            attr_reader :nombre, :dni, :telefono
      
            def initialize(nombre, dni, telefono)
              @nombre = nombre
              @dni = dni
              @telefono = telefono
            end
      
          end
  
        HEREDOC

        expect{eval actual}.to raise_error(RuntimeError, "No se puede utilizar la anotacion Inline en clases: Persona")

        Object.send(:remove_const, :Persona)

      end

      it "Si, luego de aplicar el bloque el campo no tiene un tipo representable con un atributo, el serializador debe fallar" do

        eval <<-HEREDOC.chomp

          class Persona
      
            attr_reader :nombre, :dni, :telefono
      
            def initialize(nombre, dni, telefono, mascota)
              @nombre = nombre
              @dni = dni
              @telefono = telefono
              @mascota = mascota
            end
      
            #{Emoticon}Inline#{Emoticon}{|nombre| Mascota.new(nombre)}
            def mascota
              @mascota
            end
      
          end

class Mascota

  attr_accessor :nombre

  def initialize(nombre)
    @nombre = nombre
  end

end
  
        HEREDOC

        expect{Document.serialize(Persona.new("Juan", "123456-7", "1234567890", "Firulais"))}.to raise_error(RuntimeError, "Luego de aplicar el bloque de la anotacion Inline sobre el campo del atributo mascota, el resultado no tiene un tipo representable como un atributo del tag persona")

        Object.send(:remove_const, :Persona)

      end

    end

  end

  describe "#{Emoticon}Custom#{Emoticon}" do

    context "Instanciación" do

      it "La constante de la clase Custom debería existir" do
        expect(ObjectSpace.const_defined?("Custom")).to be(true)
      end

      it "Initialize debería ser de aridad 0" do
        expect(Custom.instance_method(:initialize).arity).to eq(0)
      end

      it "El parámetro de initialize debería ser un bloque" do
        expect(Custom.instance_method(:initialize).parameters[0][0]).to be(:block)
      end

      it "Si no se recibe un bloque se lanza una excepción acorde" do
        expect{Custom.new}.to raise_error(RuntimeError, "La anotacion Custom debe recibir un bloque que espera un solo parametro")
      end

      it "Si se recibe un bloque de aridad = 0 se lanza una excepción acorde" do
        expect{Custom.new {}}.to raise_error(RuntimeError, "La anotacion Custom debe recibir un bloque que espera un solo parametro")
      end

      it "Si se recibe un bloque de aridad = 1 no se lanza ninguna excepción" do
        expect{Custom.new {|campo|}}.to_not raise_error
      end

      it "Si se recibe un bloque de aridad > 1 se lanza una excepción acorde" do
        expect{Custom.new {|foo, bar|}}.to raise_error(RuntimeError, "La anotacion Custom debe recibir un bloque que espera un solo parametro")
      end

    end

    context "Serialización de ejemplo" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp

          class Alumno
      
            attr_reader :nombre, :legajo, :telefono
      
            #{Emoticon}Label#{Emoticon}("situacion")
            attr_reader :estado
      
            def initialize(nombre, legajo, telefono, estado)
              @nombre = nombre
              @legajo = legajo
              @telefono = telefono
              @estado = estado
            end
      
          end
      
          #{Emoticon}Custom#{Emoticon} do |estado|
            regular { estado.es_regular }
            pendientes { estado.materias_aprobadas - estado.finales_rendidos }
          end
          class Estado
      
            attr_reader :finales_rendidos, :materias_aprobadas, :es_regular
      
            def initialize(finales_rendidos, materias_aprobadas, es_regular)
              @finales_rendidos = finales_rendidos
              @es_regular = es_regular
              @materias_aprobadas = materias_aprobadas
            end
      
          end

        HEREDOC

        serializacion = Document.serialize(Alumno.new("Matias", "123456-7", "1234567890", Estado.new(3, 5, true)))

        expected = <<-HEREDOC.chomp
<alumno nombre="Matias" legajo="123456-7" telefono="1234567890">
	<situacion>
		<regular>
			true
		</regular>
		<pendientes>
			2
		</pendientes>
	</situacion>
</alumno>
        HEREDOC

        expect(serializacion.xml).to eq expected

        Object.send(:remove_const, :Alumno)
        Object.send(:remove_const, :Estado)
      end

    end

    context "Excepciones" do

      it "Sólo se debe permitir utilizar la annotation Custom en clases" do

        actual = <<-HEREDOC.chomp

          class Persona
      
            attr_reader :nombre, :dni, :telefono
      
            def initialize(nombre, dni, telefono)
              @nombre = nombre
              @dni = dni
              @telefono = telefono
            end
      
            #{Emoticon}Custom#{Emoticon}{|campo|}
            def nombre
              @nombre
            end

          end
  
        HEREDOC

        expect{eval actual}.to raise_error(RuntimeError, "La anotacion Custom solo puede ser utilizada en clases; no en: nombre")

        Object.send(:remove_const, :Persona)

      end

    end

  end

  describe "#{Emoticon}Extra#{Emoticon}" do

    context "Instanciación" do

      it "La constante de la clase Extra debería existir" do
        expect(ObjectSpace.const_defined?("Extra")).to be(true)
      end

      it "Initialize debería ser de aridad 1" do
        expect(Extra.instance_method(:initialize).arity).to eq(1)
      end

      it "El primer parámetro de initialize (el simbolo) debería ser requerido" do
        expect(Extra.instance_method(:initialize).parameters[0][0]).to be(:req)
      end

      it "El segundo parámetro de initialize debería ser un bloque" do
        expect(Extra.instance_method(:initialize).parameters[1][0]).to be(:block)
      end

      it "Si no se recibe un bloque se lanza una excepción acorde" do
        expect{Extra.new(:simbolo)}.to raise_error(RuntimeError, "La anotacion Extra debe recibir un bloque que no espera parametros")
      end

      it "Si se recibe un bloque de aridad = 0 no se lanza ninguna excepción" do
        expect{Extra.new(:simbolo) {}}.to_not raise_error
      end

      it "Si se recibe un bloque de aridad > 1 se lanza una excepción acorde" do
        expect{Extra.new(:simbolo) {|campo|}}.to raise_error(RuntimeError, "La anotacion Extra debe recibir un bloque que no espera parametros")
      end

    end

    context "Serialización de ejemplo" do

      it("El XML generado es correcto") do

        eval <<-HEREDOC.chomp

          #{Emoticon}Extra#{Emoticon}(:global) { (@fuerza + @velocidad) / 2}
          class Guerrero

            attr_accessor :salud
        
            def initialize(fuerza, velocidad, salud)
              @fuerza = fuerza
              @velocidad = velocidad
              @salud = salud
            end

          end

        HEREDOC

        serializacion = Document.serialize(Guerrero.new(50, 70, 100))

        expected = <<-HEREDOC.chomp
<guerrero global=60 salud=100/>
        HEREDOC

        expect(serializacion.xml).to eq expected

        Object.send(:remove_const, :Guerrero)
      end

    end

    context "Excepciones" do

      it "Sólo se debe permitir utilizar la annotation Extra en clases" do

        actual = <<-HEREDOC.chomp

          class Persona
      
            attr_reader :nombre, :dni, :telefono
      
            def initialize(nombre, dni, telefono)
              @nombre = nombre
              @dni = dni
              @telefono = telefono
            end
      
            #{Emoticon}Extra#{Emoticon}(:simbolo) {}
            def nombre
              @nombre
            end

          end
  
        HEREDOC

        expect{eval actual}.to raise_error(RuntimeError, "La anotacion Extra solo puede ser utilizada en clases; no en: nombre")

        Object.send(:remove_const, :Persona)

      end

    end

  end

end