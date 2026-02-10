package entities;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Analista extends FuncionarioCLT{
    public Analista(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 5_000.00, Duration.of(3, ChronoUnit.HOURS));
    }
}
