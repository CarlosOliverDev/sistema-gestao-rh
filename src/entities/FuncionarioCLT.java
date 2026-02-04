package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public abstract class FuncionarioCLT extends Funcionario{
    private double salario;
    private final LocalTime jornadaTrabalho = LocalTime.of(8 + getHorarioDeAlmoco().getHour(), getHorarioDeAlmoco().getMinute());
    private final LocalTime maxHorasExtras;
    private final Map<LocalDate,RelatorioHorariosDia> relatoriosDiasTrabalhados;

    public FuncionarioCLT(String nomeFuncionario, int idadeFuncionario, double salario, LocalTime maxHorasExtras) {
        super(nomeFuncionario, idadeFuncionario);
        this.salario = salario;
        this.maxHorasExtras = maxHorasExtras;
        this.relatoriosDiasTrabalhados = new TreeMap<LocalDate,RelatorioHorariosDia>();
    }

    public void imprimirDiaTrabalho(LocalDate novoDiaTrabalhado) {
        super.imprimirInfosBasicas();
        System.out.println("Dia: " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + this.relatoriosDiasTrabalhados.get(novoDiaTrabalhado));
    }

    public void imprimirTodosDiaTrabalho(LocalDate novoDiaTrabalhado) {
        System.out.println("Dia: " + novoDiaTrabalhado.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" + this.relatoriosDiasTrabalhados.get(novoDiaTrabalhado));
    }

    public void adicionarDiaTrabalho(LocalDate novoDiaTrabalhado) {
        this.relatoriosDiasTrabalhados.put(novoDiaTrabalhado, null);
    }

    public void imprimirDiasTrabalhados() {
        if(relatoriosDiasTrabalhados.isEmpty()) {
            System.out.println("O " + this.getClass().getSimpleName() + " " + this.getNomeFuncionario() + " não tem nenhum dia de trabalho registrado.\n");
        } else {
            System.out.println("\nDias trabalhados por " + this.getNomeFuncionario() + ":");
            if(relatoriosDiasTrabalhados.size() == 1) {
                System.out.println("Esse " + this.getClass().getSimpleName() + " trabalhou por 1 dia.");
            } else {
                System.out.println("Esse " + this.getClass().getSimpleName() + " trabalhou por " + relatoriosDiasTrabalhados.size() + " dias.");
            }
            System.out.println("\nDias Registrados:");
            int dias = 1;
            for(Map.Entry<LocalDate,RelatorioHorariosDia> diasTrabalhados : relatoriosDiasTrabalhados.entrySet()) {
                System.out.println("Registro dia " + dias + ":");
                imprimirTodosDiaTrabalho(diasTrabalhados.getKey());
                dias++;
                System.out.println();
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

    public void adicionarDiaTrabalhadoComHorario(LocalDate novoDiaTrabalhado, RelatorioHorariosDia relatorioHorariosDia) {
        this.relatoriosDiasTrabalhados.put(novoDiaTrabalhado, relatorioHorariosDia);
    }

    public int totalHorasJornadaDia() {
        return (getJornadaTrabalho().getHour()*60) + getJornadaTrabalho().getMinute();
    }

    public int totalHorasPermitidasDia() {
        return (getJornadaTrabalho().getHour() + this.getMaxHorasExtras().getHour()) * 60 + (getJornadaTrabalho().getMinute() + this.getMaxHorasExtras().getMinute());
    }

    public boolean existeDataRelatorioTrabalho(LocalDate data) {
        if(this.getRelatoriosDiasTrabalhados().containsKey(data)) {
            return true;
        }
        return false;
    }

    public void excluirDataRelatorioTrabalho(LocalDate data) {
        this.getRelatoriosDiasTrabalhados().remove(data);
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalTime getJornadaTrabalho() {
        return jornadaTrabalho;
    }

    public LocalTime getMaxHorasExtras() {
        return maxHorasExtras;
    }

    public Map<LocalDate, RelatorioHorariosDia> getRelatoriosDiasTrabalhados() {
        return relatoriosDiasTrabalhados;
    }
}
