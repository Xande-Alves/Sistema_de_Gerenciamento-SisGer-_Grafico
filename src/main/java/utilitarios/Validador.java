package utilitarios;

public final class Validador {

    private Validador() {
    }

    public static boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        return cpf.matches("\\d{11}");
    }

    public static boolean cnpjValido(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        cnpj = cnpj.replaceAll("[^0-9]", "");

        return cnpj.matches("\\d{14}");
    }

    public static boolean telefoneValido(String telefone) {
        if (telefone == null) {
            return false;
        }

        telefone = telefone.replaceAll("[^0-9]", "");

        return telefone.matches("\\d{11}");
    }

    public static boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    public static boolean cepValido(String cep) {
        if (cep == null) {
            return false;
        }

        cep = cep.replaceAll("[^0-9]", "");

        return cep.matches("\\d{8}");
    }
}