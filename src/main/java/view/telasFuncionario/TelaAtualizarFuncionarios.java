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
public class TelaAtualizarFuncionarios extends javax.swing.JPanel {

    private entidades.Funcionario funcionarioAtual; // Armazena o funcionario localizado
    private boolean escutadorAtivo = false;  // Controla para não ativar o botão enquanto carrega os dados
    /**
     * Creates new form TelaAtualizarFuncionarios
     */
    public TelaAtualizarFuncionarios() {
        initComponents();
        
        // --- SEU CÓDIGO EDITÁVEL COMEÇA AQUI ---
        configurarGrupoBotoes();
        configurarMascaras();
        configurarEscutadoresDeModificacao();
        configurarEventosBusca();
        
        // O botão de atualizar inicia desativado até que alguma modificação ocorra
        jButtonAtualizar.setEnabled(false);
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
            // Máscara do CPF do formulário
            javax.swing.text.MaskFormatter mascaraCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            jFormattedTextFieldCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCpf));

            // Máscara de Telefone Celular
            javax.swing.text.MaskFormatter mascaraTelefone = new javax.swing.text.MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            jFormattedTextFieldTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraTelefone));

            // Máscara de CEP
            javax.swing.text.MaskFormatter mascaraCep = new javax.swing.text.MaskFormatter("#####-###");
            mascaraCep.setPlaceholderCharacter('_');
            jFormattedTextFieldCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCep));
            
        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(TelaAtualizarFuncionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Monitora se o usuário alterar qualquer dado após a busca
    private void configurarEscutadoresDeModificacao() {
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { verificarAlteracao(); }
            @Override public void removeUpdate(DocumentEvent e) { verificarAlteracao(); }
            @Override public void changedUpdate(DocumentEvent e) { verificarAlteracao(); }
        };

        jTextFieldNome.getDocument().addDocumentListener(dl);
        jTextFieldEmail.getDocument().addDocumentListener(dl);
        jFormattedTextFieldTelefone.getDocument().addDocumentListener(dl);
        jTextFieldLogradouro.getDocument().addDocumentListener(dl);
        jTextFieldNumero.getDocument().addDocumentListener(dl);
        jTextFieldBairro.getDocument().addDocumentListener(dl);
        jTextFieldCidade.getDocument().addDocumentListener(dl);
        jFormattedTextFieldCep.getDocument().addDocumentListener(dl);
        
        jComboBoxEstado.addActionListener(e -> verificarAlteracao());
    }

    private void verificarAlteracao() {
        if (escutadorAtivo && funcionarioAtual != null) {
            jButtonAtualizar.setEnabled(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNome = new javax.swing.JLabel();
        jLabelCpf = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabel1Telefone = new javax.swing.JLabel();
        jLabelLogradouro = new javax.swing.JLabel();
        jLabelNumero = new javax.swing.JLabel();
        jLabelBairro = new javax.swing.JLabel();
        jLabelCidade = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();
        jLabelCep = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldLogradouro = new javax.swing.JTextField();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldBairro = new javax.swing.JTextField();
        jTextFieldCidade = new javax.swing.JTextField();
        jComboBoxEstado = new javax.swing.JComboBox<>();
        jButtonAtualizar = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jFormattedTextFieldCpf = new javax.swing.JFormattedTextField();
        jFormattedTextFieldCep = new javax.swing.JFormattedTextField();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jRadioButtonId = new javax.swing.JRadioButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jButtonBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNome.setText("Nome");

        jLabelCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpf.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpf.setText("CPF");

        jLabelEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEmail.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelEmail.setText("E-mail");

        jLabel1Telefone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1Telefone.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1Telefone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1Telefone.setText("Telefone");

        jLabelLogradouro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelLogradouro.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLogradouro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLogradouro.setText("Logradouro");

        jLabelNumero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNumero.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNumero.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNumero.setText("Número");

        jLabelBairro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBairro.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBairro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBairro.setText("Bairro");

        jLabelCidade.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCidade.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCidade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCidade.setText("Cidade");

        jLabelEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEstado.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelEstado.setText("Estado");

        jLabelCep.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCep.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCep.setText("CEP");

        jComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Selecione --", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));

        jButtonAtualizar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonAtualizar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAtualizar.setText("Atualizar");
        jButtonAtualizar.addActionListener(this::jButtonAtualizarActionPerformed);

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("ATUALIZAR FUNCIONÁRIOS");

        jRadioButtonId.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonId.setText("ID");

        jRadioButtonCpf.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCpf.setText("CPF");

        jButtonBuscar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(this::jButtonBuscarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCpf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1Telefone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelBairro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNome)
                            .addComponent(jTextFieldEmail)
                            .addComponent(jTextFieldLogradouro)
                            .addComponent(jTextFieldNumero)
                            .addComponent(jTextFieldBairro)
                            .addComponent(jTextFieldCidade)
                            .addComponent(jFormattedTextFieldCpf)
                            .addComponent(jFormattedTextFieldCep)
                            .addComponent(jFormattedTextFieldTelefone))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonId)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonCpf)))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 187, Short.MAX_VALUE)
                        .addComponent(jButtonAtualizar)
                        .addGap(132, 132, 132))))
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButtonId)
                            .addComponent(jRadioButtonCpf))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNome)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCpf)
                    .addComponent(jFormattedTextFieldCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1Telefone)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogradouro)
                    .addComponent(jTextFieldLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumero)
                    .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBairro)
                    .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCidade)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEstado)
                    .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCep)
                    .addComponent(jFormattedTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAtualizar)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
        String email = jTextFieldEmail.getText();
        String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (!email.matches(regexEmail)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, insira um endereço de e-mail válido!", "E-mail Inválido", javax.swing.JOptionPane.ERROR_MESSAGE);
            jTextFieldEmail.requestFocus();
            return;
        }

        // Dispara a atualização no controlador usando o ID do objeto guardado
        ControladorFuncionario.getInstanciaControladorFuncionario().atualizarFuncionarioGrafico(
            Integer.parseInt(funcionarioAtual.getIdentificacao()),
            jTextFieldNome.getText(),
            email,
            jFormattedTextFieldTelefone.getText(),
            jTextFieldLogradouro.getText(),
            jTextFieldNumero.getText(),
            jTextFieldBairro.getText(),
            jTextFieldCidade.getText(),
            jComboBoxEstado.getSelectedItem().toString(),
            jFormattedTextFieldCep.getText()
        );

        javax.swing.JOptionPane.showMessageDialog(this, "Cadastro atualizado com sucesso!", "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Desabilita de novo até a próxima alteração
        jButtonAtualizar.setEnabled(false);
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

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
            jButtonAtualizar.setEnabled(false);
            return;
        }

        // Bloqueia o escutador para não ativar o botão de "Atualizar" enquanto os dados entram nos campos
        escutadorAtivo = false;

        jTextFieldNome.setText(funcionarioAtual.getNome());
        jFormattedTextFieldCpf.setText(funcionarioAtual.getCpf());
        jFormattedTextFieldCpf.setEditable(false); // CPF não deve ser alterado em atualizações normais
        jTextFieldEmail.setText(funcionarioAtual.getEmail());
        jFormattedTextFieldTelefone.setText(funcionarioAtual.getTelefone());
        jTextFieldLogradouro.setText(funcionarioAtual.getEndereco().getLogradouro());
        jTextFieldNumero.setText(funcionarioAtual.getEndereco().getNumero());
        jTextFieldBairro.setText(funcionarioAtual.getEndereco().getBairro());
        jTextFieldCidade.setText(funcionarioAtual.getEndereco().getCidade());
        jComboBoxEstado.setSelectedItem(funcionarioAtual.getEndereco().getEstado());
        jFormattedTextFieldCep.setText(funcionarioAtual.getEndereco().getCep());

        // Libera o escutador. Qualquer modificação que o usuário fizer a partir de agora ativará o botão "Atualizar"
        escutadorAtivo = true;
        jButtonAtualizar.setEnabled(false); 
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JComboBox<String> jComboBoxEstado;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JFormattedTextField jFormattedTextFieldCep;
    private javax.swing.JFormattedTextField jFormattedTextFieldCpf;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel1Telefone;
    private javax.swing.JLabel jLabelBairro;
    private javax.swing.JLabel jLabelCep;
    private javax.swing.JLabel jLabelCidade;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelLogradouro;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelNumero;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    private javax.swing.JTextField jTextFieldBairro;
    private javax.swing.JTextField jTextFieldCidade;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldLogradouro;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldNumero;
    // End of variables declaration//GEN-END:variables
}
