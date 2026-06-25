/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasCliente;

import controladores.ControladorCliente;
import javax.swing.ButtonGroup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Mario
 */
public class TelaAtualizarClientes extends javax.swing.JPanel {

    private entidades.Cliente clienteAtual; // Armazena o cliente localizado
    private boolean escutadorAtivo = false;  // Controla para não ativar o botão enquanto carrega os dados

    /**
     * Creates new form TelaAtualizarClientes
     */
    public TelaAtualizarClientes() {
        initComponents();
        
        // --- SEU CÓDIGO EDITÁVEL COMEÇA AQUI ---
        configurarGrupoBotoes();
        configurarMascaras();
        configurarEscutadoresDeModificacao();
        configurarEventosBusca();
        
        // Vincula a ação ao botão buscar que foi criado no design
        jButtonBuscar.addActionListener(this::jButtonBuscarActionPerformed);
        
        // O botão de atualizar inicia desativado até que alguma modificação ocorra
        jButtonAtualizarCli.setEnabled(false);
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
            java.util.logging.Logger.getLogger(TelaAtualizarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    });
}

    private void configurarMascaras() {
        try {
            // Máscara do CPF do formulário
            javax.swing.text.MaskFormatter mascaraCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            jFormattedTextFieldCpfCli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCpf));

            // Máscara de Telefone Celular
            javax.swing.text.MaskFormatter mascaraTelefone = new javax.swing.text.MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            jFormattedTextFieldTelefoneCli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraTelefone));

            // Máscara de CEP
            javax.swing.text.MaskFormatter mascaraCep = new javax.swing.text.MaskFormatter("#####-###");
            mascaraCep.setPlaceholderCharacter('_');
            jFormattedTextFieldCepCli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCep));
            
        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(TelaAtualizarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Monitora se o usuário alterar qualquer dado após a busca
    private void configurarEscutadoresDeModificacao() {
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { verificarAlteracao(); }
            @Override public void removeUpdate(DocumentEvent e) { verificarAlteracao(); }
            @Override public void changedUpdate(DocumentEvent e) { verificarAlteracao(); }
        };

        jTextFieldNomeCli.getDocument().addDocumentListener(dl);
        jTextFieldEmailCli.getDocument().addDocumentListener(dl);
        jFormattedTextFieldTelefoneCli.getDocument().addDocumentListener(dl);
        jTextFieldLogradouroCli.getDocument().addDocumentListener(dl);
        jTextFieldNumeroCli.getDocument().addDocumentListener(dl);
        jTextFieldBairroCli.getDocument().addDocumentListener(dl);
        jTextFieldCidadeCli.getDocument().addDocumentListener(dl);
        jFormattedTextFieldCepCli.getDocument().addDocumentListener(dl);
        
        jComboBoxEstadoCli.addActionListener(e -> verificarAlteracao());
    }

    private void verificarAlteracao() {
        if (escutadorAtivo && clienteAtual != null) {
            jButtonAtualizarCli.setEnabled(true);
        }
    }

    // LÓGICA DO BOTÃO BUSCAR (Diferencia se a busca é por ID ou por CPF)
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String termo = jFormattedTextFieldBusca.getText().trim();
        String tipoBusca = jRadioButtonId.isSelected() ? "ID" : "CPF";

        if (termo.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite o ID ou CPF para buscar.", "Campo Vazio", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Faz a chamada ao Controlador que configuramos anteriormente
        clienteAtual = ControladorCliente.getInstanciaControladorCliente().buscarClientePorIdOuCpf(tipoBusca, termo);

        if (clienteAtual == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cliente não encontrado no sistema.", "Não Localizado", javax.swing.JOptionPane.ERROR_MESSAGE);
            jButtonAtualizarCli.setEnabled(false);
            return;
        }

        // Bloqueia o escutador para não ativar o botão de "Atualizar" enquanto os dados entram nos campos
        escutadorAtivo = false;

        jTextFieldNomeCli.setText(clienteAtual.getNome());
        jFormattedTextFieldCpfCli.setText(clienteAtual.getCpf());
        jFormattedTextFieldCpfCli.setEditable(false); // CPF não deve ser alterado em atualizações normais
        jTextFieldEmailCli.setText(clienteAtual.getEmail());
        jFormattedTextFieldTelefoneCli.setText(clienteAtual.getTelefone());
        jTextFieldLogradouroCli.setText(clienteAtual.getEndereco().getLogradouro());
        jTextFieldNumeroCli.setText(clienteAtual.getEndereco().getNumero());
        jTextFieldBairroCli.setText(clienteAtual.getEndereco().getBairro());
        jTextFieldCidadeCli.setText(clienteAtual.getEndereco().getCidade());
        jComboBoxEstadoCli.setSelectedItem(clienteAtual.getEndereco().getEstado());
        jFormattedTextFieldCepCli.setText(clienteAtual.getEndereco().getCep());

        // Libera o escutador. Qualquer modificação que o usuário fizer a partir de agora ativará o botão "Atualizar"
        escutadorAtivo = true;
        jButtonAtualizarCli.setEnabled(false); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNomeCli = new javax.swing.JLabel();
        jLabelCpfCli = new javax.swing.JLabel();
        jLabelEmailCli = new javax.swing.JLabel();
        jLabel1TelefoneCli = new javax.swing.JLabel();
        jLabelLogradouroCli = new javax.swing.JLabel();
        jLabelNumeroCli = new javax.swing.JLabel();
        jLabelBairroCli = new javax.swing.JLabel();
        jLabelCidadeCli = new javax.swing.JLabel();
        jLabelEstadoCli = new javax.swing.JLabel();
        jLabelCepCli = new javax.swing.JLabel();
        jTextFieldNomeCli = new javax.swing.JTextField();
        jTextFieldEmailCli = new javax.swing.JTextField();
        jTextFieldLogradouroCli = new javax.swing.JTextField();
        jTextFieldNumeroCli = new javax.swing.JTextField();
        jTextFieldBairroCli = new javax.swing.JTextField();
        jTextFieldCidadeCli = new javax.swing.JTextField();
        jComboBoxEstadoCli = new javax.swing.JComboBox<>();
        jButtonAtualizarCli = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jFormattedTextFieldCpfCli = new javax.swing.JFormattedTextField();
        jFormattedTextFieldCepCli = new javax.swing.JFormattedTextField();
        jFormattedTextFieldTelefoneCli = new javax.swing.JFormattedTextField();
        jRadioButtonId = new javax.swing.JRadioButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jButtonBuscar = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelNomeCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNomeCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNomeCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNomeCli.setText("Nome");

        jLabelCpfCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpfCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpfCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpfCli.setText("CPF");

        jLabelEmailCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEmailCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEmailCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelEmailCli.setText("E-mail");

        jLabel1TelefoneCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1TelefoneCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1TelefoneCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1TelefoneCli.setText("Telefone");

        jLabelLogradouroCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelLogradouroCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelLogradouroCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLogradouroCli.setText("Logradouro");

        jLabelNumeroCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNumeroCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNumeroCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNumeroCli.setText("Número");

        jLabelBairroCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBairroCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBairroCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBairroCli.setText("Bairro");

        jLabelCidadeCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCidadeCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCidadeCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCidadeCli.setText("Cidade");

        jLabelEstadoCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEstadoCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstadoCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelEstadoCli.setText("Estado");

        jLabelCepCli.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCepCli.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCepCli.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCepCli.setText("CEP");

        jComboBoxEstadoCli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Selecione --", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));

        jButtonAtualizarCli.setBackground(new java.awt.Color(102, 102, 255));
        jButtonAtualizarCli.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAtualizarCli.setText("Atualizar");
        jButtonAtualizarCli.addActionListener(this::jButtonAtualizarCliActionPerformed);

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setText("ATUALIZAR CLIENTES");

        jRadioButtonId.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonId.setText("ID");

        jRadioButtonCpf.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCpf.setText("CPF");

        jButtonBuscar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBuscar.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNomeCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelLogradouroCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCpfCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEmailCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1TelefoneCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNumeroCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelBairroCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCidadeCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEstadoCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelCepCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxEstadoCli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNomeCli)
                            .addComponent(jTextFieldEmailCli)
                            .addComponent(jTextFieldLogradouroCli)
                            .addComponent(jTextFieldNumeroCli)
                            .addComponent(jTextFieldBairroCli)
                            .addComponent(jTextFieldCidadeCli)
                            .addComponent(jFormattedTextFieldCpfCli)
                            .addComponent(jFormattedTextFieldCepCli)
                            .addComponent(jFormattedTextFieldTelefoneCli))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 132, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButtonAtualizarCli)
                                .addGap(132, 132, 132))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelTitulo)
                                .addGap(117, 117, 117))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonId)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonCpf)))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNomeCli)
                    .addComponent(jTextFieldNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCpfCli)
                    .addComponent(jFormattedTextFieldCpfCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmailCli)
                    .addComponent(jTextFieldEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1TelefoneCli)
                    .addComponent(jFormattedTextFieldTelefoneCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogradouroCli)
                    .addComponent(jTextFieldLogradouroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumeroCli)
                    .addComponent(jTextFieldNumeroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBairroCli)
                    .addComponent(jTextFieldBairroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCidadeCli)
                    .addComponent(jTextFieldCidadeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEstadoCli)
                    .addComponent(jComboBoxEstadoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCepCli)
                    .addComponent(jFormattedTextFieldCepCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAtualizarCli)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtualizarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarCliActionPerformed
        String email = jTextFieldEmailCli.getText();
        String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (!email.matches(regexEmail)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, insira um endereço de e-mail válido!", "E-mail Inválido", javax.swing.JOptionPane.ERROR_MESSAGE);
            jTextFieldEmailCli.requestFocus();
            return;
        }

        // Dispara a atualização no controlador usando o ID do objeto guardado
        ControladorCliente.getInstanciaControladorCliente().atualizarClienteGrafico(
            Integer.parseInt(clienteAtual.getIdentificacao()),
            jTextFieldNomeCli.getText(),
            email,
            jFormattedTextFieldTelefoneCli.getText(),
            jTextFieldLogradouroCli.getText(),
            jTextFieldNumeroCli.getText(),
            jTextFieldBairroCli.getText(),
            jTextFieldCidadeCli.getText(),
            jComboBoxEstadoCli.getSelectedItem().toString(),
            jFormattedTextFieldCepCli.getText()
        );

        javax.swing.JOptionPane.showMessageDialog(this, "Cadastro atualizado com sucesso!", "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        // Desabilita de novo até a próxima alteração
        jButtonAtualizarCli.setEnabled(false);
    }//GEN-LAST:event_jButtonAtualizarCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizarCli;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JComboBox<String> jComboBoxEstadoCli;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JFormattedTextField jFormattedTextFieldCepCli;
    private javax.swing.JFormattedTextField jFormattedTextFieldCpfCli;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefoneCli;
    private javax.swing.JLabel jLabel1TelefoneCli;
    private javax.swing.JLabel jLabelBairroCli;
    private javax.swing.JLabel jLabelCepCli;
    private javax.swing.JLabel jLabelCidadeCli;
    private javax.swing.JLabel jLabelCpfCli;
    private javax.swing.JLabel jLabelEmailCli;
    private javax.swing.JLabel jLabelEstadoCli;
    private javax.swing.JLabel jLabelLogradouroCli;
    private javax.swing.JLabel jLabelNomeCli;
    private javax.swing.JLabel jLabelNumeroCli;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    private javax.swing.JTextField jTextFieldBairroCli;
    private javax.swing.JTextField jTextFieldCidadeCli;
    private javax.swing.JTextField jTextFieldEmailCli;
    private javax.swing.JTextField jTextFieldLogradouroCli;
    private javax.swing.JTextField jTextFieldNomeCli;
    private javax.swing.JTextField jTextFieldNumeroCli;
    // End of variables declaration//GEN-END:variables
}
