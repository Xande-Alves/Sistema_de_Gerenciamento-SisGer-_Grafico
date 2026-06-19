package utilitarios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class LeitorConsole {

    private final Scanner scanner;
    private final DateTimeFormatter formatador =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LeitorConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros.");
            }
        }
    }

    public Integer lerInteiroOpcional(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);

                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    return null;
                }

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros.");
            }
        }
    }

    public Double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);

                return Double.parseDouble(
                        scanner.nextLine().replace(",", ".")
                );

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public Double lerDoubleOpcional(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);

                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    return null;
                }

                return Double.parseDouble(
                        entrada.replace(",", ".")
                );

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);

                return LocalDate.parse(
                        scanner.nextLine().trim(),
                        formatador
                );

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Informe uma data válida no formato dd/MM/aaaa."
                );
            }
        }
    }

    public String lerCpf(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cpf = scanner.nextLine();

            if (Validador.cpfValido(cpf)) {
                return Formatador.formatarCpf(cpf);
            }

            System.out.println("O CPF deve conter 11 números.");
        }
    }

    public String lerCpfOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cpf = scanner.nextLine().trim();

            if (cpf.isEmpty()) {
                return null;
            }

            if (Validador.cpfValido(cpf)) {
                return Formatador.formatarCpf(cpf);
            }

            System.out.println("O CPF deve conter 11 números.");
        }
    }

    public String lerCnpj(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cnpj = scanner.nextLine();

            if (Validador.cnpjValido(cnpj)) {
                return Formatador.formatarCnpj(cnpj);
            }

            System.out.println("O CNPJ deve conter 14 números.");
        }
    }

    public String lerCnpjOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cnpj = scanner.nextLine().trim();

            if (cnpj.isEmpty()) {
                return null;
            }

            if (Validador.cnpjValido(cnpj)) {
                return Formatador.formatarCnpj(cnpj);
            }

            System.out.println("O CNPJ deve conter 14 números.");
        }
    }

    public String lerTelefone(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String telefone = scanner.nextLine();

            if (Validador.telefoneValido(telefone)) {
                return Formatador.formatarTelefone(telefone);
            }

            System.out.println(
                    "Informe um telefone com 11 números (DDD + celular)."
            );
        }
    }

    public String lerTelefoneOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String telefone = scanner.nextLine().trim();

            if (telefone.isEmpty()) {
                return null;
            }

            if (Validador.telefoneValido(telefone)) {
                return Formatador.formatarTelefone(telefone);
            }

            System.out.println(
                    "Informe um telefone com 11 números (DDD + celular)."
            );
        }
    }

    public String lerEmail(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String email = scanner.nextLine().trim();

            if (Validador.emailValido(email)) {
                return email;
            }

            System.out.println("Formato de e-mail inválido.");
        }
    }

    public String lerEmailOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                return null;
            }

            if (Validador.emailValido(email)) {
                return email;
            }

            System.out.println("Formato de e-mail inválido.");
        }
    }

    public String lerCep(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cep = scanner.nextLine();

            if (Validador.cepValido(cep)) {
                return Formatador.formatarCep(cep);
            }

            System.out.println("O CEP deve conter 8 números.");
        }
    }

    public String lerCepOpcional(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            String cep = scanner.nextLine().trim();

            if (cep.isEmpty()) {
                return null;
            }

            if (Validador.cepValido(cep)) {
                return Formatador.formatarCep(cep);
            }

            System.out.println("O CEP deve conter 8 números.");
        }
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);

        String texto = scanner.nextLine().trim();

        return texto.isEmpty() ? null : texto;
    }
}