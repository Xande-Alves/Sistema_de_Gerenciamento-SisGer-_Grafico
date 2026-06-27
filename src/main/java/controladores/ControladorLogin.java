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

    public boolean loginDisponivel(String login) {
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (Objects.equals(func.getLogin(), login)
                    || Objects.equals(login, "adm")) {
                return false;
            }
        }
        return true;
    }

    public void cadastrarAcesso(Funcionario func, String login, String senha) {

        func.setLogin(login);
        func.setSenha(senha);

        String acesso = switch (func.getCargo()) {
            case "Vendedor" ->
                "14";
            case "Gerente de Vendas" ->
                "1234";
            case "Estoquista" ->
                "35";
            case "Gerente de Estoque" ->
                "235";
            default ->
                "";
        };

        func.setNivelAcesso(acesso);
    }
    
    public void alterarNivelAcesso(Funcionario func) {
        String acesso = switch (func.getCargo()) {
            case "Vendedor" ->
                "14";
            case "Gerente de Vendas" ->
                "1234";
            case "Estoquista" ->
                "35";
            case "Gerente de Estoque" ->
                "235";
            default ->
                "";
        };
        func.setNivelAcesso(acesso);
    }

    public void alterarAcesso(Funcionario func, String login, String senha) {
        func.setLogin(login);
        func.setSenha(senha);
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

    public boolean verificaVendedorParaVenda(Funcionario vendedor) {
        if ((vendedor.getCargo().equals("Vendedor")) || (vendedor.getCargo().equals("Gerente de Vendas"))) {
            return true;
        }
        return false;
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
