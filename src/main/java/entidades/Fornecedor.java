package entidades;

public class Fornecedor extends Pessoa {

    private final int idFornecedor;
    private String representaEmpresaNome;
    private String representaEmpresaCnpj;

    public Fornecedor (int idFornecedor, String nomeEmpresa, String cnpjEmpresa) {
        this.representaEmpresaNome = nomeEmpresa;
        this.representaEmpresaCnpj = cnpjEmpresa;
        this.idFornecedor = idFornecedor;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    @Override
    public String getIdentificacao() {
        return "" + getIdFornecedor();
    }

    public String getRepresentaEmpresaNome() {
        return representaEmpresaNome;
    }

    public void setRepresentaEmpresaNome(String representaEmpresaNome) {
        this.representaEmpresaNome = representaEmpresaNome;
    }

    public String getRepresentaEmpresaCnpj() {
        return representaEmpresaCnpj;
    }

    public void setRepresentaEmpresaCnpj(String representaEmpresaCnpj) {
        this.representaEmpresaCnpj = representaEmpresaCnpj;
    }
}
