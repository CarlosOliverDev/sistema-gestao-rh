package entities;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Coordenador extends FuncionarioCLT{
    public Coordenador(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 9_000.00, Duration.of(5, ChronoUnit.HOURS));
    }
}
