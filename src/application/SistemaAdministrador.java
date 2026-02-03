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
            System.out.println("\nNúmero dos Cargos:");
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
                //Assistente assistente = new Assistente(nomeFuncionario, idadeFuncionario);
                //listaFuncionario.put(assistente.getId(), assistente);
                //return assistente.getId();
            case 3:
                //Analista analista = new Analista(nomeFuncionario, idadeFuncionario);
                //listaFuncionario.put(analista.getId(), analista);
                //return analista.getId();
            case 4:
                //Coordenador coordenador = new Coordenador(nomeFuncionario, idadeFuncionario);
                //listaFuncionario.put(coordenador.getId(), coordenador);
                //return coordenador.getId();
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
            System.out.println("Não é possível registrar um dia de trabalho, pois nenhum funcionário foi registrado ainda.");
        } else {
            int escolha;
            do {
                System.out.println("\n-Registro Trabalho-");
                System.out.println("1-Registrar Dia de Trabalho\n2-Adicionar Horas Trabalhadas\n3-Adicionar Horas Extras\n4-Voltar ao menu");
                escolha = gerarInteiro("\nDigite o número de uma das opções: ");
                switch(escolha) {
                    case 1:
                        registrarNovoDiaTrabalho();
                        break;
                    case 2:
                        try {
                            //adicionarHorasTrabalhadas();
                        } catch (RegraNegocioException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            //adicionarHorasExtras();
                        } catch (RegraNegocioException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Valor inválido, digite um número entre as opções de registro de trabalho.");
                }
            } while(escolha != 4);
        }
    }

    //TODO verifica se existe data, verifica se existe horarios, se ja existir os dois
    // = hora extra, ou seja tem que existir 2 botar hora, 1 incluindo já o ID, e 2 horasExtras, 1 incluindo o ID.
    //TODO
    // a duração dos dois ser menor ou igual ao tempo de trabalho + horario de almoço + horas extras


    public static void registrarNovoDiaTrabalho() {
        int idFuncionario = gerarInteiro("\nDigite o ID do funcionário: ");
        if(!listaFuncionario.containsKey(idFuncionario)) {
            System.out.println("Não existe um funcionário com esse ID.");
        } else {
            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
                LocalDate data = gerarData();
                if(!((FuncionarioCLT) listaFuncionario.get(idFuncionario)).existeDataRelatorioTrabalho(data)) {
                    ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).adicionarDiaTrabalhado(data);
                    try {
                        adicionarHorasNovoDiaTrabalho(idFuncionario, data);
                    } catch (RegraNegocioException e) {
                        System.out.println("Erro:" + e);
                    }
                } else {
                    if(!((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getRelatoriosDiasTrabalhados().get(data).trabalhouTodoAJornada(((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getJornadaTrabalho())) {
                        LocalTime tempoTrabalhado = ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getJornadaTrabalho().plus(((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getMaxHorasExtras().getHour()*60 + ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getMaxHorasExtras().getMinute(), ChronoUnit.MINUTES);
                        if(((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getRelatoriosDiasTrabalhados().get(data).trabalhouTodoAJornada(tempoTrabalhado)) {
                            int tempoRestanteHoraExtra = ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getRelatoriosDiasTrabalhados().get(data).tempoRestanteJornadaTrabalho();
                            LocalTime horaRestanteHoraExtra = LocalTime.of(tempoRestanteHoraExtra/60, tempoRestanteHoraExtra%60);
                            System.out.println("\n");
                        }
                        //TODO HORAJORNADANORMAL
                    }


                    //TODO HORA EXTRA(ID) NAO PARECE SIMPLES ESSE AQUI
                    //TODO fazer um método que fale se
                }
            } else {
                LocalDate data = gerarData();
                listaFuncionario.get(idFuncionario).adicionarDiaTrabalhado(data);
            }
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

    public static LocalTime gerarHorasEMinutos(String mensagem) {
        while(true) {
            try {
                System.out.println("Utilize o padrão HH:mm, exemplo 14:30.");
                System.out.println(mensagem);
                String horarioString = scanner.nextLine();
                LocalTime horario = LocalTime.parse(horarioString, DateTimeFormatter.ofPattern("HH:mm"));
                if(horario.isBefore(LocalTime.of(6,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário antes das 06:00, pois o escritório está fechado.");
                }
                if(horario.isAfter(LocalTime.of(22,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário depois das 22:00, pois o escritório está fechado.");
                }
            } catch (DateTimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static LocalDate gerarData() throws DateTimeException {
        while(true) {
            try {
                int dia = gerarInteiro("Digite o número do dia que deseja fazer o registro: ");
                int mes = gerarInteiro("Digite o número do mês que deseja fazer o registro: ");
                int ano = gerarInteiro("Digite o ano que deseja fazer o registro: ");
                return LocalDate.of(ano, mes, dia);
            } catch (DateTimeException e) {
                System.out.println("Erro: Não é possível registrar essa data. Tente uma data existente.\n");
            }
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






//    public static void adicionarHorasTrabalhadas() throws RegraNegocioException{
//        int idFuncionario = gerarInteiro("\nDigite o ID do funcionário: ");
//        if(!listaFuncionario.containsKey(idFuncionario)) {
//            System.out.println("Não existe um funcionário com esse ID.");
//        } else {
//            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
//
//
//
//
//                LocalTime horarioInicio = gerarHorasEMinutos("Digite o horário que o funcionário iniciou o trabalho: ");
//                LocalTime horarioFim = gerarHorasEMinutos("Digite o horário que o funcionário finalizou o trabalho: ");
//
//                compararHorasJornada(horarioInicio, horarioFim);
//
//
//                //if(horas > ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).getJornadaTrabalho().getHour())
//            } else {
//                throw new RegraNegocioException("Gerentes e Estagiários não são permitidos a registrar horas e bater ponto.");
//            }
//        }
//    }

//    public static void registrarNovoDiaTrabalho(int idFuncionario) {
//        LocalDate data = gerarData();
//        if(!((FuncionarioCLT) listaFuncionario.get(idFuncionario)).existeDataRelatorioTrabalho(data)) {
//            ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).adicionarDiaTrabalhado(data);
//            adicionarHorasTrabalhadas();
//        }
//    }

//    public static void adicionarHorasExtras() {//TODO
//        //TODO
//    } //TODO
}
