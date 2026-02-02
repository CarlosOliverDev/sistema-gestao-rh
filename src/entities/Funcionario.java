package entities;

import java.time.LocalTime;

public abstract class Funcionario {
    private static final LocalTime horarioDeAlmoco = LocalTime.of(1,0);
    private static int ID = 1;

    private final int id;
    private String nomeFuncionario;
    private int idadeFuncionario;

    public Funcionario(String nomeFuncionario, int idadeFuncionario) {
        this.id = ID;
        this.nomeFuncionario = nomeFuncionario;
        this.idadeFuncionario = idadeFuncionario;
        ID++;
    }

    public int getId() {
        return id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public int getIdadeFuncionario() {
        return idadeFuncionario;
    }
    public void setIdadeFuncionario(int idadeFuncionario) {
        this.idadeFuncionario = idadeFuncionario;
    }

    @Override
    public abstract String toString();
}
