package entities;

import java.time.LocalTime;

public class Coordenador extends FuncionarioCLT{
    public Coordenador(String nomeFuncionario, int idadeFuncionario) {
        super(nomeFuncionario, idadeFuncionario, 9_000.00, LocalTime.of(5,0));
    }
}
