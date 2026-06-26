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

    public int cadastrarProduto(int idFornecedor, String nome, String descricao, double precoCompra, double quantidadeEstoque) {
        int idProduto = Repositorio.getInstanciaRepositorio().getListaProduto().size() + 1;
        
        Double precoVenda = calculaPrecoVenda(precoCompra);

        Produto produto = new Produto(idProduto,idFornecedor,nome,descricao,precoCompra,precoVenda,quantidadeEstoque);

        Repositorio.getInstanciaRepositorio().getListaProduto().add(produto);
        
        return idProduto;
    }

    public void atualizarProduto(int idProduto, int idFornecedor, String nome, String descricao, double precoCompra, double quantidadeEstoque) {
        
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getIdProduto() == idProduto) {
                p.setIdFornecedor(idFornecedor);
                p.setNome(nome);
                p.setDescricao(descricao);
                p.setPrecoCompra(precoCompra);
                double precoVenda = calculaPrecoVenda(precoCompra);
                p.setPrecoVenda(precoVenda);
                p.setQuantidadeEstoque(quantidadeEstoque);
            }
        }
    }
    
    public Produto buscarProdutos (int idProduto) {
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getIdProduto() == idProduto) {
                return p;
            }
        }
        
        return null;
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
