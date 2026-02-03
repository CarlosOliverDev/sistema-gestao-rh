package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public abstract class FuncionarioCLT extends Funcionario{
    private double salario;
    private LocalTime jornadaTrabalho = LocalTime.of(8 + getHorarioDeAlmoco().getHour(), getHorarioDeAlmoco().getMinute());
    private LocalTime maxHorasExtras;
    private final Map<LocalDate, RelatorioHorariosDia> relatoriosDiasTrabalhados;

    public FuncionarioCLT(String nomeFuncionario, int idadeFuncionario, double salario, LocalTime maxHorasExtras) {
        super(nomeFuncionario, idadeFuncionario);
        this.salario = salario;
        this.maxHorasExtras = maxHorasExtras;
        this.relatoriosDiasTrabalhados = new TreeMap<LocalDate, RelatorioHorariosDia>();
    }

    @Override
    public void adicionarDiaTrabalhado(LocalDate novoDiaTrabalhado) {
        this.relatoriosDiasTrabalhados.put(novoDiaTrabalhado, null);
    }

    public void adicionarDiaTrabalhadoComHorario(LocalDate novoDiaTrabalhado, RelatorioHorariosDia relatorioHorariosDia) {
        this.relatoriosDiasTrabalhados.put(novoDiaTrabalhado, relatorioHorariosDia);
    }

    @Override
    public void imprimirDiasTrabalhados() {

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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalTime getJornadaTrabalho() {
        return jornadaTrabalho;
    }

    public void setJornadaTrabalho(LocalTime jornadaTrabalho) {
        this.jornadaTrabalho = jornadaTrabalho;
    }

    public LocalTime getMaxHorasExtras() {
        return maxHorasExtras;
    }

    public void setMaxHorasExtras(LocalTime maxHorasExtras) {
        this.maxHorasExtras = maxHorasExtras;
    }

    public Map<LocalDate, RelatorioHorariosDia> getRelatoriosDiasTrabalhados() {
        return relatoriosDiasTrabalhados;
    }
}
