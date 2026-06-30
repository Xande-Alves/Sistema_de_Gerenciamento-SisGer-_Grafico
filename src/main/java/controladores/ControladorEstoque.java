package controladores;

import entidades.Produto;
import entidades.Venda;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;
import java.util.Set;
import java.util.HashSet;

import java.util.Scanner;

public class ControladorEstoque {

    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    static ControladorEstoque controladorEstoqueInstancia;

    private ControladorEstoque() {

    }

    public static ControladorEstoque getInstanciaControladorEstoque() {
        if (controladorEstoqueInstancia == null) {
            controladorEstoqueInstancia = new ControladorEstoque();
        }
        return controladorEstoqueInstancia;
    }

//    public void diminuiQuantidadeEstoqueVenda(Venda v) {
//        for (Produto p : v.getListaProdutosVenda()) {
//            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - p.getQuantidade());
//        }
//    }

    public void diminuiQuantidadeEstoqueVenda(Venda venda) {

        for (Produto pVenda : venda.getListaProdutosVenda()) {

            Produto pRepositorio
                    = ControladorProduto.getInstanciaControladorProduto()
                            .buscarProdutos(pVenda.getIdProduto());

            pRepositorio.setQuantidadeEstoque(
                    pRepositorio.getQuantidadeEstoque()
                    - pVenda.getQuantidade()
            );

            // Atualiza também a cópia da venda
            pVenda.setQuantidadeEstoque(
                    pRepositorio.getQuantidadeEstoque()
            );
        }
    }

    public String alteraDiminuiQuantidadeEstoqueVenda(Produto p, Double alteraEstoque) {
        if (p.getQuantidadeEstoque() < Math.abs(alteraEstoque)) {
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - Math.abs(alteraEstoque));
            return "Quantidade vendida é superior a quantidade estocada. Faltam: " + Math.abs(p.getQuantidadeEstoque());
        } else {
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - Math.abs(alteraEstoque));
            return "";
        }
    }

//    public void alteraQuantidadeEstoqueVenda(Venda vendaAtual, Venda vendaOriginal) {
//        double diferencaQuantidade = 0.0;
//        for (Produto pAtual : vendaAtual.getListaProdutosVenda()) {
//            for (Produto pOriginal : vendaOriginal.getListaProdutosVenda()) {
//                if (pAtual.getIdProduto() == pOriginal.getIdProduto()) {
//                    if (pAtual.getQuantidade() > pOriginal.getQuantidade()) {
//                        diferencaQuantidade = 0.0;
//                        diferencaQuantidade = pAtual.getQuantidade() - pOriginal.getQuantidade();
//                        pOriginal.setQuantidadeEstoque(pOriginal.getQuantidadeEstoque() - diferencaQuantidade);
//                    } else if (pAtual.getQuantidade() < pOriginal.getQuantidade()) {
//                        diferencaQuantidade = 0.0;
//                        diferencaQuantidade = pOriginal.getQuantidade() - pAtual.getQuantidade();
//                        pOriginal.setQuantidadeEstoque(pOriginal.getQuantidadeEstoque() + diferencaQuantidade);
//                    }
//                }
//            }
//        }
//    }
    public void alteraQuantidadeEstoqueVenda(Venda vendaAtual, Venda vendaOriginal) {

        // Junta todos os IDs de produtos existentes nas duas vendas
        Set<Integer> idsProdutos = new HashSet<>();

        for (Produto p : vendaOriginal.getListaProdutosVenda()) {
            idsProdutos.add(p.getIdProduto());
        }

        for (Produto p : vendaAtual.getListaProdutosVenda()) {
            idsProdutos.add(p.getIdProduto());
        }

        // Percorre todos os produtos envolvidos
        for (Integer id : idsProdutos) {

            Produto produtoOriginalVenda = null;
            Produto produtoAtualVenda = null;

            // Procura na venda original
            for (Produto p : vendaOriginal.getListaProdutosVenda()) {
                if (p.getIdProduto() == id) {
                    produtoOriginalVenda = p;
                    break;
                }
            }

            // Procura na venda atual
            for (Produto p : vendaAtual.getListaProdutosVenda()) {
                if (p.getIdProduto() == id) {
                    produtoAtualVenda = p;
                    break;
                }
            }

            double quantidadeOriginal = (produtoOriginalVenda == null)
                    ? 0
                    : produtoOriginalVenda.getQuantidade();

            double quantidadeAtual = (produtoAtualVenda == null)
                    ? 0
                    : produtoAtualVenda.getQuantidade();

            // Produto verdadeiro do repositório
            Produto produtoRepositorio
                    = ControladorProduto.getInstanciaControladorProduto()
                            .buscarProdutos(id);

            double novoEstoque
                    = produtoRepositorio.getQuantidadeEstoque()
                    + quantidadeOriginal
                    - quantidadeAtual;

            produtoRepositorio.setQuantidadeEstoque(novoEstoque);
        }
    }

    public void alteraEstoqueProduto(Produto p, double alteraEstoque) {

        p.setQuantidadeEstoque(p.getQuantidadeEstoque() + alteraEstoque);
    }

    public void diminuiProduto() {
        System.out.println("=========================DIMINUIR ESTOQUE=========================");
        int idProduto = leitor.lerInteiro(
                "Informe o ID do produto: "
        );

        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getIdProduto() == idProduto) {
                ControladorProduto.getInstanciaControladorProduto().mostrarProduto(p);
                existeProduto = true;
                double diminuiEstoque = leitor.lerDouble(
                        "Diminuir a quantidade do estoque do produto em quanto? "
                );
                p.setQuantidadeEstoque(p.getQuantidadeEstoque() - diminuiEstoque);
                System.out.println("Estoque do produto diminuído em " + diminuiEstoque + ", totalizando " + p.getQuantidadeEstoque() + ".");
            }
        }
        if (!existeProduto) {
            System.out.println("ID de produto não existe.");
        }
    }

    public void avisosEstoque() {
        System.out.println("=========================AVISOS DE ESTOQUE========================");
        double quantidadeProduto = leitor.lerDouble(
                "Qual a quantidade de produto a ser pesquisada? "
        );
        boolean existeProduto = false;
        for (Produto p : Repositorio.getInstanciaRepositorio().getListaProduto()) {
            if (p.getQuantidadeEstoque() <= quantidadeProduto) {
                ControladorProduto.getInstanciaControladorProduto().mostrarProduto(p);
                existeProduto = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeProduto) {
            System.out.println("Não existe produtos com estoque abaixo da quantidade informada.");
        }
    }
}
