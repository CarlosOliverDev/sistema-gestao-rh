package entities;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Assistente extends FuncionarioCLT {
    public Assistente(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 2_800.00, Duration.of(3, ChronoUnit.HOURS));
    }
}
