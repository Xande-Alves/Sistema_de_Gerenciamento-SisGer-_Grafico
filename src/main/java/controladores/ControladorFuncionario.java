package controladores;

import entidades.Funcionario;
import entidades.Pessoa;
//import menus.MenuEntidade;
import repositorio.Repositorio;
import utilitarios.LeitorConsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ControladorFuncionario extends ControladorPessoa {
    private final Scanner scanner = new Scanner(System.in);
    private final LeitorConsole leitor = new LeitorConsole(scanner);
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static ControladorFuncionario controladorFuncionarioInstancia;

    private ControladorFuncionario() {

    }

    public static ControladorFuncionario getInstanciaControladorFuncionario() {
        if (controladorFuncionarioInstancia == null) {
            controladorFuncionarioInstancia = new ControladorFuncionario();
        }
        return controladorFuncionarioInstancia;
    }

    public void cadastrarFuncionario () {
        System.out.println("=====================CADASTRO DE FUNCIONÁRIOS======================");

        int idFuncionario = Repositorio.getInstanciaRepositorio().getListaFuncionarios().size() + 1;

        Funcionario func = new Funcionario(idFuncionario,false);

        //cadastrarPessoa(func);

        int concluir = leitor.lerInteiro(
                "Concluir o procedimento? (1 para SIM): "
        );
        if (concluir != 1) {
            //MenuEntidade.getInstanciaMenuEntidade().escolhaMenuFuncionario();
            return;
        }

        Repositorio.getInstanciaRepositorio().getListaFuncionarios().add(func);
        System.out.println("Funcionário cadastrado com sucesso.");
        System.out.println("==================================================================");
    }

    public void atualizarFuncionario() {
        System.out.println("================ATUALIZAR CADASTRO DE FUNCIONÁRIOS=================");
        int idFuncionario = leitor.lerInteiro(
                "Informe o ID do funcionário: "
        );
        boolean existeFuncionario = false;

        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFuncionario) {
                System.out.println("ID: "+func.getIdFuncionario());
                mostrarDadosPessoa(func);
                System.out.println("==================================================================");

                alteraDadosPessoa(func);
                existeFuncionario = true;
                System.out.println("Cadastro atualizado com sucesso!");
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
        System.out.println("==================================================================");
    }

    public void listarFuncionarios () {
        System.out.println("=======================LISTA DE FUNCIONÁRIOS=======================");
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            System.out.println("ID Funcionário: " + func.getIdFuncionario());
            mostrarDadosPessoa(func);
            System.out.println("==================================================================");
        }
    }

    public void consultarFuncionarioNome() { consultarPessoaPorNome(Repositorio.getInstanciaRepositorio().getListaFuncionarios()); }

    public void consultarFuncionarioCpf() {
        consultarPessoaPorCpf(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioEmail() {
        consultarPessoaPorEmail(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioTelefone() {
        consultarPessoaPorTelefone(Repositorio.getInstanciaRepositorio().getListaFuncionarios());
    }

    public void consultarFuncionarioCargo() {
        String cargo = leitor.lerTexto("Informe o cargo do funcionário: ");
        boolean existeRegistro = false;
        System.out.println("RESULTADOS DA PESQUISA:");
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.isAtivo()) {
                if (func.getCargo().toLowerCase().contains(cargo.toLowerCase())) {
                    System.out.println("ID Funcionário: " + func.getIdFuncionario());
                    mostrarDadosPessoa(func);
                    existeRegistro = true;
                    System.out.println("==================================================================");
                }
            }
        }
        if (!existeRegistro) {
            System.out.println("==================================================================");
            System.out.println("Não existe registro para os dados informados.");
            System.out.println("==================================================================");
        }
    }

    public void contrataFuncionario() {
        System.out.println("====================CONTRATAÇÃO DE FUNCIONÁRIO====================");
        int idFunc = leitor.lerInteiro(
                "Informe o ID de funcionário cadastrado para efetuar contratação: "
        );

        boolean existeFuncionario = false;

        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFunc) {
                if (!func.isAtivo()) {
                    existeFuncionario = true;
                    LocalDate data = leitor.lerData(
                            "Qual a data de contratação efetiva do funcionário? (dd/mm/aaaa): "
                    );
                    func.setDataAdmissao(data);
                    func.setAtivo(true);

                    String cargo;
                    while (true) {
                        int escolhaCargo = leitor.lerInteiro(
                                "1 - Vendedor\n2 - Gerente de Vendas\n3 - Estoquista\n4 - Gerente de Estoque\nQual o cargo para qual o funcionário foi contratado? "
                            );
                        if (escolhaCargo == 1) {
                            cargo = "vendedor";
                            break;
                        } else if (escolhaCargo == 2) {
                            cargo = "gerente de vendas";
                            break;
                        } else if (escolhaCargo == 3) {
                            cargo = "estoquista";
                            break;
                        } else if (escolhaCargo == 4) {
                            cargo = "gerente de estoque";
                            break;
                        } else {
                            System.out.print("Escolha inválida. ");
                        }
                    }
                    func.setCargo(cargo);

                    double salario = leitor.lerDouble(
                            "Qual será o salário do funcionário? "
                    );
                    func.setSalario(salario);

                    ControladorLogin.getInstanciaControladorLogin().cadastrarAcesso(func, Repositorio.getInstanciaRepositorio().getListaFuncionarios());
                    System.out.println("Funcionário contratado com sucesso.");
                } else {
                    System.out.println("O funcionário contratado com data de admissão em " + func.getDataAdmissao().format(formatador) + ".");
                }
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
    }

    public void alteraCargo() {
        System.out.println("======================MUDANÇA DE CARGO DE FUNCIONÁRIO=====================");
        int idFunc = leitor.lerInteiro(
                "Informe o ID de funcionário para mudança de cargo: "
        );
        boolean existeFuncionario = false;
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFunc) {
                if (func.isAtivo()) {
                    String cargo;
                    String novoAcesso;
                    while (true) {
                        int escolhaCargo = leitor.lerInteiro(
                                "1 - Vendedor\n2 - Gerente de Vendas\n3 - Estoquista\n4 - Gerente de Estoque\nQual o novo cargo do funcionário? "
                        );
                        if (escolhaCargo == 1) {
                            cargo = "vendedor";
                            novoAcesso = "14";
                            break;
                        } else if (escolhaCargo == 2) {
                            cargo = "gerente de vendas";
                            novoAcesso = "1234";
                            break;
                        } else if (escolhaCargo == 3) {
                            cargo = "estoquista";
                            novoAcesso = "35";
                            break;
                        } else if (escolhaCargo == 4) {
                            cargo = "gerente de estoque";
                            novoAcesso = "235";
                            break;
                        } else {
                            System.out.print("Escolha inválida. ");
                        }
                    }
                    func.setCargo(cargo);
                    func.setNivelAcesso(novoAcesso);
                    System.out.println("Cargo de funcionário alterado com sucesso.");
                } else {
                    System.out.println("O funcionário não está contratado.");
                }
                existeFuncionario = true;
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
    }

    public void alteraSalario() {
        System.out.println("========================MUDANÇA DE SALÁRIO========================");
        int idFunc = leitor.lerInteiro(
                "Informe o ID de funcionário para mudança de salário: "
        );
        boolean existeFuncionario = false;
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFunc) {
                if (func.isAtivo()) {
                    double salario = leitor.lerDouble(
                            "Qual será o novo salário do funcionário? "
                    );
                    func.setSalario(salario);
                    System.out.println("Mudança de salário efetuada com sucesso.");
                } else {
                    System.out.println("O funcionário não está contratado.");
                }
                existeFuncionario = true;
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
    }

    public void desligaFuncionario() {
        System.out.println("====================DESLIGAMENTO DE FUNCIONÁRIO===================");
        int idFunc = leitor.lerInteiro(
                "Informe o ID de funcionário para desligamento: "
        );
        boolean existeFuncionario = false;
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFunc) {
                if (func.isAtivo()) {
                    LocalDate data = leitor.lerData(
                            "Informe a data de desligamento (dd/mm/aaaa): "
                    );
                    func.setDataDemissao(data);
                    func.setAtivo(false);
                    System.out.println("Funcionário desligado com sucesso.");
                } else {
                    System.out.println("O funcionário não está contratado.");
                }
                existeFuncionario = true;
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
    }

    public void atualizarAcesso() {
        System.out.println("===============ATUALIZAÇÃO DE ACESSO DE FUNCIONÁRIO===============");
        int idFuncionario = leitor.lerInteiro(
                "Informe o ID do funcionário: "
        );
        boolean existeFuncionario = false;
        for (Funcionario func : Repositorio.getInstanciaRepositorio().getListaFuncionarios()) {
            if (func.getIdFuncionario() == idFuncionario) {
                if (func.isAtivo()) {
                    System.out.println("ID do funcionário: "+func.getIdFuncionario());
                    mostrarDadosPessoa(func);

                    System.out.println("==================================================================");

                    ControladorLogin.getInstanciaControladorLogin().alterarAcesso(func, Repositorio.getInstanciaRepositorio().getListaFuncionarios());
                    System.out.println("Acesso atualizado com sucesso!");
                    System.out.println("ID: "+func.getIdFuncionario());
                    ControladorLogin.getInstanciaControladorLogin().mostrarAcesso(func);
                } else {
                    System.out.println("Funcionário não está contratado.");
                }
                existeFuncionario = true;
            }
        }
        if (!existeFuncionario) {
            System.out.println("ID de funcionário não existe.");
        }
        System.out.println("==================================================================");
    }

    @Override
    public void mostrarDadosPessoa(Pessoa p) {
        super.mostrarDadosPessoa(p);
        Funcionario func = (Funcionario) p;
        if (func.isAtivo()) {
            System.out.println("Situação: Ativo");
            System.out.println("Cargo: " + func.getCargo());
            if (func.getCargo().equals("vendedor") && func.getcomissaoVendedor() != null) {
                System.out.printf("Salário: R$ %.2f%n",(func.getSalario() + func.getcomissaoVendedor()));
            } else {
                System.out.printf("Salário: R$ %.2f%n", func.getSalario());
            }
            System.out.println("Data de admissão: " + func.getDataAdmissao().format(formatador));
            if (func.getDataDemissao() != null) {
                System.out.println("Data de demissão: " + func.getDataDemissao().format(formatador));
            }
            System.out.println("Login: " + func.getLogin());
            System.out.println("Senha: " + func.getSenha());
            System.out.println("Acesso: " + func.getNivelAcesso());
        } else {
            System.out.println("Situação: Inativo");
        }
    }
}
