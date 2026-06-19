package entidades;

public class Login {
    private String loginAtual;
    private String nivelAcessoAtual;
    private static Login loginInstancia;

    private Login() {

    }

    public static Login getInstanciaLogin() {
        if (loginInstancia == null) {
            loginInstancia = new Login();
        }
        return loginInstancia;
    }

    public void setLoginAtual(String loginAtual) {
        this.loginAtual = loginAtual;
    }

    public void setNivelAcessoAtual(String nivelAcessoAtual) {
        this.nivelAcessoAtual = nivelAcessoAtual;
    }

    public String getLoginAtual() {
        return loginAtual;
    }

    public String getNivelAcessoAtual() {
        return nivelAcessoAtual;
    }
}

