package controladores;

import entidades.Fornecedor;
import entidades.Pessoa;
//import menus.MenuEntidade;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.util.Scanner;

public class ControladorFornecedor extends ControladorPessoa{
    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private static ControladorFornecedor controladorFornecedorInstancia;

    private ControladorFornecedor(){

    }

    public static ControladorFornecedor getInstanciaControladorFornecedor() {
        if (controladorFornecedorInstancia==null) {
            controladorFornecedorInstancia = new ControladorFornecedor();
        }
        return controladorFornecedorInstancia;
    }

    public void cadastrarFornecedor() {
        System.out.println("======================CADASTRO DE FORNECEDORES====================");
        int idFornecedor = Repositorio.getInstanciaRepositorio().getListaFornecedores().size() + 1;
        Fornecedor fornec = new Fornecedor(idFornecedor,"", "");
        //cadastrarPessoa(fornec);
        String nomeEmpresa = leitor.lerTexto("Insira o nome da empresa que o fornecedor representa: ");
        fornec.setRepresentaEmpresaNome(nomeEmpresa);

        String cnpjEmpresa = leitor.lerCnpj(
                "Insira o CNPJ da empresa que o fornecedor representa: "
        );
        fornec.setRepresentaEmpresaCnpj(cnpjEmpresa);

        int concluir = leitor.lerInteiro(
                "Concluir o procedimento? (1 para SIM): "
        );
        if (concluir != 1) {
            //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuProduto();
            return;
        }

        Repositorio.getInstanciaRepositorio().getListaFornecedores().add(fornec);
        System.out.println("Fornecedor cadastrado com sucesso!");
        System.out.println("==================================================================");
    }

    public void atualizarFornecedor() {
        System.out.println("==============ATUALIZAÇÃO DE CADASTRO DE FORNECEDORES=============");
        int idFornecedor = leitor.lerInteiro(
                "Informe o ID do fornecedor: "
        );
        boolean existeFornecedor = false;

        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getIdFornecedor() == idFornecedor) {
                System.out.println("ID: "+fornec.getIdFornecedor());
                mostrarDadosPessoa(fornec);
                System.out.println("Nome da empresa: "+fornec.getRepresentaEmpresaNome());
                System.out.println("CNPJ da empresa: "+fornec.getRepresentaEmpresaCnpj());
                System.out.println("==================================================================");

                alteraDadosPessoa(fornec);
                String novoNomeEmpresa = leitor.lerTexto("Informe o novo nome da empresa (enter para não alterar): ");
                if (!novoNomeEmpresa.isEmpty()) {
                    fornec.setRepresentaEmpresaNome(novoNomeEmpresa);
                }

                String cnpj = leitor.lerCnpjOpcional(
                        "Informe o novo CNPJ (enter para não alterar): "
                );
                if (cnpj != null) {
                    fornec.setRepresentaEmpresaCnpj(cnpj);
                }

                existeFornecedor = true;
                System.out.println("Cadastro atualizado com sucesso!");
            }
        }
        if (!existeFornecedor) {
            System.out.println("ID de fornecedor não existe.");
        }
        System.out.println("==================================================================");
    }

    public void listarFornecedores() {
        System.out.println("=======================LISTA DE FORNECEDORES======================");
        for(Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            System.out.println("ID fornecedor: "+fornec.getIdFornecedor());
            mostrarDadosPessoa(fornec);
            System.out.println("Nome da empresa: "+fornec.getRepresentaEmpresaNome());
            System.out.println("CNPJ da empresa: "+fornec.getRepresentaEmpresaCnpj());

            System.out.println("==================================================================");
        }
    }

    @Override
    public void mostrarDadosPessoa(Pessoa p) {
        super.mostrarDadosPessoa(p);
        Fornecedor f = (Fornecedor) p;
        System.out.println("Nome da empresa: " + f.getRepresentaEmpresaNome());
        System.out.println("CNPJ da empresa: " + f.getRepresentaEmpresaCnpj());
    }

    public void consultarFornecedorNome() { consultarPessoaPorNome(Repositorio.getInstanciaRepositorio().getListaFornecedores()); }

    public void consultarFornecedorCpf() {
        consultarPessoaPorCpf(Repositorio.getInstanciaRepositorio().getListaFornecedores());
    }

    public void consultarFornecedorEmail() {
        consultarPessoaPorEmail(Repositorio.getInstanciaRepositorio().getListaFornecedores());
    }

    public void consultarFornecedorTelefone() {
        consultarPessoaPorTelefone(Repositorio.getInstanciaRepositorio().getListaFornecedores());
    }

    public void consultarFornecedorNomeEmpresa() {
        String nomeEmpresa = leitor.lerTexto("Informe o nome da empresa do fornecedor: ");
        boolean existeFornecedor = false;
        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getRepresentaEmpresaNome().toLowerCase().contains(nomeEmpresa.toLowerCase())) {
                System.out.println("ID Fornecedor: " + fornec.getIdFornecedor());
                mostrarDadosPessoa(fornec);
                System.out.println("==================================================================");
                existeFornecedor = true;
            }
        }
        if (!existeFornecedor) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro de fornecedor para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarFornecedorCnpjEmpresa() {
        String cnpjEmpresa = leitor.lerTexto("Informe o CNPJ da empresa do fornecedor (apenas números): ");
        boolean existeFornecedor = false;
        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getRepresentaEmpresaCnpj().replaceAll("[^0-9]", "").contains(cnpjEmpresa)) {
                System.out.println("ID entidades.Fornecedor: " + fornec.getIdFornecedor());
                mostrarDadosPessoa(fornec);
                System.out.println("==================================================================");
                existeFornecedor = true;
            }
        }
        if (!existeFornecedor) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro de fornecedor para os dados informados.");
            System.out.println("==================================================================");
        }
    }
}
