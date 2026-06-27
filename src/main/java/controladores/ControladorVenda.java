package controladores;

import entidades.*;
//import menus.MenuEntidade;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ControladorVenda {

    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static ControladorVenda controladorVendaInstancia;

    private ControladorVenda() {

    }

    public static ControladorVenda getInstanciaControladorVenda() {
        if (controladorVendaInstancia == null) {
            controladorVendaInstancia = new ControladorVenda();
        }
        return controladorVendaInstancia;
    }

    public int efetuarVenda(Venda vendaAtual, int idCliente, int idVendedor, List<Produto> listaProdutosVenda) {
        int idvendaAtual = Repositorio.getInstanciaRepositorio().getListaVenda().size() + 1;
        vendaAtual.setIdVenda(idvendaAtual);
        LocalDate dataAtual = LocalDate.now();
        vendaAtual.setDataVenda(dataAtual);
        vendaAtual.setIdVendedorVenda(idVendedor);
        vendaAtual.setIdClienteVenda(idCliente);
        vendaAtual.setListaProdutosVenda(listaProdutosVenda);

        Double valorTotalVenda = calcularValorTotalVenda(vendaAtual);
        vendaAtual.setValorTotalVenda(valorTotalVenda);
        vendaAtual.setVendaAtiva(true);

        Repositorio.getInstanciaRepositorio().getListaVenda().add(vendaAtual);
        comissaoVendedor();

        return idvendaAtual;
    }

    public void atualizarVenda(Venda vendaAtual,
            int idCliente,
            int idVendedor,
            List<Produto> listaProdutosVenda,
            double valorTotalVenda) {

        Venda vendaRepositorio = buscarVendaRepositorio(vendaAtual.getIdVenda());

        if (vendaRepositorio == null) {
            return;
        }

        vendaRepositorio.setIdClienteVenda(idCliente);
        vendaRepositorio.setIdVendedorVenda(idVendedor);
        vendaRepositorio.setValorTotalVenda(valorTotalVenda);
        vendaRepositorio.setDataVenda(vendaAtual.getDataVenda());
        vendaRepositorio.setVendaAtiva(vendaAtual.getVendaAtiva());

        List<Produto> novaLista = new ArrayList<>();

        for (Produto p : listaProdutosVenda) {

            Produto copia = new Produto(
                    p.getIdProduto(),
                    p.getIdFornecedor(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getPrecoCompra(),
                    p.getPrecoVenda(),
                    p.getQuantidadeEstoque()
            );

            copia.setQuantidade(p.getQuantidade());

            novaLista.add(copia);
        }

        vendaRepositorio.setListaProdutosVenda(novaLista);

        comissaoVendedor();
    }

    public Venda buscarVendaRepositorio(int idVenda) {
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdVenda() == idVenda) {
                return v;
            }
        }
        return null;
    }

//    public void atualizarVenda(Venda vendaAtual, int idCliente, int idVendedor, List<Produto> listaProdutosVenda, double valorTotalVenda) {
//
//         Verificar permissão
//        int idVendedorLogado = ControladorLogin.getInstanciaControladorLogin().verificaVendedorParaVenda();
//        boolean ehVendedorDaVenda =
//                //vendaEncontrada.getIdVendedorVenda() == idVendedorLogado;
//
//        boolean ehGerente =
//                Objects.equals(
//                        ControladorLogin.getInstanciaControladorLogin().verificaGerenteDeVendasParaVenda().toLowerCase(),
//                        "gerente de vendas"
//                );
//
//        if (!ehVendedorDaVenda && !ehGerente) {
//            System.out.println("Usuário não efetuou a venda e não possui cargo de Gerente de Vendas.");
//            //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuVendas();
//            return;
//        }
//        vendaAtual.setIdVendedorVenda(idVendedor);
//        vendaAtual.setIdClienteVenda(idCliente);
//        vendaAtual.setListaProdutosVenda(listaProdutosVenda);
//        vendaAtual.setValorTotalVenda(valorTotalVenda);
//
//        comissaoVendedor();
//    }
    
    public Venda buscarVendaCopia(int idVenda) {
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {

            if (v.getIdVenda() == idVenda) {

                Venda copia = new Venda(
                        v.getIdVenda(),
                        v.getIdClienteVenda(),
                        v.getIdVendedorVenda()
                );
                copia.setValorTotalVenda(v.getValorTotalVenda());
                copia.setDataVenda(v.getDataVenda());
                copia.setVendaAtiva(v.getVendaAtiva());

                for (Produto p : v.getListaProdutosVenda()) {

                    Produto produtoCopia = new Produto(
                            p.getIdProduto(),
                            p.getIdFornecedor(),
                            p.getNome(),
                            p.getDescricao(),
                            p.getPrecoCompra(),
                            p.getPrecoVenda(),
                            p.getQuantidadeEstoque()
                    );

                    produtoCopia.setQuantidade(p.getQuantidade());

                    copia.getListaProdutosVenda().add(produtoCopia);
                }

                return copia;
            }
        }

        return null;
    }

    public void consultarVendaPorID() {
        int idVenda = leitor.lerInteiro(
                "Informe o ID da venda: "
        );
        boolean existeVenda = false;
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdVenda() == idVenda) {
                mostrarVenda(v);
                existeVenda = true;
            }
        }
        if (!existeVenda) {
            System.out.println("ID de venda inexistente.");
        }
        System.out.println("==================================================================");
    }

    public void consultarVendaAtivaVendedor() {
        int idVendedor = leitor.lerInteiro(
                "Informe o ID do vendedor: "
        );
        boolean existeVenda = false;
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdVendedorVenda() == idVendedor && v.getVendaAtiva()) {
                mostrarVenda(v);
                existeVenda = true;
            }
        }
        if (!existeVenda) {
            System.out.println("Não existem vendas ativas do vendedor informado.");
        }
        System.out.println("==================================================================");
    }

    public void consultarVendaInativaVendedor() {
        int idVendedor = leitor.lerInteiro(
                "Informe o ID do vendedor: "
        );
        boolean existeVenda = false;
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdVendedorVenda() == idVendedor && !v.getVendaAtiva()) {
                mostrarVenda(v);
                existeVenda = true;
            }
        }
        if (!existeVenda) {
            System.out.println("Não existem vendas inativas do vendedor informado.");
        }
        System.out.println("==================================================================");
    }

    public void consultarVendaAtivaCliente() {
        int idCliente = leitor.lerInteiro(
                "Informe o ID do cliente: "
        );
        boolean existeVenda = false;
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdClienteVenda() == idCliente && v.getVendaAtiva()) {
                mostrarVenda(v);
                existeVenda = true;
            }
        }
        if (!existeVenda) {
            System.out.println("Não existem vendas ativas do cliente informado.");
        }
        System.out.println("==================================================================");
    }

    public void consultarVendaInativaCliente() {
        int idCliente = leitor.lerInteiro(
                "Informe o ID do cliente: "
        );
        boolean existeVenda = false;
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdClienteVenda() == idCliente && !v.getVendaAtiva()) {
                mostrarVenda(v);
                existeVenda = true;
            }
        }
        if (!existeVenda) {
            System.out.println("Não existem vendas inativas do cliente informado.");
        }
        System.out.println("==================================================================");
    }

    public void cancelarVenda(int idVenda) {
        
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            if (v.getIdVenda() == idVenda && v.getVendaAtiva()) {
                v.setVendaAtiva(false);
                comissaoVendedor();
            }
        }
    }

    public void listarVendas() {
        System.out.println("==========================LISTA DE VENDAS=========================");
        for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {
            mostrarVenda(v);
            System.out.println("==================================================================");
        }
    }

    public void mostrarProdutoVenda(Produto p) {
        System.out.println("ID do produto: " + p.getIdProduto());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Descrição: " + p.getDescricao());
        System.out.println("Preço de venda: R$ " + p.getPrecoVenda());
        System.out.println("Quantidade em estoque: " + p.getQuantidadeEstoque());
    }

    public void mostrarVenda(Venda v) {
        System.out.println("ID venda: " + v.getIdVenda());
        System.out.print("Situação: ");
        if (v.getVendaAtiva()) {
            System.out.println("Ativa");
        } else {
            System.out.println("Inativa");
        }
        System.out.print("ID vendedor: " + v.getIdVendedorVenda() + " - ");
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (v.getIdVendedorVenda() == func.getIdFuncionario()) {
                System.out.println(func.getNome());
                break;
            }
        }
        System.out.print("ID Cliente: " + v.getIdClienteVenda() + " - ");
        for (Cliente cli : Repositorio.getInstanciaRepositorio().getListaClientes()) {
            if (v.getIdClienteVenda() == cli.getIdCliente()) {
                System.out.println(cli.getNome());
                break;
            }
        }
        System.out.println("Data da venda: " + v.getDataVenda().format(formatador));
        System.out.printf("Valor total da venda: R$ %.2f%n", calcularValorTotalVenda(v));
        System.out.println("Itens vendidos:");
        for (Produto p : v.getListaProdutosVenda()) {
            mostrarProdutoVenda(p);
            System.out.println("Quantidade: " + p.getQuantidade());
            System.out.printf("Valor parcial do item: R$ %.2f%n", (p.getQuantidade() * p.getPrecoVenda()));
            System.out.println("==================================================================");
        }
    }

    public Double calcularValorTotalVenda(Venda v) {
        double valorTotal = 0.0;
        for (Produto p : v.getListaProdutosVenda()) {
            valorTotal += p.getPrecoVenda() * p.getQuantidade();
        }
        return valorTotal;
    }

    public void comissaoVendedor() {

        LocalDate dataAtual = LocalDate.now();

        LocalDate primeiroDia = dataAtual.withDayOfMonth(1);
        LocalDate ultimoDia = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth());

        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {

            if (func.getCargo().equalsIgnoreCase("vendedor")) {

                double acumulado = 0.0;

                for (Venda v : Repositorio.getInstanciaRepositorio().getListaVenda()) {

                    if (v.getVendaAtiva()
                            && !v.getDataVenda().isBefore(primeiroDia)
                            && !v.getDataVenda().isAfter(ultimoDia)
                            && v.getIdVendedorVenda() == func.getIdFuncionario()) {

                        acumulado += v.getValorTotalVenda();
                    }
                }

                // comissão de 1%
                double comissao = acumulado * 0.01;
                func.setComissaoVendedor(comissao);
            }
        }
    }
}
