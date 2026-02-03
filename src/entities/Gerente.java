package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

public final class Gerente extends Funcionario{
    private final Set<LocalDate> diasTrabalhados;
    private double salario = 12_000.00;

    public Gerente(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario);
        this.diasTrabalhados = new TreeSet<LocalDate>();
    }

    public Set<LocalDate> getDiasTrabalhados() {
        return diasTrabalhados;
    }

    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public void adicionarDiaTrabalhado(LocalDate novoDiaTrabalhado) {
        if(this.diasTrabalhados.add(novoDiaTrabalhado)) {
            System.out.println("Novo registro de trabalho do gerente " + this.getNomeFuncionario() + " no dia " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +".");
        } else {
            System.out.println("Gerente " + this.getNomeFuncionario() + " já tem registro no dia " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +".");
        }
    }

    @Override
    public void imprimirDiasTrabalhados() {
        if(getDiasTrabalhados().isEmpty()) {
            System.out.println("O gerente " + this.getNomeFuncionario() + " não tem nenhum dia de trabalho registrado.");
        } else {
            System.out.println("Dias trabalhados por " + this.getNomeFuncionario() + ":");
            if(getDiasTrabalhados().size() == 1) {
                System.out.println("\nEsse gerente trabalhou por 1 dia.");
            } else {
                System.out.println("\nEsse gerente trabalhou por " + getDiasTrabalhados().size() + " dias.");
            }
            for(LocalDate diaTrabalhado : getDiasTrabalhados()) {
                System.out.println(diaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
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