package entities;

import java.time.LocalDate;
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

    public void adicionarDiaTrabalhado(LocalDate novoDiaTrabalhado) {
        if(this.diasTrabalhados.add(novoDiaTrabalhado)) {
            System.out.println("Novo registro de trabalho do estagiário " + this.getNomeFuncionario() + " no dia " + novoDiaTrabalhado +".");
        } else {
            System.out.println("Estagiário " + this.getNomeFuncionario() + " já tem registro no dia " + novoDiaTrabalhado +".");
        }
    }

    @Override
    public void imprimirDiasTrabalhados() {
        if(getDiasTrabalhados().isEmpty()) {
            System.out.println("Nenhum dia de trabalho registrado.");
        } else {
            System.out.println("Dias trabalhados por " + this.getNomeFuncionario() + ":");
            int dias = 0;
            for(LocalDate diaTrabalhado : getDiasTrabalhados()) {
                System.out.print(diaTrabalhado + " ");
                dias++;
            }
            if(dias == 1) {
                System.out.println("\nEsse estagiário trabalhou por 1 dia.");
            } else {
                System.out.println("\nEsse estagiário trabalhou por " + dias + " dias.");
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
