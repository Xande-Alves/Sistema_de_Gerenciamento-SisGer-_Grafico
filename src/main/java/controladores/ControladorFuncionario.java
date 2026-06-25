package controladores;

import entidades.Funcionario;
import entidades.Pessoa;
//import menus.MenuEntidade;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ControladorFuncionario extends ControladorPessoa {

    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static ControladorFuncionario controladorFuncionarioInstancia;

    private ControladorFuncionario() {

    }

    public static ControladorFuncionario getInstanciaControladorFuncionario() {
        if (controladorFuncionarioInstancia == null) {
            controladorFuncionarioInstancia = new ControladorFuncionario();
        }
        return controladorFuncionarioInstancia;
    }

    public int cadastrarFuncionarioGrafico(String nome, String cpf, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {

        int idFuncionario = Repositorio.getInstanciaRepositorio().getListaFuncionarios().size() + 1;

        Funcionario func = new Funcionario(idFuncionario, false);

        cadastrarPessoaGrafico(func, nome, cpf, email, telefone, logradouro, numero, bairro, cidade, estado, cep);

        Repositorio.getInstanciaRepositorio().getListaFuncionarios().add(func);

        return idFuncionario;
    }

    public boolean cpfJaCadastradoFuncionario(String cpf) {
        // Pega a lista atual de clientes
        java.util.List<entidades.Funcionario> funcionario = repositorio.Repositorio.getInstanciaRepositorio().getListaFuncionarios();

        // Limpa pontos e traços do CPF que vem da tela para comparar apenas os números limpos
        String cpfNovoLimpo = cpf.replaceAll("[^0-9]", "");

        for (entidades.Funcionario func : funcionario) {
            String cpfExistenteLimpo = func.getCpf().replaceAll("[^0-9]", "");

            if (cpfExistenteLimpo.equals(cpfNovoLimpo)) {
                return true; // Encontrou um cliente com o mesmo CPF
            }
        }

        return false; // CPF está livre para cadastro
    }

    public entidades.Funcionario buscarFuncionarioPorIdOuCpf(String tipoBusca, String termo) {
        java.util.List<entidades.Funcionario> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaFuncionarios();

        for (entidades.Funcionario func : lista) {
            if (tipoBusca.equals("ID") && String.valueOf(func.getIdentificacao()).equals(termo.trim())) {
                return func;
            } else if (tipoBusca.equals("CPF")) {
                String cpfLista = func.getCpf().replaceAll("[^0-9]", "");
                String cpfBusca = termo.replaceAll("[^0-9]", "");
                if (cpfLista.equals(cpfBusca)) {
                    return func;
                }
            }
        }
        return null; // Não encontrou
    }

    public void atualizarFuncionarioGrafico(int id, String nome, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {

        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == id) {

                alteraDadosPessoaGrafico(func, nome, email, telefone, logradouro, numero, bairro, cidade, estado, cep);
            }
        }
    }

    public void listarFuncionarios() {
        System.out.println("=======================LISTA DE FUNCIONÁRIOS=======================");
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            System.out.println("ID Funcionário: " + func.getIdFuncionario());
            //mostrarDadosPessoa(func);
            System.out.println("==================================================================");
        }
    }

    public void consultarFuncionarioNome() {
        consultarPessoaPorNome(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioCpf() {
        consultarPessoaPorCpf(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioEmail() {
        consultarPessoaPorEmail(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioTelefone() {
        consultarPessoaPorTelefone(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioCargo() {
        String cargo = leitor.lerTexto("Informe o cargo do funcionário: ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.isAtivo()) {
                if (func.getCargo().toLowerCase().contains(cargo.toLowerCase())) {
                    System.out.println("ID Funcionário: " + func.getIdFuncionario());
                    //mostrarDadosPessoa(func);
                    existeRegistro = true;
                    System.out.println("==================================================================");
                }
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public boolean contratarFuncionario(
            Funcionario func,
            String cargo,
            double salario,
            LocalDate data,
            String login,
            String senha) {

        // Verifica se o login está disponível
        boolean loginOk = ControladorLogin.getInstanciaControladorLogin()
                .loginDisponivel(login);

        if (!loginOk) {
            return false;
        }

        // Contrata
        func.setAtivo(true);
        func.setCargo(cargo);
        func.setSalario(salario);
        func.setDataAdmissao(data);

        // Agora que o cargo existe, cadastra o acesso
        ControladorLogin.getInstanciaControladorLogin()
                .cadastrarAcesso(func, login, senha);

        return true;
    }

    public void alteraCargo(Funcionario func, String cargo) {
        func.setCargo(cargo);
        ControladorLogin.getInstanciaControladorLogin().alterarNivelAcesso(func);
    }

    public void alteraSalario(Funcionario func, double salario) {
        func.setSalario(salario);
    }

    public void desligaFuncionario(Funcionario func, LocalDate data) {
        func.setDataDemissao(data);
        func.setAtivo(false);
    }

    public boolean atualizarAcesso(Funcionario func, String login, String senha) {
        // Verifica se o login está disponível
        boolean loginOk = ControladorLogin.getInstanciaControladorLogin()
                .loginDisponivel(login);

        if (!loginOk) {
            return false;
        }
        
        ControladorLogin.getInstanciaControladorLogin()
                .alterarAcesso(func, login, senha);
        
        return true;
    }
}
