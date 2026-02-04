package entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RelatorioHorariosDia {
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private LocalTime horasExtras = LocalTime.of(0,0);

    public RelatorioHorariosDia(LocalTime horarioInicio, LocalTime horarioFim) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public String retornarHorasExtrasString() {
        String horasExtraString = "";
        if(getHorasExtras() != LocalTime.of(0,0) ) {
            horasExtraString += "\nHoras Extras Feitas: ";
            if(getHorasExtras().getHour() != 0) {
                if(getHorasExtras().getHour() != 1) {
                    horasExtraString += getHorasExtras().getHour() + " horas";
                } else {
                    horasExtraString += getHorasExtras().getHour() + " hora";
                }
            }
            if(getHorasExtras().getMinute() != 0) {
                if(getHorasExtras().getMinute() != 1) {
                    horasExtraString += " " + getHorasExtras().getMinute() + " minutos";
                } else {
                    horasExtraString += " " + getHorasExtras().getMinute() + " minuto";
                }
            }
            horasExtraString +=  ".";
        }
        return horasExtraString;
    }

    public LocalTime getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(LocalTime horasExtras) {
        this.horasExtras = horasExtras;
    }

    @Override
    public String toString(){
        return "Início Expediente: " + horarioInicio.format(DateTimeFormatter.ofPattern("HH:mm")) +
                "\nFim Expediente: " + horarioFim.format(DateTimeFormatter.ofPattern("HH:mm")) +
                retornarHorasExtrasString();
    }
}
