package entidades;

import java.time.LocalDate;
import java.util.*;

public class Venda {

    private List<Produto> listaProdutosVenda = new ArrayList<>();
    private int idVenda;
    private int idVendedorVenda;
    private int idClienteVenda;
    private LocalDate dataVenda;
    private Double valorTotalVenda;
    private boolean vendaAtiva;

    public Venda(int idVenda, int idVendedor, int idCliente) {
        this.idVenda = idVenda;
        this.idVendedorVenda = idVendedor;
        this.idClienteVenda = idCliente;
    }

    public int getIdVenda() {
        return idVenda;
    }
    
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdVendedorVenda() {
        return idVendedorVenda;
    }

    public void setIdVendedorVenda(int idVendedorVenda) {
        this.idVendedorVenda = idVendedorVenda;
    }

    public int getIdClienteVenda() {
        return idClienteVenda;
    }

    public void setIdClienteVenda(int idClienteVenda) {
        this.idClienteVenda = idClienteVenda;
    }

    public void setValorTotalVenda(Double valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public Double getValorTotalVenda() {
        return valorTotalVenda;
    }

    public boolean getVendaAtiva() {
        return vendaAtiva;
    }

    public void setVendaAtiva(boolean vendaAtiva) {
        this.vendaAtiva = vendaAtiva;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<Produto> getListaProdutosVenda() {
        return listaProdutosVenda;
    }
    
    public void setListaProdutosVenda(List<Produto> listaProdutosVenda) {
        this.listaProdutosVenda = listaProdutosVenda;
    }
}
