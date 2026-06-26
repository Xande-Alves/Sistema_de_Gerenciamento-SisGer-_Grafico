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

    public int cadastrarFornecedorGrafico(String nome, String cpf, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep, String razaoSocial, String cnpj) {
        int idFornecedor = Repositorio.getInstanciaRepositorio().getListaFornecedores().size() + 1;

        Fornecedor fornec = new Fornecedor(idFornecedor, razaoSocial, cnpj);

        cadastrarPessoaGrafico(fornec, nome, cpf, email, telefone, logradouro, numero, bairro, cidade, estado, cep);

        Repositorio.getInstanciaRepositorio().getListaFornecedores().add(fornec);

        return idFornecedor;
    }
    
    public boolean cpfJaCadastradoFornecedor(String cpf) {
        // Pega a lista atual de clientes
        java.util.List<entidades.Fornecedor> fornecedores = repositorio.Repositorio.getInstanciaRepositorio().getListaFornecedores();

        // Limpa pontos e traços do CPF que vem da tela para comparar apenas os números limpos
        String cpfNovoLimpo = cpf.replaceAll("[^0-9]", "");

        for (entidades.Fornecedor fornec : fornecedores) {
            String cpfExistenteLimpo = fornec.getCpf().replaceAll("[^0-9]", "");

            if (cpfExistenteLimpo.equals(cpfNovoLimpo)) {
                return true; // Encontrou um cliente com o mesmo CPF
            }
        }

        return false; // CPF está livre para cadastro
    }
    
    public boolean existeFornecedor (int idfornecedor) {
        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getIdFornecedor() == idfornecedor) {
                return true;
            }
        }
        
        return false;
    }
    
    public entidades.Fornecedor buscarFornecedorPorIdOuCpf(String tipoBusca, String termo) {
        java.util.List<entidades.Fornecedor> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaFornecedores();

        for (entidades.Fornecedor fornec : lista) {
            if (tipoBusca.equals("ID") && String.valueOf(fornec.getIdentificacao()).equals(termo.trim())) {
                return fornec;
            } else if (tipoBusca.equals("CPF")) {
                String cpfLista = fornec.getCpf().replaceAll("[^0-9]", "");
                String cpfBusca = termo.replaceAll("[^0-9]", "");
                if (cpfLista.equals(cpfBusca)) {
                    return fornec;
                }
            }
        }
        return null; // Não encontrou
    }

    public void atualizarFornecedorGrafico(int id, String nome, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep, String razaoSocial, String cnpj) {

        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getIdFornecedor()== id) {

                alteraDadosPessoaGrafico(fornec, nome, email, telefone, logradouro, numero, bairro, cidade, estado, cep);
                fornec.setRepresentaEmpresaNome(razaoSocial);
                fornec.setRepresentaEmpresaCnpj(cnpj);
            }
        }
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
                //mostrarDadosPessoa(fornec);
                System.out.println("Nome da empresa: "+fornec.getRepresentaEmpresaNome());
                System.out.println("CNPJ da empresa: "+fornec.getRepresentaEmpresaCnpj());
                System.out.println("==================================================================");

                //alteraDadosPessoa(fornec);
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
            //mostrarDadosPessoa(fornec);
            System.out.println("Nome da empresa: "+fornec.getRepresentaEmpresaNome());
            System.out.println("CNPJ da empresa: "+fornec.getRepresentaEmpresaCnpj());

            System.out.println("==================================================================");
        }
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
                //mostrarDadosPessoa(fornec);
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
                //mostrarDadosPessoa(fornec);
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
