package controladores;

import entidades.Funcionario;
import entidades.Login;
//import menus.MenuControleAcesso;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ControladorLogin {

    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private static ControladorLogin controladorLoginInstancia;

    private ControladorLogin() {

    }

    public static ControladorLogin getInstanciaControladorLogin() {
        if (controladorLoginInstancia == null) {
            controladorLoginInstancia = new ControladorLogin();
        }
        return controladorLoginInstancia;
    }

    public void cadastrarAcesso(Funcionario func, List<Funcionario> listaFunc) {
        while (true) {
            boolean aceite = true;
            String login = leitor.lerTexto("Digite o login do funcionário: ");
            for (Funcionario f : listaFunc) {
                if (Objects.equals(f.getLogin(), login) || Objects.equals(login, "adm")) {
                    System.out.println("Login já está em uso. Digite outro login.");
                    aceite = false;
                    break;
                }
            }
            if (aceite) {
                func.setLogin(login);
                break;
            }
        }
        String senha = leitor.lerTexto("Digite a senha do funcionário: ");
        func.setSenha(senha);
        String acesso = switch (func.getCargo()) {
            case "vendedor" ->
                "14";
            case "gerente de vendas" ->
                "1234";
            case "estoquista" ->
                "35";
            case "gerente de estoque" ->
                "235";
            case null, default ->
                "";
        };
        func.setNivelAcesso(acesso);
    }

    public void alterarAcesso(Funcionario func, List<Funcionario> listaFunc) {
        while (true) {
            boolean aceite = true;
            String novoLogin = leitor.lerTexto("Digite o novo login do funcionário: ");
            for (Funcionario f : listaFunc) {
                if (Objects.equals(f.getLogin(), novoLogin) || Objects.equals(novoLogin, "adm")) {
                    System.out.println("Login já está em uso. Digite outro login.");
                    aceite = false;
                }
            }
            if (aceite) {
                func.setLogin(novoLogin);
                break;
            }
        }
        String novaSenha = leitor.lerTexto("Qual a nova senha do funcionário? ");
        func.setSenha(novaSenha);
        String novoAcesso = switch (func.getCargo()) {
            case "vendedor" ->
                "14";
            case "gerente de vendas" ->
                "1234";
            case "estoquista" ->
                "35";
            case "gerente de estoque" ->
                "235";
            case null, default ->
                "";
        };
        func.setNivelAcesso(novoAcesso);
    }

    public void mostrarAcesso(Funcionario func) {
        System.out.println("Login: " + func.getLogin());
        System.out.println("Senha: " + func.getSenha());
        System.out.println("Nível de acesso: " + func.getNivelAcesso());
    }

    // Modifique o método para receber login, senha e a lista de funcionários
    public boolean efetuarLoginGrafico(String entradaLogin, String entradaSenha, List<Funcionario> listaFunc) {

        // Validação do Administrador
        if (Objects.equals("adm", entradaLogin)) {
            if (Objects.equals("123", entradaSenha)) {
                Login.getInstanciaLogin().setLoginAtual("adm");
                Login.getInstanciaLogin().setNivelAcessoAtual("12345");
                return true; // Login com sucesso
            } else {
                return false; // Senha incorreta
            }
        }

        // Validação dos Funcionários
        for (Funcionario f : listaFunc) {
            if (Objects.equals(f.getLogin(), entradaLogin)) {
                if (Objects.equals(f.getSenha(), entradaSenha)) {
                    Login.getInstanciaLogin().setLoginAtual(f.getLogin());
                    Login.getInstanciaLogin().setNivelAcessoAtual(f.getNivelAcesso());
                    return true; // Login com sucesso
                }
                return false; // Senha incorreta
            }
        }

        return false; // Login não encontrado
    }

    public boolean permitirAcesso(int modulo) {
        return Login.getInstanciaLogin().getNivelAcessoAtual().contains(String.valueOf(modulo));
    }

    public int verificaVendedorParaVenda() {
        for (Funcionario f : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (Objects.equals(f.getLogin(), Login.getInstanciaLogin().getLoginAtual()) && Objects.equals(f.getCargo().toLowerCase(), "vendedor")) {
                return f.getIdFuncionario();
            }
        }
        return -1;
    }

    public String verificaGerenteDeVendasParaVenda() {
        String cargo;
        for (Funcionario f : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (Objects.equals(f.getLogin(), Login.getInstanciaLogin().getLoginAtual())) {
                if (Objects.equals(f.getCargo().toLowerCase(), "gerente de vendas")) {
                    cargo = f.getCargo();
                    return cargo;
                }
            }
        }
        return "negativo";
    }
}
