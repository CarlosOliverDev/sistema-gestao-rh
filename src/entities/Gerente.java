package entities;

public final class Gerente extends Funcionario{
    private double salario = 12_000.00;

    public Gerente(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario);
    }

    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return  "ID: " + this.getId() +
                "\nNome: " + this.getNomeFuncionario() +
                "\nIdade: " + this.getIdadeFuncionario() +
                "\nCargo: Gerente" +
                "\nSalário: R$ " + String.format("%.2f",this.getSalario());
    }
}