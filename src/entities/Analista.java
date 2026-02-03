package entities;

import java.time.LocalTime;

public class Analista extends FuncionarioCLT{
    public Analista(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 5_000.00, LocalTime.of(3,0));
    }
}
