package application;

import entities.*;
import exceptions.RegraNegocioException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaAdministrador {
    public static Scanner scanner = new Scanner(System.in);
    public static Map<Integer, Funcionario> listaFuncionario = new LinkedHashMap<Integer, Funcionario>();

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Portal de Recursos Humanos.");
        apresentarOpcoes();

        scanner.close();
        listaFuncionario.clear();
    }

    public static void apresentarOpcoes() {
        int opcao = 0;
        do {
            System.out.println("\nMenu:");
            System.out.print("1-Registrar um novo funcionário\n2-Registrar dia ou horas de trabalho\n3-Listar os funcionários\n4-Buscar informações de um funcionário\n5-Fechar o programa\n");
            opcao = gerarInteiro("\nEscolha o número de uma das opções: ");
            escolherOpcao(opcao);
        } while(opcao != 5);
        System.out.println("Programa finalizado.");
    }

    public static void escolherOpcao(int opcao) {
        switch(opcao) {
            case 1:
                registrarNovoFuncionario();
                break;
            case 2:
                registrarDiaDeTrabalho();
                break;
            case 3:
                //listarFuncionarios();
                break;
            case 4:
                //buscarInfoFuncionario();
                break;
            case 5:
                break;
            default:
                System.out.println("Por favor, selecione um número de 1 a 5.");
        }
    }

    public static void registrarNovoFuncionario() {
        System.out.print("\n-Registro Novo Funcionário-");

        System.out.print("\nDigite o nome do novo funcionário: ");
        String nomeFuncionario = scanner.nextLine();

        int idadeFuncionario = gerarInteiro("Digite a idade do novo funcionário: ");

        int cargoFuncionario = 0;
        int id;
        do {
            System.out.println("Número dos Cargos:");
            System.out.println("1-Estagiário\n2-Assistente\n3-Analista\n4-Coordenador\n5-Gerente");
            cargoFuncionario = gerarInteiro("\nSelecione o número do cargo do novo funcionário: ");
            id = criarNovoFuncionario(cargoFuncionario, nomeFuncionario, idadeFuncionario);
        } while(id == 0);

        System.out.println("\nInformações do Novo " + listaFuncionario.get(id).getClass().getSimpleName() + ":");
        System.out.println(listaFuncionario.get(id));
        System.out.println("\nRegistro do Novo " + listaFuncionario.get(id).getClass().getSimpleName() + " Finalizado.");
    }

    public static int criarNovoFuncionario(int cargoFuncionario, String nomeFuncionario, int idadeFuncionario) {
        switch(cargoFuncionario) {
            case 1:
                Estagiario estagiario = new Estagiario(nomeFuncionario, idadeFuncionario);
                listaFuncionario.put(estagiario.getId(), estagiario);
                return estagiario.getId();
            case 2:
                Assistente assistente = new Assistente(nomeFuncionario, idadeFuncionario);
                listaFuncionario.put(assistente.getId(), assistente);
                return assistente.getId();
            case 3:
                Analista analista = new Analista(nomeFuncionario, idadeFuncionario);
                listaFuncionario.put(analista.getId(), analista);
                return analista.getId();
            case 4:
                Coordenador coordenador = new Coordenador(nomeFuncionario, idadeFuncionario);
                listaFuncionario.put(coordenador.getId(), coordenador);
                return coordenador.getId();
            case 5:
                Gerente gerente = new Gerente(nomeFuncionario, idadeFuncionario);
                listaFuncionario.put(gerente.getId(), gerente);
                return gerente.getId();
            default:
                System.out.println("Valor inválido, digite um número entre as opções de cargo.");
                return 0;
        }
    }

    public static void registrarDiaDeTrabalho() {
        if(listaFuncionario.isEmpty()) {
            System.out.println("Não é possível registrar um dia de trabalho, pois nenhum funcionário foi registrado.");
        } else {
            int idFuncionario = gerarInteiro("\nDigite o ID do funcionário: ");
            if(!listaFuncionario.containsKey(idFuncionario)) {
                System.out.println("Não existe um funcionário com esse ID.");
            } else {
                System.out.println("\n-Registro Trabalho-");
                try {
                    registrarNovoDiaTrabalho(idFuncionario);
                } catch (RegraNegocioException e) {
                    System.out.println("Erro: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public static void registrarNovoDiaTrabalho(int idFuncionario) throws RegraNegocioException{
            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
                LocalDate data = gerarData();
                if(!((FuncionarioCLT) listaFuncionario.get(idFuncionario)).existeDataRelatorioTrabalho(data)) {
                    ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).adicionarDiaTrabalho(data);
                    try {
                        adicionarHorasNovoDiaTrabalho(idFuncionario, data);
                    } catch (RegraNegocioException e) {
                        System.out.println("Erro:" + e);
                    }
                } else {
                    System.out.println("O funcionário já tem registro nesse dia.");
                }
            } else {
                throw new RegraNegocioException(listaFuncionario.get(idFuncionario).getClass().getSimpleName() + " não podem ter registros de horários, pois não podem bater ponto.");
            }
    }

    public static void adicionarHorasNovoDiaTrabalho(int idFuncionario, LocalDate data) throws RegraNegocioException {
        LocalTime horarioInicio = gerarHorasEMinutos("Digite o horário que o funcionário iniciou o trabalho: ");
        LocalTime horarioFim = gerarHorasEMinutos("Digite o horário que o funcionário finalizou o trabalho: ");
        try {
            compararHorasJornada(horarioInicio, horarioFim);
        } catch (RegraNegocioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        int duracao = (horarioFim.getHour()*60) + (horarioFim.getMinute()) - (horarioInicio.getHour()*60) + (horarioInicio.getMinute());

        if(((FuncionarioCLT) listaFuncionario.get(idFuncionario)).totalHorasJornadaDia() >= duracao) {
            RelatorioHorariosDia relatorio = new RelatorioHorariosDia(horarioInicio, horarioFim);
            ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).adicionarDiaTrabalhadoComHorario(data, relatorio);
        } else if(((FuncionarioCLT) listaFuncionario.get(idFuncionario)).totalHorasPermitidasDia() >= duracao) {
            adicionarHorasExtrasNovoDiaTrabalho(idFuncionario, data, horarioInicio, horarioFim);
        } else {
            throw new RegraNegocioException("Quantidade de Horas trabalhadas maior do que o permitido para esse cargo.");
        }
    }

    public static void adicionarHorasExtrasNovoDiaTrabalho(int idFuncionario, LocalDate data, LocalTime horarioInicio, LocalTime horarioFimHoraExtra) {
        RelatorioHorariosDia relatorio = new RelatorioHorariosDia(horarioInicio, horarioFimHoraExtra);
        LocalTime horarioFimJornada = horarioInicio.plus(9, ChronoUnit.HOURS);
        horarioFimHoraExtra = horarioFimHoraExtra.minus((horarioFimJornada.getHour() * 60) + horarioFimJornada.getMinute(),ChronoUnit.MINUTES);
        relatorio.setHorasExtras(horarioFimHoraExtra);
        ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).adicionarDiaTrabalhadoComHorario(data, relatorio);
    }

    public static void compararHorasJornada(LocalTime horarioInicio, LocalTime horarioFim) throws RegraNegocioException{
        if(horarioInicio.isAfter(horarioFim) || horarioInicio.equals(horarioFim)) {
            throw new RegraNegocioException("Horário de fim de trabalho não pode ser antes do horário de início do trabalho.");
        }
    }

    public static int gerarInteiro(String mensagem) throws InputMismatchException {
        while(true) {
            try {
                System.out.print(mensagem);
                int escolha = scanner.nextInt();
                scanner.nextLine();
                return escolha;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Erro: Entrada de dados inválida, por favor, utilize um número inteiro.");
            }
        }
    }

    public static LocalDate gerarData() throws DateTimeException {
        while(true) {
            try {
                System.out.print("Digite a data no padrão dia/mês/ano: ");
                String data = scanner.nextLine();
                return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeException e) {
                System.out.println("Erro: Não foi possível registrar a data. Verifique se digitou corretamente e tente novamente.");
            }
        }
    }

    public static LocalTime gerarHorasEMinutos(String mensagem) {
        while(true) {
            try {
                System.out.println("\nUse o padrão horas:minutos, (exemplo 23:58)");
                System.out.print(mensagem);
                String horarioString = scanner.nextLine();
                LocalTime horario = LocalTime.parse(horarioString, DateTimeFormatter.ofPattern("HH:mm"));
                if(horario.isBefore(LocalTime.of(6,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário antes das 06:00, pois o escritório está fechado.");
                }
                if(horario.isAfter(LocalTime.of(22,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário depois das 22:00, pois o escritório está fechado.");
                }
                return horario;
            } catch (DateTimeException e) {
                System.out.println("Erro: Não foi possível registrar esse horário, utilize as horas de 0 a 23, e os minutos de 0 a 59.");
            } catch (RegraNegocioException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
