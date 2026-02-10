package entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

public abstract class FuncionarioCLT extends Funcionario{
    private double salario;
    private final Duration jornadaTrabalho = Duration.of(8, ChronoUnit.HOURS).plus(getHorarioDeAlmoco());
    private final Duration maxHorasExtras;
    private final Map<LocalDate,RelatorioHorariosDia> relatoriosDiasTrabalhados;

    public FuncionarioCLT(String nomeFuncionario, int idadeFuncionario, double salario, Duration maxHorasExtras) {
        super(nomeFuncionario, idadeFuncionario);
        this.salario = salario;
        this.maxHorasExtras = maxHorasExtras;
        this.relatoriosDiasTrabalhados = new TreeMap<LocalDate,RelatorioHorariosDia>();
    }

    public void adicionarDiaTrabalhadoComHorario(LocalDate novoDiaTrabalhado, RelatorioHorariosDia relatorioHorariosDia) {
        this.relatoriosDiasTrabalhados.put(novoDiaTrabalhado, relatorioHorariosDia);
    }

    public boolean existeDataRelatorioTrabalho(LocalDate data) {
        return this.getRelatoriosDiasTrabalhados().containsKey(data);
    }

    public void imprimirDiaTrabalho(LocalDate novoDiaTrabalhado) {
        System.out.println("\n-Novo Registro de Trabalho-");
        System.out.println(super.imprimirInfosBasicas());
        System.out.println("Dia: " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + this.relatoriosDiasTrabalhados.get(novoDiaTrabalhado));
    }

    public void imprimirDiasTrabalhados() {
        if(relatoriosDiasTrabalhados.isEmpty()) {
            System.out.println("O " + this.getClass().getSimpleName() + " " + this.getNomeFuncionario() + " não tem nenhum dia de trabalho registrado.");
        } else {
            System.out.println("\nDias trabalhados por " + this.getNomeFuncionario() + ":");
            if(relatoriosDiasTrabalhados.size() == 1) {
                System.out.println("Esse " + this.getClass().getSimpleName() + " trabalhou por 1 dia.");
            } else {
                System.out.println("Esse " + this.getClass().getSimpleName() + " trabalhou por " + relatoriosDiasTrabalhados.size() + " dias.");
            }
            System.out.print("\n-Dias Registrados-");
            int dias = 1;
            for(Map.Entry<LocalDate,RelatorioHorariosDia> diasTrabalhados : relatoriosDiasTrabalhados.entrySet()) {
                System.out.println("\nRegistro dia " + dias + ":");
                System.out.println("Dia: " + diasTrabalhados.getKey().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + this.relatoriosDiasTrabalhados.get(diasTrabalhados.getKey()));
                dias++;
            }
        }
    }

    @Override
    public String toString() {
        return  "ID: " + this.getId() +
                "\nNome: " + this.getNomeFuncionario() +
                "\nIdade: " + this.getIdadeFuncionario() +
                "\nCargo: " + this.getClass().getSimpleName() +
                "\nSalário: R$ " + String.format("%.2f",getSalario());
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Duration getJornadaTrabalho() {
        return jornadaTrabalho;
    }

    public Duration getMaxHorasExtras() {
        return maxHorasExtras;
    }

    public Map<LocalDate, RelatorioHorariosDia> getRelatoriosDiasTrabalhados() {
        return relatoriosDiasTrabalhados;
    }
}
