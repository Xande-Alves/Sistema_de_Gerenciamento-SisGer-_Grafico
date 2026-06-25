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
public class TelaContrataFuncionario extends javax.swing.JPanel {

    private entidades.Funcionario funcionarioAtual; // Armazena o funcionario localizado
    private boolean escutadorAtivo = false;

    /**
     * Creates new form TelaContrataFuncionario
     */
    public TelaContrataFuncionario() {
        initComponents();

        // Pegamos as restrições de layout atuais do campo de busca
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) this.getLayout();
        java.awt.GridBagConstraints constraints = layout.getConstraints(jFormattedTextFieldBusca);

        // Mudamos o preenchimento interno (ipadx) de 32 para 150 (alarga o campo)
        constraints.ipadx = 150;

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

        jButtonContratar.setEnabled(false);
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

            // =================================================================
            // MÁSCARA DE DATA (CAMPO DE ADMISSÃO)
            // =================================================================
            javax.swing.text.MaskFormatter mascaraData = new javax.swing.text.MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            jFormattedTextFieldAdmissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraData));

            // =================================================================
            // FORMATADOR MONETÁRIO (CAMPO DE SALÁRIO)
            // =================================================================
            java.text.NumberFormat formatoMoeda = java.text.NumberFormat.getCurrencyInstance();
            formatoMoeda.setMinimumFractionDigits(2);
            formatoMoeda.setMaximumFractionDigits(2);

            javax.swing.text.NumberFormatter formatterSalario = new javax.swing.text.NumberFormatter(formatoMoeda);
            formatterSalario.setAllowsInvalid(false); // Impede digitação de letras
            formatterSalario.setOverwriteMode(true);

            jFormattedTextFieldSalario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatterSalario));

            // Define um valor inicial numérico para evitar erros de inicialização vazia
            jFormattedTextFieldSalario.setValue(0.00);

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
        jFormattedTextFieldAdmissao.getDocument().addDocumentListener(escutadorCampos);
        jFormattedTextFieldSalario.getDocument().addDocumentListener(escutadorCampos);
        jTextFieldLogin.getDocument().addDocumentListener(escutadorCampos);
        jPasswordFieldSenha.getDocument().addDocumentListener(escutadorCampos);
        jPasswordFieldConfirmaSenha.getDocument().addDocumentListener(escutadorCampos);

        // Adiciona monitoramento à caixa de seleção do Cargo
        jComboBoxCargo.addActionListener(e -> verificarCampos());
    }

    private void verificarCampos() {
        // Se o escutador não estiver ativo ou o funcionário não foi buscado, mantém desativado
        if (!escutadorAtivo || funcionarioAtual == null) {
            jButtonContratar.setEnabled(false);
            return;
        }

        // 1. Valida Seleção do Cargo
        boolean cargoValido = jComboBoxCargo.getSelectedIndex() > 0; // Evita o "-- Selecione --"

        // 2. Valida Salário (Verifica se não está vazio ou zerado)
        double salario = 0.0;
        if (jFormattedTextFieldSalario.getValue() != null) {
            salario = ((Number) jFormattedTextFieldSalario.getValue()).doubleValue();
        }
        boolean salarioValido = salario > 0.0;

        // 3. SEPARAÇÃO CORRETA: Apenas verifica se o campo de data foi totalmente preenchido
        // Se todos os números da máscara forem digitados, o getValue() não será nulo.
        boolean dataValida = jFormattedTextFieldAdmissao.getValue() != null;

        // 4. Valida Login
        boolean loginValido = !jTextFieldLogin.getText().trim().isEmpty();

        // 5. Valida Senha e Confirmação
        String senha = new String(jPasswordFieldSenha.getPassword());
        String confirmaSenha = new String(jPasswordFieldConfirmaSenha.getPassword());
        boolean senhasValidas = !senha.isEmpty() && senha.equals(confirmaSenha);

        // O botão só ativa se TODAS as condições forem verdadeiras
        jButtonContratar.setEnabled(cargoValido && salarioValido && dataValida && loginValido && senhasValidas);
    }

    private void limparFormulario() {
        escutadorAtivo = false;
        funcionarioAtual = null;
        jLabelNomeFunc.setText("");
        jLabelCpfFunc.setText("");
        jFormattedTextFieldBusca.setText("");
        jComboBoxCargo.setSelectedIndex(0);
        jFormattedTextFieldSalario.setValue(0.00);
        jFormattedTextFieldAdmissao.setText("");
        jTextFieldLogin.setText("");
        jPasswordFieldSenha.setText("");
        jPasswordFieldConfirmaSenha.setText("");
        jButtonContratar.setEnabled(false);
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

        jLabelNome = new javax.swing.JLabel();
        jLabelCpf = new javax.swing.JLabel();
        jLabelCargo = new javax.swing.JLabel();
        jLabelSalario = new javax.swing.JLabel();
        jLabelAdmissao = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jLabelSenha = new javax.swing.JLabel();
        jLabelConfirmaSenha = new javax.swing.JLabel();
        jTextFieldLogin = new javax.swing.JTextField();
        jComboBoxCargo = new javax.swing.JComboBox<>();
        jButtonContratar = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jFormattedTextFieldSalario = new javax.swing.JFormattedTextField();
        jRadioButtonId = new javax.swing.JRadioButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jButtonBuscar = new javax.swing.JButton();
        jLabelNomeFunc = new javax.swing.JLabel();
        jLabelCpfFunc = new javax.swing.JLabel();
        jFormattedTextFieldAdmissao = new javax.swing.JFormattedTextField();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jPasswordFieldConfirmaSenha = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(153, 153, 255));
        setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelNome, gridBagConstraints);

        jLabelCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpf.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpf.setText("CPF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelCpf, gridBagConstraints);

        jLabelCargo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCargo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCargo.setText("Cargo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelCargo, gridBagConstraints);

        jLabelSalario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSalario.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSalario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSalario.setText("Salário");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 56;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelSalario, gridBagConstraints);

        jLabelAdmissao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelAdmissao.setForeground(new java.awt.Color(0, 0, 0));
        jLabelAdmissao.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAdmissao.setText("Admissão");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelAdmissao, gridBagConstraints);

        jLabelLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelLogin.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLogin.setText("Login");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 63;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelLogin, gridBagConstraints);

        jLabelSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSenha.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSenha.setText("Senha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 59;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelSenha, gridBagConstraints);

        jLabelConfirmaSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelConfirmaSenha.setForeground(new java.awt.Color(0, 0, 0));
        jLabelConfirmaSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelConfirmaSenha.setText("Confirma senha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelConfirmaSenha, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jTextFieldLogin, gridBagConstraints);

        jComboBoxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Selecione --", "Vendedor", "Estoquista", "Gerente de Vendas", "Gerente de Estoque" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 136;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jComboBoxCargo, gridBagConstraints);

        jButtonContratar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonContratar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonContratar.setText("Contratar");
        jButtonContratar.addActionListener(this::jButtonContratarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 45, 17, 0);
        add(jButtonContratar, gridBagConstraints);

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONTRATAR FUNCIONÁRIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 212;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabelTitulo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jFormattedTextFieldSalario, gridBagConstraints);

        jRadioButtonId.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonId.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 0, 0);
        add(jRadioButtonId, gridBagConstraints);

        jRadioButtonCpf.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCpf.setText("CPF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 18, 0, 0);
        add(jRadioButtonCpf, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jFormattedTextFieldBusca, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(18, 17, 0, 0);
        add(jButtonBuscar, gridBagConstraints);

        jLabelNomeFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNomeFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNomeFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jLabelNomeFunc, gridBagConstraints);

        jLabelCpfFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCpfFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpfFunc.setText("          ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jLabelCpfFunc, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jFormattedTextFieldAdmissao, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jPasswordFieldSenha, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 209;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        add(jPasswordFieldConfirmaSenha, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonContratarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonContratarActionPerformed
        // 1. Pega o ID e o Cargo normalmente
        String cargo = jComboBoxCargo.getSelectedItem().toString();

        // 2. RECUPERA O SALÁRIO (Convertendo de Number para double)
        double salario = 0.0;
        if (jFormattedTextFieldSalario.getValue() != null) {
            salario = ((Number) jFormattedTextFieldSalario.getValue()).doubleValue();
        }

        // 3. CONVERTE E VALIDA LOGICAMENTE A DATA AO CLICAR
        java.time.LocalDate dataAdmissao = null;
        Object valorData = jFormattedTextFieldAdmissao.getValue();

        try {
            if (valorData == null) {
                throw new Exception("Campo vazio");
            }

            String dataTexto = valorData.toString().trim();
            int dia, mes, ano;

            if (dataTexto.contains("-")) {
                String[] partes = dataTexto.split("-");
                ano = Integer.parseInt(partes[0]);
                mes = Integer.parseInt(partes[1]);
                dia = Integer.parseInt(partes[2]);
            } else {
                String[] partes = dataTexto.split("/");
                dia = Integer.parseInt(partes[0]);
                mes = Integer.parseInt(partes[1]);
                ano = Integer.parseInt(partes[2]);
            }

            // Se for uma data impossível (Ex: 31/04 ou 30/02), o LocalDate.of joga o erro pro catch
            dataAdmissao = java.time.LocalDate.of(ano, mes, dia);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "A data de admissão informada é inválida!\nPor favor, digite uma data real válida.",
                    "Data Inválida",
                    javax.swing.JOptionPane.ERROR_MESSAGE);

            jFormattedTextFieldAdmissao.requestFocus();
            return; // Interrompe a contratação
        }

        // CAPTURA LOGIN
        String login = jTextFieldLogin.getText();

        // FORMA CORRETA: Captura como array de char
        char[] senhaArray = jPasswordFieldSenha.getPassword();
        // Converte para String APENAS se o seu controlador exigir uma String
        String senha = new String(senhaArray);
        // Limpa o array da memória logo após o uso por segurança
        java.util.Arrays.fill(senhaArray, '0');

        // CHAMA O MÉTODO DO CONTROLADOR
        boolean sucesso = ControladorFuncionario.getInstanciaControladorFuncionario()
                .contratarFuncionario(
                        funcionarioAtual,
                        cargo,
                        salario,
                        dataAdmissao,
                        login,
                        senha);

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
                "Funcionário contratado com sucesso!",
                "Sucesso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o sucesso
        limparFormulario();
    }//GEN-LAST:event_jButtonContratarActionPerformed

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
            jButtonContratar.setEnabled(false);
            return;
        }

        escutadorAtivo = false; // Bloqueia validações enquanto preenche o cabeçalho

        jLabelNomeFunc.setText(funcionarioAtual.getNome());
        jLabelCpfFunc.setText(funcionarioAtual.getCpf());

        escutadorAtivo = true; // Libera o monitoramento dos campos do formulário
        verificarCampos();     // Executa a primeira verificação do estado atual
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonContratar;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private javax.swing.JFormattedTextField jFormattedTextFieldAdmissao;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JFormattedTextField jFormattedTextFieldSalario;
    private javax.swing.JLabel jLabelAdmissao;
    private javax.swing.JLabel jLabelCargo;
    private javax.swing.JLabel jLabelConfirmaSenha;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelCpfFunc;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelNomeFunc;
    private javax.swing.JLabel jLabelSalario;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPasswordField jPasswordFieldConfirmaSenha;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    private javax.swing.JTextField jTextFieldLogin;
    // End of variables declaration//GEN-END:variables
}
