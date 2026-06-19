package repositorio;
import entidades.*;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private final List<Cliente> listaClientes = new ArrayList<>();
    private final List<Funcionario> listaFuncionarios = new ArrayList<>();
    private final List<Fornecedor> listaFornecedores = new ArrayList<>();
    private final List<Produto> listaProduto = new ArrayList<>();
    private final List<Venda> listaVenda = new ArrayList<>();
    private static Repositorio instanciaRepositorio;

    private Repositorio() {

    }

    public static Repositorio getInstanciaRepositorio() {
        if (instanciaRepositorio == null) {
            instanciaRepositorio = new Repositorio();
        }
        return instanciaRepositorio;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public List<Fornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public List<Venda> getListaVenda() {
        return listaVenda;
    }
}
