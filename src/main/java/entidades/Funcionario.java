package entidades;

import java.time.LocalDate;

public class Funcionario extends Pessoa {

    private final int idFuncionario;
    private Double salario;
    private Double comissaoVendedor;
    private boolean ativo;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private String cargo;
    private String login;
    private String senha;
    private String nivelAcesso;

    public Funcionario(int idFuncionario, boolean ativo) {
        this.idFuncionario = idFuncionario;
        this.ativo = ativo;
    }

    @Override
    public String getIdentificacao() {
        return "" + getIdFuncionario();
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public void setComissaoVendedor(Double comissaoVendedor) {
        this.comissaoVendedor = comissaoVendedor;
    }

    public Double getcomissaoVendedor() {
        return comissaoVendedor;
    }
}
