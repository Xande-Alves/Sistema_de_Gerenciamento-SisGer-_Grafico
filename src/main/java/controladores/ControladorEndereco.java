package controladores;
import entidades.Endereco;
import utilitarios.LeitorConsole;


import java.util.Scanner;

public class ControladorEndereco {
    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private static ControladorEndereco instanciaControladorEndereco;

    private ControladorEndereco() {

    }

    public static ControladorEndereco getInstanciaControladorEndereco() {
        if (instanciaControladorEndereco == null) {
            instanciaControladorEndereco = new ControladorEndereco();
        }
        return instanciaControladorEndereco;
    }

    public void cadastrarEnderecoGrafico(Endereco e, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        e.setLogradouro(logradouro);
        e.setNumero(numero);
        e.setBairro(bairro);
        e.setCidade(cidade);
        e.setEstado(estado);
        e.setCep(cep);
    }

    public void alterarEnderecoGrafico(Endereco e, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        
        if (!logradouro.isEmpty()) {
            e.setLogradouro(logradouro);
        }

        if (!numero.isEmpty()) {
            e.setNumero(numero);
        }

        if (!bairro.isEmpty()) {
            e.setBairro(bairro);
        }

        if (!cidade.isEmpty()) {
            e.setCidade(cidade);
        }

        if (!estado.isEmpty()) {
            e.setEstado(estado);
        }

        if (!cep.isEmpty()) {
            e.setCep(cep);
        }

    }
}
