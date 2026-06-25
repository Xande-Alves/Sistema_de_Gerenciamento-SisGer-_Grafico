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
public class TelaDesligaFuncionario extends javax.swing.JPanel {
    
    private entidades.Funcionario funcionarioAtual; // Armazena o funcionario localizado
    private boolean escutadorAtivo = false;

    /**
     * Creates new form TelaDesligaFuncionario
     */
    public TelaDesligaFuncionario() {
        initComponents();
        
        configurarGrupoBotoes();
        configurarMascaras(); // Inicializa as máscaras internas (salário/data)

        // CORREÇÃO: Força o campo de busca a iniciar sem máscara (já que ID inicia selecionado)
        jFormattedTextFieldBusca.setFormatterFactory(null);
        jFormattedTextFieldBusca.setText("");

        configurarEscutadoresDeModificacao();
        configurarEventosBusca();

        jButtonDesligaFunc.setEnabled(false);
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
            jFormattedTextFieldDataDesliga.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraData));

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
        jFormattedTextFieldDataDesliga.getDocument().addDocumentListener(escutadorCampos);
    }
    
    private void verificarCampos() {
        // Se o escutador não estiver ativo ou o funcionário não foi buscado, mantém desativado
        if (!escutadorAtivo || funcionarioAtual == null) {
            jButtonDesligaFunc.setEnabled(false);
            return;
        }

        // 3. SEPARAÇÃO CORRETA: Apenas verifica se o campo de data foi totalmente preenchido
        // Se todos os números da máscara forem digitados, o getValue() não será nulo.
        boolean dataValida =
            !jFormattedTextFieldDataDesliga.getText().contains("_");

        // O botão só ativa se TODAS as condições forem verdadeiras
        jButtonDesligaFunc.setEnabled(dataValida);
    }
    
    private void limparFormulario() {
        escutadorAtivo = false;
        funcionarioAtual = null;
        jLabelNomeFunc.setText("");
        jLabelCpfFunc.setText("");
        jLabelCargoAtualFunc.setText("");
        jFormattedTextFieldDataDesliga.setText("");
        jFormattedTextFieldBusca.setText("");
        jButtonDesligaFunc.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDesligaFunc = new javax.swing.JButton();
        jLabelNome = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        jRadioButtonId = new javax.swing.JRadioButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jButtonBuscar = new javax.swing.JButton();
        jLabelNomeFunc = new javax.swing.JLabel();
        jLabelCpfFunc = new javax.swing.JLabel();
        jLabelCargoAtual = new javax.swing.JLabel();
        jLabelCargoAtualFunc = new javax.swing.JLabel();
        jLabelCpf = new javax.swing.JLabel();
        jLabelDataDesligamento = new javax.swing.JLabel();
        jFormattedTextFieldDataDesliga = new javax.swing.JFormattedTextField();

        setBackground(new java.awt.Color(153, 153, 255));

        jButtonDesligaFunc.setBackground(new java.awt.Color(255, 51, 51));
        jButtonDesligaFunc.setForeground(new java.awt.Color(0, 0, 0));
        jButtonDesligaFunc.setText("Desligar Funcionário");
        jButtonDesligaFunc.addActionListener(this::jButtonDesligaFuncActionPerformed);

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNome.setText("Nome");

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("DESLIGAR FUNCIONÁRIO");

        jRadioButtonId.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonId.setText("ID");

        jRadioButtonCpf.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCpf.setText("CPF");

        jButtonBuscar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(this::jButtonBuscarActionPerformed);

        jLabelNomeFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNomeFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNomeFunc.setText("          ");

        jLabelCpfFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCpfFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpfFunc.setText("          ");

        jLabelCargoAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCargoAtual.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargoAtual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCargoAtual.setText("Cargo atual");

        jLabelCargoAtualFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCargoAtualFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargoAtualFunc.setText("          ");

        jLabelCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpf.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpf.setText("CPF");

        jLabelDataDesligamento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelDataDesligamento.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDataDesligamento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelDataDesligamento.setText("Desligamento");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButtonId)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonCpf))
                    .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jButtonBuscar))
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelNomeFunc))
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabelCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelCpfFunc))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabelCargoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelCargoAtualFunc))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDataDesligamento, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldDataDesliga, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jButtonDesligaFunc))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelTitulo)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonId)
                            .addComponent(jRadioButtonCpf))
                        .addGap(6, 6, 6)
                        .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jButtonBuscar)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNome)
                    .addComponent(jLabelNomeFunc))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCpf)
                    .addComponent(jLabelCpfFunc))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCargoAtual)
                    .addComponent(jLabelCargoAtualFunc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDataDesligamento)
                    .addComponent(jFormattedTextFieldDataDesliga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonDesligaFunc)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDesligaFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDesligaFuncActionPerformed
        // 3. CONVERTE E VALIDA LOGICAMENTE A DATA AO CLICAR
        java.time.LocalDate dataDesligamento = null;
        Object valorData = jFormattedTextFieldDataDesliga.getValue();

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
            dataDesligamento = java.time.LocalDate.of(ano, mes, dia);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "A data de desligamento informada é inválida!\nPor favor, digite uma data real válida.",
                    "Data Inválida",
                    javax.swing.JOptionPane.ERROR_MESSAGE);

            jFormattedTextFieldDataDesliga.requestFocus();
            return; // Interrompe a contratação
        }
        
        // CHAMA O MÉTODO DO CONTROLADOR
        ControladorFuncionario.getInstanciaControladorFuncionario().desligaFuncionario(funcionarioAtual, dataDesligamento);

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Funcionário desligado com sucesso!",
            "Sucesso",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o sucesso
        limparFormulario();
        jFormattedTextFieldBusca.requestFocus();
    }//GEN-LAST:event_jButtonDesligaFuncActionPerformed

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
            jButtonDesligaFunc.setEnabled(false);
            return;
        }

        jLabelNomeFunc.setText(funcionarioAtual.getNome());
        jLabelCpfFunc.setText(funcionarioAtual.getCpf());
        jLabelCargoAtualFunc.setText(funcionarioAtual.getCargo());
        
        escutadorAtivo = true; // Libera o monitoramento dos campos do formulário
        verificarCampos();     // Executa a primeira verificação do estado atual
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonDesligaFunc;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataDesliga;
    private javax.swing.JLabel jLabelCargoAtual;
    private javax.swing.JLabel jLabelCargoAtualFunc;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelCpfFunc;
    private javax.swing.JLabel jLabelDataDesligamento;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelNomeFunc;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    // End of variables declaration//GEN-END:variables
}
