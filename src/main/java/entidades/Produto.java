package entidades;

public class Produto {

    private final int idProduto;
    private int idFornecedor;
    private String nome;
    private String descricao;
    private Double precoVenda;
    private Double precoCompra;
    private Double quantidade;
    private Double quantidadeEstoque;

    public Produto(int idProduto, int idFornecedor, String nome, String descricao, Double precoCompra, Double precoVenda, Double quantidadeEstoque) {
        this.idProduto = idProduto;
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.descricao = descricao;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPrecoCompra(Double preco) {
        this.precoCompra = preco;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double preco) {
        this.precoVenda = preco;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdProduto() {
        return idProduto;
    }
}
