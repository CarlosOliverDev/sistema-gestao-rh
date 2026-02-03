package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

public final class Estagiario extends Funcionario{
    private final Set<LocalDate> diasTrabalhados;

    public Estagiario(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario);
        this.diasTrabalhados = new TreeSet<LocalDate>();
    }

    public Set<LocalDate> getDiasTrabalhados() {
        return diasTrabalhados;
    }

    @Override
    public void adicionarDiaTrabalhado(LocalDate novoDiaTrabalhado) {
        if(this.diasTrabalhados.add(novoDiaTrabalhado)) {
            System.out.println("\nNovo registro de trabalho do estagiário " + this.getNomeFuncionario() + " no dia " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +".");
        } else {
            System.out.println("\nEstagiário " + this.getNomeFuncionario() + " já tem registro no dia " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +".");
        }
    }

    @Override
    public void imprimirDiasTrabalhados() {
        if(getDiasTrabalhados().isEmpty()) {
            System.out.println("O estagiário " + this.getNomeFuncionario() + " não tem nenhum dia de trabalho registrado.");
        } else {
            System.out.println("Dias trabalhados por " + this.getNomeFuncionario() + ":");
            if(getDiasTrabalhados().size() == 1) {
                System.out.println("\nEsse estagiário trabalhou por 1 dia.");
            } else {
                System.out.println("\nEsse estagiário trabalhou por " + getDiasTrabalhados().size() + " dias.");
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
                "\nCargo: Estagiário";
    }
}
