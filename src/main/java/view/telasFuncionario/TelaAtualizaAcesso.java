/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasFuncionario;

import controladores.ControladorFuncionario;
import javax.swing.ButtonGroup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Mario
 */
public class TelaAtualizaAcesso extends javax.swing.JPanel {
    
    private entidades.Funcionario funcionarioAtual; // Armazena o funcionario localizado
    private boolean escutadorAtivo = false;

    /**
     * Creates new form TelaAtualizaAcesso
     */
    public TelaAtualizaAcesso() {
        initComponents();
        
        // Pegamos as restrições de layout atuais do campo de busca
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) this.getLayout();
        java.awt.GridBagConstraints constraints = layout.getConstraints(jFormattedTextFieldBusca);

        // Mudamos o preenchimento interno (ipadx) de 32 para 150 (alarga o campo)
        constraints.ipadx = 90;

        // Reaplicamos as novas restrições modificadas ao componente
        layout.setConstraints(jFormattedTextFieldBusca, constraints);

        // Força o painel a recalcular o layout visual na tela
        this.revalidate();
        // =================================================================
        
        configurarGrupoBotoes();
        configurarMascaras(); // Inicializa as máscaras internas (salário/data)

        // CORREÇÃO: Força o campo de busca a iniciar sem máscara (já que ID inicia selecionado)
        jFormattedTextFieldBusca.setFormatterFactory(null);
        jFormattedTextFieldBusca.setText("");

        configurarEscutadoresDeModificacao();
        configurarEventosBusca();

        jButtonAtualizarAcesso.setEnabled(false);
    }
    
    // Une os RadioButtons para que funcionem de forma exclusiva (marcar um desmarca o outro)
    private void configurarGrupoBotoes() {
        ButtonGroup grupoBusca = new ButtonGroup();
        grupoBusca.add(jRadioButtonId);
        grupoBusca.add(jRadioButtonCpf);
        jRadioButtonId.setSelected(true); // ID inicia selecionado por padrão
    }

    private void configurarEventosBusca() {
        // Evento para quando selecionar ID: remove a máscara
        jRadioButtonId.addActionListener(e -> {
            jFormattedTextFieldBusca.setFormatterFactory(null);
            jFormattedTextFieldBusca.setText("");
            jFormattedTextFieldBusca.requestFocus();
        });

        // Evento para quando selecionar CPF: aplica a máscara
        jRadioButtonCpf.addActionListener(e -> {
            try {
                javax.swing.text.MaskFormatter mascaraCpfBusca = new javax.swing.text.MaskFormatter("###.###.###-##");
                mascaraCpfBusca.setPlaceholderCharacter('_');
                jFormattedTextFieldBusca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCpfBusca));
                jFormattedTextFieldBusca.setText("");
                jFormattedTextFieldBusca.requestFocus();
            } catch (java.text.ParseException ex) {
                java.util.logging.Logger.getLogger(TelaAtualizarFuncionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    private void configurarMascaras() {
        try {
            // =================================================================
            // MÁSCARA DO CPF (CAMPO DE BUSCA)
            // =================================================================
            javax.swing.text.MaskFormatter mascaraCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            jFormattedTextFieldBusca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCpf));

        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(TelaAtualizarFuncionarios.class.getName())
                    .log(java.util.logging.Level.SEVERE, "Erro ao configurar as máscaras dos campos", ex);
        }
    }

    private void configurarEscutadoresDeModificacao() {
        // Escutador padrão para campos de texto, formatação e senhas
        DocumentListener escutadorCampos = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCampos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCampos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarCampos();
            }
        };

        // Adiciona o monitoramento aos componentes necessários
        jTextFieldLogin.getDocument().addDocumentListener(escutadorCampos);
        jPasswordFieldSenha.getDocument().addDocumentListener(escutadorCampos);
        jPasswordFieldConfirmaSenha.getDocument().addDocumentListener(escutadorCampos);
    }

    private void verificarCampos() {
        // Se o escutador não estiver ativo ou o funcionário não foi buscado, mantém desativado
        if (!escutadorAtivo || funcionarioAtual == null) {
            jButtonAtualizarAcesso.setEnabled(false);
            return;
        }

        // 4. Valida Login
        boolean loginValido = !jTextFieldLogin.getText().trim().isEmpty();

        // 5. Valida Senha e Confirmação
        String senha = new String(jPasswordFieldSenha.getPassword());
        String confirmaSenha = new String(jPasswordFieldConfirmaSenha.getPassword());
        boolean senhasValidas = !senha.isEmpty() && senha.equals(confirmaSenha);

        // O botão só ativa se TODAS as condições forem verdadeiras
        jButtonAtualizarAcesso.setEnabled(loginValido && senhasValidas);
    }

    private void limparFormulario() {
        escutadorAtivo = false;
        funcionarioAtual = null;
        jLabelNomeFunc.setText("");
        jLabelCpfFunc.setText("");
        jLabelLoginAtualFunc.setText("");
        jLabelSenhaAtualFunc.setText("");
        jFormattedTextFieldBusca.setText("");
        jTextFieldLogin.setText("");
        jPasswordFieldSenha.setText("");
        jPasswordFieldConfirmaSenha.setText("");
        jButtonAtualizarAcesso.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelCpfFunc = new javax.swing.JLabel();
        jLabelLoginAtual = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jLabelSenhaAtual = new javax.swing.JLabel();
        jLabelSenha = new javax.swing.JLabel();
        jRadioButtonId = new javax.swing.JRadioButton();
        jButtonAtualizarAcesso = new javax.swing.JButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jLabelNome = new javax.swing.JLabel();
        jLabelConfirmaSenha = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jPasswordFieldConfirmaSenha = new javax.swing.JPasswordField();
        jLabelNomeFunc = new javax.swing.JLabel();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jTextFieldLogin = new javax.swing.JTextField();
        jLabelCpf = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelLoginAtualFunc = new javax.swing.JLabel();
        jLabelSenhaAtualFunc = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 255));
        setLayout(new java.awt.GridBagLayout());

        jLabelCpfFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCpfFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpfFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 0, 0);
        add(jLabelCpfFunc, gridBagConstraints);

        jLabelLoginAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelLoginAtual.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLoginAtual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLoginAtual.setText("Login Atual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 1, 0, 0);
        add(jLabelLoginAtual, gridBagConstraints);

        jButtonBuscar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(this::jButtonBuscarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 17, 0, 0);
        add(jButtonBuscar, gridBagConstraints);

        jLabelSenhaAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSenhaAtual.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSenhaAtual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSenhaAtual.setText("Senha Atual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 1, 0, 0);
        add(jLabelSenhaAtual, gridBagConstraints);

        jLabelSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSenha.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSenha.setText("Senha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 59;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabelSenha, gridBagConstraints);

        jRadioButtonId.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonId.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 1, 0, 0);
        add(jRadioButtonId, gridBagConstraints);

        jButtonAtualizarAcesso.setBackground(new java.awt.Color(102, 102, 255));
        jButtonAtualizarAcesso.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAtualizarAcesso.setText("Atualizar acesso");
        jButtonAtualizarAcesso.addActionListener(this::jButtonAtualizarAcessoActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 50, 73, 0);
        add(jButtonAtualizarAcesso, gridBagConstraints);

        jRadioButtonCpf.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCpf.setText("CPF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 18, 0, 0);
        add(jRadioButtonCpf, gridBagConstraints);

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNome.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 59;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 1, 0, 0);
        add(jLabelNome, gridBagConstraints);

        jLabelConfirmaSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelConfirmaSenha.setForeground(new java.awt.Color(0, 0, 0));
        jLabelConfirmaSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelConfirmaSenha.setText("Confirma senha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabelConfirmaSenha, gridBagConstraints);

        jLabelLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelLogin.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLogin.setText("Login");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 63;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(jLabelLogin, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 0, 0);
        add(jPasswordFieldConfirmaSenha, gridBagConstraints);

        jLabelNomeFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNomeFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNomeFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 23, 0, 0);
        add(jLabelNomeFunc, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 0, 0);
        add(jPasswordFieldSenha, gridBagConstraints);

        jFormattedTextFieldBusca.setColumns(100);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 1, 0, 0);
        add(jFormattedTextFieldBusca, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 23, 0, 0);
        add(jTextFieldLogin, gridBagConstraints);

        jLabelCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpf.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpf.setText("CPF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 1, 0, 0);
        add(jLabelCpf, gridBagConstraints);

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("ATUALIZAR ACESSO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 277;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabelTitulo, gridBagConstraints);

        jLabelLoginAtualFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelLoginAtualFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLoginAtualFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 0, 0);
        add(jLabelLoginAtualFunc, gridBagConstraints);

        jLabelSenhaAtualFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelSenhaAtualFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSenhaAtualFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 0, 0);
        add(jLabelSenhaAtualFunc, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String termo = jFormattedTextFieldBusca.getText().trim();
        String tipoBusca = jRadioButtonId.isSelected() ? "ID" : "CPF";

        if (termo.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite o ID ou CPF para buscar.", "Campo Vazio", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Faz a chamada ao Controlador que configuramos anteriormente
        funcionarioAtual = ControladorFuncionario.getInstanciaControladorFuncionario().buscarFuncionarioPorIdOuCpf(tipoBusca, termo);

        if (funcionarioAtual == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Funcionário não encontrado no sistema.", "Não Localizado", javax.swing.JOptionPane.ERROR_MESSAGE);
            jButtonAtualizarAcesso.setEnabled(false);
            return;
        }

        escutadorAtivo = false; // Bloqueia validações enquanto preenche o cabeçalho

        jLabelNomeFunc.setText(funcionarioAtual.getNome());
        jLabelCpfFunc.setText(funcionarioAtual.getCpf());
        jLabelLoginAtualFunc.setText(funcionarioAtual.getLogin());
        jLabelSenhaAtualFunc.setText(funcionarioAtual.getSenha());

        escutadorAtivo = true; // Libera o monitoramento dos campos do formulário
        verificarCampos();     // Executa a primeira verificação do estado atual
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAtualizarAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarAcessoActionPerformed
    
        // CAPTURA LOGIN
        String login = jTextFieldLogin.getText();

        // FORMA CORRETA: Captura como array de char
        char[] senhaArray = jPasswordFieldSenha.getPassword();
        // Converte para String APENAS se o seu controlador exigir uma String
        String senha = new String(senhaArray);
        // Limpa o array da memória logo após o uso por segurança
        java.util.Arrays.fill(senhaArray, '0');

        // CHAMA O MÉTODO DO CONTROLADOR
        boolean sucesso = ControladorFuncionario.getInstanciaControladorFuncionario().atualizarAcesso(funcionarioAtual, login, senha);

        if (!sucesso) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Login já existente.",
                "Erro",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Credenciais alteradas com sucesso!",
            "Sucesso",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o sucesso
        limparFormulario();
    }//GEN-LAST:event_jButtonAtualizarAcessoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizarAcesso;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JLabel jLabelConfirmaSenha;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelCpfFunc;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelLoginAtual;
    private javax.swing.JLabel jLabelLoginAtualFunc;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelNomeFunc;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JLabel jLabelSenhaAtual;
    private javax.swing.JLabel jLabelSenhaAtualFunc;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPasswordField jPasswordFieldConfirmaSenha;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    private javax.swing.JTextField jTextFieldLogin;
    // End of variables declaration//GEN-END:variables
}
