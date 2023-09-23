describe Prueba do
  let(:prueba) { Prueba.new }

  describe '#materia' do
    it 'debería pasar este test' do
      expect(prueba.materia).to be :tadp
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