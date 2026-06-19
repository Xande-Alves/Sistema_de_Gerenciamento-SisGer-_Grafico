package controladores;
import java.util.Scanner;

//import menus.MenuEntidade;
import repositorio.Repositorio;
import entidades.Cliente;
import utilitarios.LeitorConsole;


public class ControladorCliente extends ControladorPessoa{
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

    public int cadastrarClienteGrafico (String nome, String cpf, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        int idCliente = Repositorio.getInstanciaRepositorio().getListaClientes().size() + 1;

        Cliente c = new Cliente(idCliente);
        cadastrarPessoaGrafico(c, nome, cpf, email, telefone, logradouro, numero, bairro, cidade, estado, cep);

        repositorio.Repositorio.getInstanciaRepositorio().getListaClientes().add(c);
        
        return idCliente;
    }

    public void atualizarCliente() {
        System.out.println("==================ATUALIZAR CADASTRO DE CLIENTES==================");
        int idCliente = leitor.lerInteiro(
                "Informe o ID do cliente: "
        );
        boolean existeCliente = false;

        for (Cliente c : Repositorio.getInstanciaRepositorio().getListaClientes()) {
            if (c.getIdCliente() == idCliente) {
                System.out.println("CLIENTE ENCONTRADO:");
                System.out.println("ID: "+c.getIdCliente());
                mostrarDadosPessoa(c);
                System.out.println("==================================================================");

                alteraDadosPessoa(c);
                existeCliente = true;
                System.out.println("Cadastro atualizado com sucesso!");
            }
        }
        if (!existeCliente) {
            System.out.println("ID de cliente não existe.");
        }
        System.out.println("==================================================================");
    }

    public void listarClientes () {
        System.out.println("=========================LISTA DE CLIENTES=========================");
        for (Cliente c : Repositorio.getInstanciaRepositorio().getListaClientes()) {
            System.out.println("ID entidades.Cliente: " + c.getIdCliente());
            mostrarDadosPessoa(c);
            System.out.println("==================================================================");
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
