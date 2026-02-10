package entities;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RelatorioHorariosDia {
    private final LocalTime horarioInicio;
    private final LocalTime horarioFim;
    private Duration horasExtras;

    public RelatorioHorariosDia(LocalTime horarioInicio, LocalTime horarioFim) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public String retornarHorasExtrasString() {
        String horasExtraString = "";
        if(getHorasExtras() != null) {
            horasExtraString += "\nHoras Extras Feitas: ";
            if(getHorasExtras().toHours() > 0) {
                if(getHorasExtras().toHours() != 1) {
                    horasExtraString += getHorasExtras().toHours() + " horas";
                } else {
                    horasExtraString += getHorasExtras().toHours() + " hora";
                } if(getHorasExtras().toMinutesPart() > 0) {
                    horasExtraString += " e ";
                }
            }
            if(getHorasExtras().toMinutesPart() > 0) {
                if(getHorasExtras().toMinutesPart() != 1) {
                    horasExtraString += getHorasExtras().toMinutesPart() + " minutos";
                } else {
                    horasExtraString += getHorasExtras().toMinutesPart() + " minuto";
                }
            }
        }
        return horasExtraString;
    }

    public Duration getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Duration horasExtras) {
        this.horasExtras = horasExtras;
    }

    @Override
    public String toString(){
        return "Início Expediente: " + horarioInicio.format(DateTimeFormatter.ofPattern("HH:mm")) +
                "\nFim Expediente: " + horarioFim.format(DateTimeFormatter.ofPattern("HH:mm")) +
                retornarHorasExtrasString();
    }
}
