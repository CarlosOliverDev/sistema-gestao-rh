package application;

import entities.Estagiario;
import entities.Funcionario;
import entities.FuncionarioCLT;
import entities.Gerente;
import exceptions.RegraNegocioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaAdministrador {
    public static Scanner scanner = new Scanner(System.in);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
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
            System.out.print("1-Registrar um novo funcionário\n2-Registrar um dia de trabalho\n3-Listar os funcionários\n4-Buscar informações de um funcionário\n5-Fechar o programa\n");
            opcao = gerarInteiro("Escolha o número de uma das opções: ");
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
            cargoFuncionario = gerarInteiro("Selecione o número do cargo do novo funcionário: ");
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
            System.out.println("\nNão é possível registrar um dia de trabalho, pois nenhum funcionário foi registrado ainda.");
        } else {
            int escolha;
            do {
                System.out.println("\n-Registro Trabalho-");
                System.out.println("1-Registrar Dia de Trabalho\n2-Adicionar Horas Trabalhadas\n3-Adicionar Horas Extras");
                escolha = gerarInteiro("Digite o número de uma das opções: ");
                switch(escolha) {
                    case 1:
                        registrarNovoDiaTrabalho();
                        break;
                    case 2:
                        adicionarHorasTrabalhadas();
                        break;
                    case 3:
                        adicionarHorasExtras();
                    default:
                        System.out.println("Valor inválido, digite um número entre as opções de registro de trabalho.");
                }
            } while(escolha < 1 || escolha > 3);
        }
    }

    public static void registrarNovoDiaTrabalho() {
        int idFuncionario = gerarInteiro("\nDigite o ID do funcionário: ");
        if(!listaFuncionario.containsKey(idFuncionario)) {
            System.out.println("Não existe um funcionário com esse ID.");
        } else {
            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
                System.out.println("Registrar o dia: ");
                adicionarHoras(idFuncionario);
            } else {
                LocalDate data = adicionarData();
                listaFuncionario.get(idFuncionario).adicionarDiaTrabalhado(data);
            }
        }
    }

    public static void adicionarHorasTrabalhadas() throws RegraNegocioException {
        int idFuncionario = gerarInteiro("Digite o ID do funcionário: ");
        if(!listaFuncionario.containsKey(idFuncionario)) {
            System.out.println("Não existe um funcionário com esse ID.");
        } else {
            if(listaFuncionario.get(idFuncionario) instanceof FuncionarioCLT) {
                //TODO
                adicionarHoras(idFuncionario);
            } else {
                throw new RegraNegocioException("Erro: Gerentes e Estagiários não são permitidos a registrar horas e bater ponto.");
            }
        }
    }

    public static void adicionarHoras(int idFuncionario) {
        //TODO REGISTRAR HORAS CASO JÁ TIVER USAR MÉTODO adicionarHorasExtras()
    }

    public static void adicionarHorasExtras() {
        //TODO
    }

    public static LocalDate adicionarData() {
        int dia = gerarInteiro("Digite o número do dia que deseja registrar: ");
        int mes = gerarInteiro("Digite o número do mês que deseja registrar: ");
        int ano = gerarInteiro("Digite o ano que deseja registrar: ");
        return LocalDate.of(ano, mes, dia);
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
                System.out.println("Erro: Entrada de dados inválida, por favor, utilize um número inteiro.\n");
            }
        }
    }
}
