package controladores;

import entidades.Pessoa;
import utilitarios.LeitorConsole;

import java.util.List;
import java.util.Scanner;

public abstract class  ControladorPessoa {
    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);

    public void cadastrarPessoaGrafico (Pessoa p, String nome, String cpf, String email, String telefone, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        p.setNome(nome);

        p.setCpf(cpf);

        p.setEmail(email);

        p.setTelefone(telefone);
        
        ControladorEndereco.getInstanciaControladorEndereco().cadastrarEnderecoGrafico(p.getEndereco(), logradouro, numero, bairro, cidade, estado, cep);
    }

    public void mostrarDadosPessoa(Pessoa p) {
        System.out.println("Nome: "+p.getNome());
        System.out.println("CPF: "+p.getCpf());
        System.out.println("E-mail: "+p.getEmail());
        System.out.println("Telefone celular: "+p.getTelefone());
        ControladorEndereco.getInstanciaControladorEndereco().mostrarEndereco(p.getEndereco());
    }

    public void alteraDadosPessoa(Pessoa p) {
        String nome = leitor.lerTextoOpcional("Nome (enter para não alterar): ");
        if (!nome.isEmpty()) {
            p.setNome(nome);
        }

        String cpf = leitor.lerCpfOpcional(
                "Informe o novo CPF (enter para não alterar): "
        );

        if (cpf != null) {
            p.setCpf(cpf);
        }

        String email = leitor.lerEmailOpcional(
                "Informe o novo e-mail (enter para não alterar): "
        );

        if (email != null) {
            p.setEmail(email);
        }

        String telefone = leitor.lerTelefoneOpcional(
                "Novo telefone (enter para não alterar): "
        );

        if (telefone != null) {
            p.setTelefone(telefone);
        }
        ControladorEndereco.getInstanciaControladorEndereco().alterarEndereco(p.getEndereco());
    }

    public void consultarPessoaPorNome(List<? extends Pessoa> lista) {
        String nomeConsulta = leitor.lerTexto("Digite o nome que deseja consultar: ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Pessoa p : lista) {
            if (p.getNome().toLowerCase().contains(nomeConsulta.toLowerCase())) {
                System.out.println(p.getIdentificacao());
                mostrarDadosPessoa(p);
                existeRegistro = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarPessoaPorCpf(List<? extends Pessoa> lista) {
        String cpfConsulta = leitor.lerTexto("Digite o CPF que deseja consultar (apenas números): ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Pessoa p : lista) {
            if (p.getCpf().replaceAll("[^0-9]", "").contains(cpfConsulta.replaceAll("[^0-9]", ""))) {
                System.out.println(p.getIdentificacao());
                mostrarDadosPessoa(p);
                existeRegistro = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarPessoaPorEmail(List<? extends Pessoa> lista) {
        String emailConsulta = leitor.lerTexto("Digite o E-mail que deseja consultar: ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Pessoa p : lista) {
            if (p.getEmail().toLowerCase().contains(emailConsulta.toLowerCase())) {
                System.out.println(p.getIdentificacao());
                mostrarDadosPessoa(p);
                existeRegistro = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void consultarPessoaPorTelefone(List<? extends Pessoa> lista) {
        String telefoneConsulta = leitor.lerTexto("Digite o telefone celular que deseja consultar (apenas números): ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Pessoa p : lista) {
            if (p.getTelefone().replaceAll("[^0-9]", "").contains(telefoneConsulta.replaceAll("[^0-9]", ""))) {
                System.out.println(p.getIdentificacao());
                mostrarDadosPessoa(p);
                existeRegistro = true;
                System.out.println("==================================================================");
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }
}
