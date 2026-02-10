package application;

import entities.*;
import exceptions.RegraNegocioException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaAdministrador {
    public static Scanner scanner = new Scanner(System.in);
    public static Map<Integer, Funcionario> listaFuncionario = new LinkedHashMap<Integer, Funcionario>();

    public static void main(String[] args) {
        System.out.print("Bem-vindo ao Portal de Recursos Humanos.");
        apresentarOpcoes();

        scanner.close();
        listaFuncionario.clear();
    }

    public static void apresentarOpcoes() {
        int opcao = 0;
        do {
            System.out.println("\nMenu do Portal de Recursos Humanos:");
            System.out.print("1-Registrar um novo funcionário\n2-Registrar dia ou horas de trabalho de um funcionário\n3-Listar os funcionários\n4-Buscar informações de um funcionário\n5-Fechar o programa\n");
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
                listarFuncionarios();
                break;
            case 4:
                buscarInfoFuncionario();
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
        while (idadeFuncionario < 16) {
            System.out.println("\nÉ proibido contratar pessoas menores de 16 anos, insira outro valor.");
            idadeFuncionario = gerarInteiro("Digite a idade do novo funcionário: ");
        }

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

        System.out.print("\nDeseja confimar o registro do novo funcionário? (s/n): ");
        char confirmar = scanner.nextLine().charAt(0);
        if(confirmar == 's' || confirmar == 'S') {
            System.out.println("Registro do Novo " + listaFuncionario.get(id).getClass().getSimpleName() + " Finalizado.");
        } else {
            listaFuncionario.get(id).diminuirID();
            listaFuncionario.remove(id);
            System.out.println("Registro cancelado.");
        }
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
                System.out.println(listaFuncionario.get(idFuncionario).imprimirInfosBasicas());
                try {
                    registrarNovoDiaTrabalho(idFuncionario);
                } catch (RegraNegocioException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }

    public static void registrarNovoDiaTrabalho(int idFuncionario) throws RegraNegocioException{
            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
                System.out.println("\n-Registro Trabalho-");
                LocalDate data = gerarData();
                FuncionarioCLT funcionarioCLT = (FuncionarioCLT) listaFuncionario.get(idFuncionario);
                if(!funcionarioCLT.existeDataRelatorioTrabalho(data)) {
                    System.out.println("Data - " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    try {
                        adicionarHorasNovoDiaTrabalho(funcionarioCLT, data);
                    } catch (RegraNegocioException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                } else {
                    System.out.println("O funcionário já tem registro nesse dia.");
                }
            } else {
                throw new RegraNegocioException(listaFuncionario.get(idFuncionario).getClass().getSimpleName() + "s não podem ter registros de horários e não podem bater ponto.");
            }
    }

    public static void adicionarHorasNovoDiaTrabalho(FuncionarioCLT funcionarioCLT, LocalDate data) throws RegraNegocioException {
        System.out.println("\n-Início do Expediente-");
        LocalTime horarioInicio = gerarHorasEMinutos("Digite o horário que o funcionário iniciou o trabalho: ");
        System.out.println("Início do Expediente - " + horarioInicio.format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.println("\n-Fim do Expediente-");
        LocalTime horarioFim = gerarHorasEMinutos("Digite o horário que o funcionário finalizou o trabalho: ");
        System.out.println("Fim do Expediente - " + horarioFim.format(DateTimeFormatter.ofPattern("HH:mm")));

        if(horarioInicio.isAfter(horarioFim) || horarioInicio.equals(horarioFim)) {
            throw new RegraNegocioException("Horário de fim de expediente precisa ser depois do horário de início de expediente.");
        }

        Duration duracaoTrabalho = Duration.between(horarioInicio, horarioFim);
        Duration duracaoMaxTempoTrabalho = Duration.ofMinutes(funcionarioCLT.totalHorasPermitidasDia());
        Duration duracaoJornadaTrabalho = Duration.ofMinutes(funcionarioCLT.totalHorasJornadaDia());

        if(duracaoTrabalho.compareTo(duracaoMaxTempoTrabalho) > 0) {
            throw new RegraNegocioException("Quantidade de Horas trabalhadas maior do que o permitido para esse cargo.");
        } else if (duracaoTrabalho.compareTo(duracaoJornadaTrabalho) > 0) {
            RelatorioHorariosDia relatorio = new RelatorioHorariosDia(horarioInicio, horarioFim);
            Duration horasExtras = duracaoTrabalho.minus(duracaoJornadaTrabalho);
            relatorio.setHorasExtras(horasExtras);
            funcionarioCLT.adicionarDiaTrabalhadoComHorario(data, relatorio);
        } else {
            RelatorioHorariosDia relatorio = new RelatorioHorariosDia(horarioInicio, horarioFim);
            funcionarioCLT.adicionarDiaTrabalhadoComHorario(data, relatorio);
        }
        funcionarioCLT.imprimirDiaTrabalho(data);
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

    public static LocalDate gerarData() {
        while(true) {
            try {
                System.out.print("Digite a data no padrão dia/mês/ano, (exemplo 01/01/2000): ");
                String data = scanner.nextLine();
                return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeException e) {
                System.out.println("Erro: Não foi possível registrar a data. Verifique se digitou corretamente e tente novamente.\n");
            }
        }
    }

    public static LocalTime gerarHorasEMinutos(String mensagem) {
        while(true) {
            try {
                System.out.println("Use o padrão horas:minutos, (exemplo 23:58)");
                System.out.print(mensagem);
                String horarioString = scanner.nextLine();
                LocalTime horario = LocalTime.parse(horarioString, DateTimeFormatter.ofPattern("HH:mm"));
                if(horario.isBefore(LocalTime.of(6,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário antes das 06:00, pois o escritório está fechado.\n");
                }
                if(horario.isAfter(LocalTime.of(22,0))) {
                    throw new RegraNegocioException("Não é possível registrar horário depois das 22:00, pois o escritório está fechado.\n");
                }
                return horario;
            } catch (DateTimeException e) {
                System.out.println("Erro: Não foi possível registrar esse horário, utilize as horas de 0 a 23, e os minutos de 0 a 59.\n");
            } catch (RegraNegocioException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static void listarFuncionarios() {
        if(listaFuncionario.isEmpty()) {
            System.out.println("Não é possível listar os funcionários, pois nenhum funcionário foi registrado.");
        } else {
            System.out.print("\n-Lista de Funcionários Registrados-");
            int indice = 1;
            for(Map.Entry<Integer,Funcionario> funcionarioEntry : listaFuncionario.entrySet()) {
                System.out.println("\nFuncionário " + indice + ":");
                System.out.println(funcionarioEntry.getValue().imprimirInfosBasicas());
                indice++;
            }
        }
    }

    public static void buscarInfoFuncionario() {
        if(listaFuncionario.isEmpty()) {
            System.out.println("Não é possível buscar informações de algum funcionário, pois nenhum funcionário foi registrado.");
        } else {
            int idFuncionario = gerarInteiro("\nDigite o ID do funcionário: ");
            if(!listaFuncionario.containsKey(idFuncionario)) {
                System.out.println("Não existe um funcionário com esse ID.");
            } else {
                System.out.println(listaFuncionario.get(idFuncionario).imprimirInfosBasicas());
                buscarInfo(idFuncionario);
            }
        }
    }

    public static void buscarInfo(int idFuncionario) {
        int opcao = 0;
        do {
            System.out.print("\n-Buscar Informações do Funcionário-");
            System.out.print("\n1-Listar os dados de todos os registros de horas desse funcionário\n2-Remover usuário\n3-Voltar ao menu\n");
            opcao = gerarInteiro("\nEscolha o número de uma das opções: ");
            switch(opcao) {
                case 1:
                    try {
                        listarRegistrosFuncionario(idFuncionario);
                    } catch (RegraNegocioException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    int removeu = removerFuncionario(idFuncionario);
                    if(removeu == 0) {
                        break;
                    }
                    return;
                case 3:
                    break;
                default:
                    System.out.println("Por favor, selecione um número de 1 a 3.");
            }
        } while(opcao != 3);
    }

    public static void listarRegistrosFuncionario(int idFuncionario) throws RegraNegocioException{
        if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
            ((FuncionarioCLT) listaFuncionario.get(idFuncionario)).imprimirDiasTrabalhados();
        } else {
            throw new RegraNegocioException(listaFuncionario.get(idFuncionario).getClass().getSimpleName() + "s não tem dias de trabalhos registrados, pois não podem ter registros de horários e não podem bater ponto.");
        }
    }

    public static int removerFuncionario(int idFuncionario) {
        System.out.println(listaFuncionario.get(idFuncionario).imprimirInfosBasicas());
        System.out.print("\nRealmente deseja remover esse funcionário? (s/n): ");
        char confirmar = scanner.nextLine().charAt(0);
        if(confirmar == 's' || confirmar == 'S') {
            System.out.println("Usuário " + listaFuncionario.get(idFuncionario).getNomeFuncionario() + " removido.");
            listaFuncionario.remove(idFuncionario);
            return 1;
        } else {
            System.out.println("Remoção cancelada.");
            return 0;
        }
    }
}
