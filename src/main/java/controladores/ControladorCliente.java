package controladores;

import java.util.Scanner;

//import menus.MenuEntidade;
import repositorio.Repositorio;
import entidades.Cliente;
import utilitarios.LeitorConsole;

public class ControladorCliente extends ControladorPessoa {

    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private static ControladorCliente ControladorClienteInstancia;

    private ControladorCliente() {

    }

    public static ControladorCliente getInstanciaControladorCliente() {
        if (ControladorClienteInstancia == null) {
            ControladorClienteInstancia = new ControladorCliente();
        }
        return ControladorClienteInstancia;
    }

    public int cadastrarClienteGrafico(String nome, String cpf, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        int idCliente = Repositorio.getInstanciaRepositorio().getListaClientes().size() + 1;

        Cliente c = new Cliente(idCliente);
        cadastrarPessoaGrafico(c, nome, cpf, email, telefone, logradouro, numero, bairro, cidade, estado, cep);

        repositorio.Repositorio.getInstanciaRepositorio().getListaClientes().add(c);

        return idCliente;
    }

    public boolean cpfJaCadastradoCliente(String cpf) {
        // Pega a lista atual de clientes
        java.util.List<entidades.Cliente> clientes = repositorio.Repositorio.getInstanciaRepositorio().getListaClientes();

        // Limpa pontos e traços do CPF que vem da tela para comparar apenas os números limpos
        String cpfNovoLimpo = cpf.replaceAll("[^0-9]", "");

        for (entidades.Cliente c : clientes) {
            String cpfExistenteLimpo = c.getCpf().replaceAll("[^0-9]", "");

            if (cpfExistenteLimpo.equals(cpfNovoLimpo)) {
                return true; // Encontrou um cliente com o mesmo CPF
            }
        }

        return false; // CPF está livre para cadastro
    }

    public entidades.Cliente buscarClientePorIdOuCpf(String tipoBusca, String termo) {
        java.util.List<entidades.Cliente> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaClientes();

        for (entidades.Cliente c : lista) {
            if (tipoBusca.equals("ID") && String.valueOf(c.getIdentificacao()).equals(termo.trim())) {
                return c;
            } else if (tipoBusca.equals("CPF")) {
                String cpfLista = c.getCpf().replaceAll("[^0-9]", "");
                String cpfBusca = termo.replaceAll("[^0-9]", "");
                if (cpfLista.equals(cpfBusca)) {
                    return c;
                }
            }
        }
        return null; // Não encontrou
    }

//    public void atualizarClienteGrafico(int id, String nome, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
//        java.util.List<entidades.Cliente> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaClientes();
//
//        for (entidades.Cliente c : lista) {
//            if (Integer.parseInt(c.getIdentificacao())== id) {
//                c.setNome(nome);
//                c.setEmail(email);
//                c.setTelefone(telefone);
//                c.getEndereco().setLogradouro(logradouro);
//                c.getEndereco().setNumero(numero);
//                c.getEndereco().setBairro(bairro);
//                c.getEndereco().setCidade(cidade);
//                c.getEndereco().setEstado(estado);
//                c.getEndereco().setCep(cep);
//                break;
//            }
//        }
//    }

    public void atualizarClienteGrafico(int id, String nome, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {

        for (Cliente c : Repositorio.getInstanciaRepositorio().getListaClientes()) {
            if (c.getIdCliente() == id) {
                alteraDadosPessoaGrafico(c, nome, email, telefone, logradouro, numero, bairro, cidade, estado, cep);
            }
        }
    }

    public void consultarClientesNome() {
        consultarPessoaPorNome(Repositorio.getInstanciaRepositorio().getListaClientes());
    }

    public void consultarClientesCpf() {
        consultarPessoaPorCpf(Repositorio.getInstanciaRepositorio().getListaClientes());
    }

    public void consultarClientesEmail() {
        consultarPessoaPorEmail(Repositorio.getInstanciaRepositorio().getListaClientes());
    }

    public void consultarClientesTelefone() {
        consultarPessoaPorTelefone(repositorio.Repositorio.getInstanciaRepositorio().getListaClientes());
    }

}
