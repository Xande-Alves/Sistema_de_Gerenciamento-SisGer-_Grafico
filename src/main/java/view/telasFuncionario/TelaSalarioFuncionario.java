/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasFuncionario;

import controladores.ControladorFuncionario;
import javax.swing.ButtonGroup;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Mario
 */
public class TelaSalarioFuncionario extends javax.swing.JPanel {

    private entidades.Funcionario funcionarioAtual; // Armazena o funcionario localizado
    private boolean escutadorAtivo = false;

    /**
     * Creates new form TelaSalarioFuncionario
     */
    public TelaSalarioFuncionario() {
        initComponents();

        configurarGrupoBotoes();
        configurarMascaras(); // Inicializa as máscaras internas (salário/data)

        // CORREÇÃO: Força o campo de busca a iniciar sem máscara (já que ID inicia selecionado)
        jFormattedTextFieldBusca.setFormatterFactory(null);
        jFormattedTextFieldBusca.setText("");

        // Adiciona monitoramento à caixa de seleção do Cargo
        jFormattedTextFieldSalario.addPropertyChangeListener(
                "value",
                evt -> verificarCampos()
        );
        configurarEventosBusca();

        jButtonAlterarSalario.setEnabled(false);
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
            // FORMATADOR MONETÁRIO (CAMPO DE SALÁRIO)
            // =================================================================
            java.text.NumberFormat formatoMoeda = java.text.NumberFormat.getCurrencyInstance();
            formatoMoeda.setMinimumFractionDigits(2);
            formatoMoeda.setMaximumFractionDigits(2);

            javax.swing.text.NumberFormatter formatterSalario = new javax.swing.text.NumberFormatter(formatoMoeda);
            formatterSalario.setAllowsInvalid(false); // Impede digitação de letras
            formatterSalario.setOverwriteMode(true);
            formatterSalario.setCommitsOnValidEdit(true);

            jFormattedTextFieldSalario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatterSalario));

            // Define um valor inicial numérico para evitar erros de inicialização vazia
            jFormattedTextFieldSalario.setValue(0.00);

        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(TelaAtualizarFuncionarios.class.getName())
                    .log(java.util.logging.Level.SEVERE, "Erro ao configurar as máscaras dos campos", ex);
        }
    }

    private void verificarCampos() {
        // Se o escutador não estiver ativo ou o funcionário não foi buscado, mantém desativado
        if (!escutadorAtivo || funcionarioAtual == null) {
            jButtonAlterarSalario.setEnabled(false);
            return;
        }

        // 2. Valida Salário (Verifica se não está vazio ou zerado)
        double salario = 0.0;
        if (jFormattedTextFieldSalario.getValue() != null) {
            salario = ((Number) jFormattedTextFieldSalario.getValue()).doubleValue();
        }
        boolean salarioValido = salario > 0.0;

        jButtonAlterarSalario.setEnabled(salarioValido);
    }

    private void limparFormulario() {
        escutadorAtivo = false;
        funcionarioAtual = null;
        jLabelNomeFunc.setText("");
        jLabelCpfFunc.setText("");
        jLabelCargoAtualFunc.setText("");
        jLabelSalarioAtualFunc.setText("");
        jFormattedTextFieldBusca.setText("");
        jFormattedTextFieldSalario.setValue(0.00);
        jButtonAlterarSalario.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelCargoAtual1 = new javax.swing.JLabel();
        jLabelCargoAtualFunc1 = new javax.swing.JLabel();
        jLabelCpf = new javax.swing.JLabel();
        jButtonAlterarSalario = new javax.swing.JButton();
        jLabelNome = new javax.swing.JLabel();
        jLabelCargo = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        jRadioButtonId = new javax.swing.JRadioButton();
        jRadioButtonCpf = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jButtonBuscar = new javax.swing.JButton();
        jLabelNomeFunc = new javax.swing.JLabel();
        jLabelCpfFunc = new javax.swing.JLabel();
        jLabelCargoAtual = new javax.swing.JLabel();
        jLabelCargoAtualFunc = new javax.swing.JLabel();
        jFormattedTextFieldSalario = new javax.swing.JFormattedTextField();
        jLabelSalarioAtualFunc = new javax.swing.JLabel();
        jLabelSalarioAtual = new javax.swing.JLabel();

        jLabelCargoAtual1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCargoAtual1.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargoAtual1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCargoAtual1.setText("Cargo atual");

        jLabelCargoAtualFunc1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCargoAtualFunc1.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargoAtualFunc1.setText("          ");

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelCpf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCpf.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCpf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCpf.setText("CPF");

        jButtonAlterarSalario.setBackground(new java.awt.Color(102, 102, 255));
        jButtonAlterarSalario.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAlterarSalario.setText("Alterar salário");
        jButtonAlterarSalario.addActionListener(this::jButtonAlterarSalarioActionPerformed);

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNome.setText("Nome");

        jLabelCargo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCargo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCargo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCargo.setText("Novo salário");

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("ALTERAR SALÁRIO");

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

        jLabelSalarioAtualFunc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelSalarioAtualFunc.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSalarioAtualFunc.setText("          ");

        jLabelSalarioAtual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelSalarioAtual.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSalarioAtual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSalarioAtual.setText("Salário atual");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(149, 149, 149)
                .addComponent(jButtonAlterarSalario))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonId)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonCpf))
                            .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabelCargoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelSalarioAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSalarioAtualFunc)
                    .addComponent(jLabelCargoAtualFunc)))
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
                    .addComponent(jLabelSalarioAtualFunc)
                    .addComponent(jLabelSalarioAtual))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCargo))
                .addGap(18, 18, 18)
                .addComponent(jButtonAlterarSalario)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAlterarSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarSalarioActionPerformed
        // 2. RECUPERA O SALÁRIO (Convertendo de Number para double)
        double salario = 0.0;
        if (jFormattedTextFieldSalario.getValue() != null) {
            salario = ((Number) jFormattedTextFieldSalario.getValue()).doubleValue();
        }

        // CHAMA O MÉTODO DO CONTROLADOR
        ControladorFuncionario.getInstanciaControladorFuncionario().alteraSalario(funcionarioAtual, salario);

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Salário do Funcionário alterado com sucesso!",
                "Sucesso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o sucesso
        limparFormulario();
        jFormattedTextFieldBusca.requestFocus();
    }//GEN-LAST:event_jButtonAlterarSalarioActionPerformed

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
            jButtonAlterarSalario.setEnabled(false);
            return;
        }

        escutadorAtivo = false; // Bloqueia validações enquanto preenche o cabeçalho

        jLabelNomeFunc.setText(funcionarioAtual.getNome());
        jLabelCpfFunc.setText(funcionarioAtual.getCpf());
        jLabelCargoAtualFunc.setText(funcionarioAtual.getCargo());
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        jLabelSalarioAtualFunc.setText(formatoMoeda.format(funcionarioAtual.getSalario()));

        escutadorAtivo = true; // Libera o monitoramento dos campos do formulário
        verificarCampos();     // Executa a primeira verificação do estado atual
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterarSalario;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JFormattedTextField jFormattedTextFieldSalario;
    private javax.swing.JLabel jLabelCargo;
    private javax.swing.JLabel jLabelCargoAtual;
    private javax.swing.JLabel jLabelCargoAtual1;
    private javax.swing.JLabel jLabelCargoAtualFunc;
    private javax.swing.JLabel jLabelCargoAtualFunc1;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelCpfFunc;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelNomeFunc;
    private javax.swing.JLabel jLabelSalarioAtual;
    private javax.swing.JLabel jLabelSalarioAtualFunc;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonCpf;
    private javax.swing.JRadioButton jRadioButtonId;
    // End of variables declaration//GEN-END:variables
}
