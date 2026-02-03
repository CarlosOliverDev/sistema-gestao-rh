package entities;

import java.time.LocalTime;

public class Assistente extends FuncionarioCLT {
    public Assistente(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 2_800.00, LocalTime.of(3,0));
    }
}
