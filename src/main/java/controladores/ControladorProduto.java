package controladores;

import entidades.Fornecedor;
import entidades.Produto;
//import menus.MenuEntidade;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.util.Scanner;

public class ControladorProduto {
    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private static ControladorProduto controladorProdutoInstancia;

    private ControladorProduto() {

    }

    public static ControladorProduto getInstanciaControladorProduto() {
        if (controladorProdutoInstancia == null) {
            controladorProdutoInstancia = new ControladorProduto();
        }
        return controladorProdutoInstancia;
    }

    public void cadastrarProduto() {
        System.out.println("=======================CADASTRO DE PRODUTOS=======================");
        int idProduto = Repositorio.getInstanciaRepositorio().getListaProduto().size() + 1;
        boolean existeFornecedor = false;

        int idFornecedor = leitor.lerInteiro(
                "Informe o ID do fornecedor: "
        );
        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (fornec.getIdFornecedor() == idFornecedor) {
                existeFornecedor = true;
                break;
            }
        }
        if (!existeFornecedor) {
            System.out.println("ID de fornecedor inexistente.");
            //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuProduto();
        }
        String nome = leitor.lerTexto("Informe o nome do produto: ");
        String descricao = leitor.lerTexto("Informe a descrição do produto: ");
        double precoCompra = leitor.lerDouble(
                "Informe o preço de compra do produto: "
        );

        double quantidadeEstoque = leitor.lerDouble(
                "Informe a quantidade de estoque: "
        );
        Double precoVenda = calculaPrecoVenda(precoCompra);

        Produto produto = new Produto(idProduto,idFornecedor,nome,descricao,precoCompra,precoVenda,quantidadeEstoque);

        int concluir = leitor.lerInteiro(
                "Concluir o procedimento? (1 para SIM): "
        );
        if (concluir != 1) {
            //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuProduto();
            return;
        }

        Repositorio.getInstanciaRepositorio().getListaProduto().add(produto);
        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("==================================================================");
    }

    public void atualizarProduto() {
        System.out.println("===================ATUALIZAR CADASTRO DE PRODUTO==================");
        int idProduto = leitor.lerInteiro(
                "Informe o ID do produto: "
        );
        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getIdProduto() == idProduto) {
                mostrarProduto(p);
                System.out.println("==================================================================");

                int idFornecedorInt;
                Integer idFornecedor = leitor.lerInteiroOpcional(
                        "Informe o novo ID do fornecedor (enter para não alterar): "
                );

                if (idFornecedor != null) {
                    idFornecedorInt = idFornecedor;
                } else {
                    idFornecedorInt = p.getIdFornecedor();
                }

                boolean existeFornecedor = false;
                for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
                    if (idFornecedor == null || (fornec.getIdFornecedor() == idFornecedorInt)) {
                        existeFornecedor = true;
                        break;
                    }
                }
                if (!existeFornecedor) {
                    System.out.println("ID de fornecedor inexistente.");
                    //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuProduto();
                    return;
                }
                if (idFornecedor != null) {
                    p.setIdFornecedor(idFornecedorInt);
                }
                String nome = leitor.lerTextoOpcional("Informe o novo nome do produto (enter para não alterar): ");
                if (!nome.isEmpty()) {
                    p.setNome(nome);
                }
                String descricao = leitor.lerTextoOpcional("Informe a nova descrição do produto (enter para não alterar): ");
                if (!descricao.isEmpty()) {
                    p.setDescricao(descricao);
                }

                Double precoCompra = leitor.lerDoubleOpcional(
                        "Informe o novo preço de compra do produto (enter para não alterar): "
                );
                if (precoCompra != null) {
                    p.setPrecoCompra(precoCompra);
                    double precoVenda = calculaPrecoVenda(precoCompra);
                    p.setPrecoVenda(precoVenda);
                }

                existeProduto = true;
                System.out.println("Cadastro atualizado com sucesso!");
            }
        }
        if (!existeProduto) {
            System.out.println("ID de produto não existe.");
        }
        System.out.println("==================================================================");
    }

    public void listarProdutos() {
        System.out.println("=========================LISTA DE PRODUTOS========================");
        for(Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            mostrarProduto(p);
            System.out.println("==================================================================");
        }
    }

    public void consultarProdutoIdFornecedor() {
        int idFornec = leitor.lerInteiro(
                "Informe o ID do fornecedor: "
        );
        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getIdFornecedor() == idFornec) {
                mostrarProduto(p);
                existeProduto = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeProduto) {
            System.out.println("==================================================================");
            System.out.println("Não existe produto para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarProdutoNome() {
        String nomeProduto = leitor.lerTexto("Informe o nome do produto: ");
        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getNome().toLowerCase().contains(nomeProduto.toLowerCase())) {
                mostrarProduto(p);
                existeProduto = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeProduto) {
            System.out.println("==================================================================");
            System.out.println("Não existe produto para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarProdutoDescricao() {
        String descricaoProduto = leitor.lerTexto("Informe parte da descrição do produto: ");
        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getDescricao().toLowerCase().contains(descricaoProduto.toLowerCase())) {
                mostrarProduto(p);
                existeProduto = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeProduto) {
            System.out.println("==================================================================");
            System.out.println("Não existe produto para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void mostrarProduto(Produto p) {
        System.out.println("ID Produto: " + p.getIdProduto());
        System.out.print("ID Fornecedor: " + p.getIdFornecedor()+" - ");
        for (Fornecedor fornec : Repositorio.getInstanciaRepositorio().getListaFornecedores()) {
            if (p.getIdFornecedor() == fornec.getIdFornecedor()) {
                System.out.println(fornec.getNome()+", da empresa "+fornec.getRepresentaEmpresaNome());
                break;
            }
        }
        System.out.println("Nome: " + p.getNome());
        System.out.println("Descrição: " + p.getDescricao());
        System.out.printf("Preço de compra: R$ %.2f%n", p.getPrecoCompra());
        System.out.printf("Preço de venda: R$ %.2f%n", p.getPrecoVenda());
        System.out.println("Quantidade em estoque: "+p.getQuantidadeEstoque());
    }

    public Double calculaPrecoVenda (Double precoCompra) {
        return precoCompra*120/100;
    }

}
