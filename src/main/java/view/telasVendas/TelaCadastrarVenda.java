/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasVendas;

import entidades.Cliente;
import entidades.Funcionario;
import entidades.Produto;
import entidades.Venda;
import java.awt.Frame;
import javax.swing.SwingUtilities;
import view.telasCliente.DialogConsultaCliente;
import view.telasFornecedor.DialogConsultaFuncionario;
import view.telasProduto.DialogConsultaProduto;

/**
 *
 * @author Mario
 */
public class TelaCadastrarVenda extends javax.swing.JPanel {

    Venda vendaAtual = new Venda(0, 0, 0);

    /**
     * Creates new form TelaCadastrarVenda
     */
    public TelaCadastrarVenda() {
        initComponents();

        configurarListenerTabela();

        // Configuração dos spinners 
        jSpinnerIdCliente.setModel(new javax.swing.SpinnerNumberModel(
                0, // valor inicial
                0, // valor mínimo
                null, // valor máximo (sem limite)
                1 // incremento
        ));

        jSpinnerIdVendedor.setModel(new javax.swing.SpinnerNumberModel(
                0, // valor inicial
                0, // valor mínimo
                null, // valor máximo (sem limite)
                1 // incremento
        ));

        jSpinnerIdProduto.setModel(new javax.swing.SpinnerNumberModel(
                0, // valor inicial
                0, // valor mínimo
                null, // valor máximo (sem limite)
                1 // incremento
        ));
    }

    public void carregarTabela() {
        // 1. Pega a lista de funcionarios do seu repositório
        java.util.List<entidades.Produto> lista = vendaAtual.getListaProdutosVenda();

        // 2. Captura o modelo da tabela para manipular as linhas
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutosVenda.getModel();

        // Limpa a tabela antes de carregar
        modelo.setNumRows(0);

        // 3. Varre a lista de funcionarios e adiciona cada um como uma nova linha
        for (entidades.Produto produto : lista) {
            modelo.addRow(new Object[]{
                produto.getIdProduto(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoVenda(),
                produto.getQuantidadeEstoque(),
                produto.getQuantidade(),
                produto.getQuantidade() * produto.getPrecoVenda()
            });
        }
    }

    private void configurarListenerTabela() {

        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTableListaProdutosVenda.getModel();

        modelo.addTableModelListener(e -> {

            // Apenas quando editar a coluna Quantidade de Venda
            if (e.getColumn() == 5) {

                int linha = e.getFirstRow();

                try {

                    Object valor = modelo.getValueAt(linha, 5);

                    double quantidade = Double.parseDouble(valor.toString());

                    Produto produto = vendaAtual.getListaProdutosVenda().get(linha);

                    // Atualiza o objeto
                    produto.setQuantidade(quantidade);

                    // AVISA SOBRE A QUANTIDADE DE VENDA MAIOR QUE A DE ESTOQUE
                    if (produto.getQuantidade() > produto.getQuantidadeEstoque()) {

                        javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "O produto \"" + produto.getNome()
                                + "\" possui apenas "
                                + produto.getQuantidadeEstoque()
                                + " unidades em estoque.",
                                "Estoque insuficiente",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );
                    }

                    // Calcula o total
                    double total = produto.getPrecoVenda() * quantidade;
                    
                    // Atualiza o total da venda
                    double valorTotalVenda = controladores.ControladorVenda.getInstanciaControladorVenda().calcularValorTotalVenda(vendaAtual);
                    vendaAtual.setValorTotalVenda(valorTotalVenda);
                    jLabelvalorTotalVenda.setText(String.valueOf(valorTotalVenda));

                    // Atualiza a coluna Total Item
                    modelo.setValueAt(total, linha, 6);

                } catch (NumberFormatException ex) {

                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Quantidade inválida.");

                    modelo.setValueAt(0, linha, 5);
                    modelo.setValueAt(0.0, linha, 6);
                }

            }

        });

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

        jLabelTitulo = new javax.swing.JLabel();
        jLabelIdCliente = new javax.swing.JLabel();
        jSpinnerIdCliente = new javax.swing.JSpinner();
        jButtonCadastrar = new javax.swing.JButton();
        jLabelIdVendedor = new javax.swing.JLabel();
        jSpinnerIdVendedor = new javax.swing.JSpinner();
        jLabelIdProduto = new javax.swing.JLabel();
        jSpinnerIdProduto = new javax.swing.JSpinner();
        jButtonConsultarCliente = new javax.swing.JButton();
        jButtonConsultarVendedor = new javax.swing.JButton();
        jButtonConsultarProduto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaProdutosVenda = new javax.swing.JTable();
        jButtonAdicionarProduto = new javax.swing.JButton();
        jLabelTotalVenda = new javax.swing.JLabel();
        jLabelvalorTotalVenda = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 255));
        setLayout(new java.awt.GridBagLayout());

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CADASTRAR VENDA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 215;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(jLabelTitulo, gridBagConstraints);

        jLabelIdCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdCliente.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdCliente.setText("ID Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        add(jLabelIdCliente, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        add(jSpinnerIdCliente, gridBagConstraints);

        jButtonCadastrar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonCadastrar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCadastrar.setText("Cadastrar Venda");
        jButtonCadastrar.addActionListener(this::jButtonCadastrarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 35, 6, 0);
        add(jButtonCadastrar, gridBagConstraints);

        jLabelIdVendedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdVendedor.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdVendedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdVendedor.setText("ID Vendedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 0, 0);
        add(jLabelIdVendedor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 0, 0);
        add(jSpinnerIdVendedor, gridBagConstraints);

        jLabelIdProduto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdProduto.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdProduto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdProduto.setText("ID Produto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelIdProduto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jSpinnerIdProduto, gridBagConstraints);

        jButtonConsultarCliente.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarCliente.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarCliente.setText("Consultar");
        jButtonConsultarCliente.addActionListener(this::jButtonConsultarClienteActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        add(jButtonConsultarCliente, gridBagConstraints);

        jButtonConsultarVendedor.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarVendedor.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarVendedor.setText("Consultar");
        jButtonConsultarVendedor.addActionListener(this::jButtonConsultarVendedorActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(jButtonConsultarVendedor, gridBagConstraints);

        jButtonConsultarProduto.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarProduto.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarProduto.setText("Consultar");
        jButtonConsultarProduto.addActionListener(this::jButtonConsultarProdutoActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jButtonConsultarProduto, gridBagConstraints);

        jTableListaProdutosVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Produto", "Nome", "Descrição", "Preço de Venda", "Quantidade em Estoque", "Quantidade de Venda", "Total Item"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaProdutosVenda);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 372;
        gridBagConstraints.ipady = 129;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 6, 0, 6);
        add(jScrollPane1, gridBagConstraints);

        jButtonAdicionarProduto.setBackground(new java.awt.Color(51, 204, 255));
        jButtonAdicionarProduto.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAdicionarProduto.setText("Adicionar");
        jButtonAdicionarProduto.addActionListener(this::jButtonAdicionarProdutoActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jButtonAdicionarProduto, gridBagConstraints);

        jLabelTotalVenda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelTotalVenda.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTotalVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalVenda.setText("VALOR TOTAL R$");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelTotalVenda, gridBagConstraints);

        jLabelvalorTotalVenda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelvalorTotalVenda.setForeground(new java.awt.Color(0, 0, 0));
        jLabelvalorTotalVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelvalorTotalVenda.setText("        ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelvalorTotalVenda, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarActionPerformed
        int idCliente = (Integer) jSpinnerIdCliente.getValue();
        int idVendedor = (Integer) jSpinnerIdVendedor.getValue();

        if (jTableListaProdutosVenda.isEditing()) {
            jTableListaProdutosVenda.getCellEditor().stopCellEditing();
        }

        // =================================================================
        // VALIDAÇÃO: VERIFICAÇÃO DE CAMPOS VAZIOS
        // =================================================================
        if (idCliente <= 0 || idVendedor <= 0 || vendaAtual.getListaProdutosVenda().size() <= 0) {

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, preencha os campos de ID de cliente, vendedor e adicione ao menos um produto à venda!",
                    "Campos Incompletos",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução aqui se faltar algo
        }

        //VALIDA SE EXISTE CLIENTE
        Cliente clienteVenda = controladores.ControladorCliente.getInstanciaControladorCliente().buscarClientePorIdOuCpf("ID", String.valueOf(idCliente));

        if (clienteVenda == null) {

            javax.swing.JOptionPane.showMessageDialog(this,
                    "ID de cliente inexistente!",
                    "ID cliente inexistente",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
        }

        //VALIDA SE EXISTE FUNCIONÁRIO E SE ELE É VENDEDOR
        Funcionario vendedorVenda = controladores.ControladorFuncionario.getInstanciaControladorFuncionario().buscarFuncionarioPorIdOuCpf("ID", String.valueOf(idVendedor));

        boolean ehVendedor = controladores.ControladorLogin.getInstanciaControladorLogin().verificaVendedorParaVenda(vendedorVenda);

        if ((vendedorVenda == null) || (!ehVendedor)) {

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Funcionário não existe ou não é vendedor!",
                    "ID vendedor inexistente",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
        }

        //VALIDA SE TODOS OS CAMPOS DE QUANTIDADE DE PRODUTOS FORAM PREENCHIDOS
        for (Produto produto : vendaAtual.getListaProdutosVenda()) {

            if (produto.getQuantidade() <= 0) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Informe a quantidade de venda para todos os produtos.",
                        "Quantidade não informada",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        // 1. Envia os dados para salvar
        int idVenda = controladores.ControladorVenda.getInstanciaControladorVenda().efetuarVenda(vendaAtual, idCliente, idVendedor, vendaAtual.getListaProdutosVenda());

        // =================================================================
        // NOVOS COMPORTAMENTOS: AVISO E LIMPEZA
        // =================================================================
        // 2. Mostra a caixinha de aviso de Sucesso
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Venda cadastrada com sucesso no ID " + idVenda,
                "Sucesso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        
        // Limpa a venda atual
        vendaAtual = new Venda(0, 0, 0);

        // 3. Limpa todos os campos da tela
        jSpinnerIdCliente.setValue(0);
        jSpinnerIdVendedor.setValue(0);
        jSpinnerIdProduto.setValue(0);
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutosVenda.getModel();
        modelo.setRowCount(0);

        jSpinnerIdCliente.requestFocus();
    }//GEN-LAST:event_jButtonCadastrarActionPerformed

    private void jButtonConsultarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarClienteActionPerformed

        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

        DialogConsultaCliente dialog = new DialogConsultaCliente(frame);
        dialog.setVisible(true);

    }//GEN-LAST:event_jButtonConsultarClienteActionPerformed

    private void jButtonConsultarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarVendedorActionPerformed
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

        DialogConsultaFuncionario dialog = new DialogConsultaFuncionario(frame);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButtonConsultarVendedorActionPerformed

    private void jButtonConsultarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarProdutoActionPerformed
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

        DialogConsultaProduto dialog = new DialogConsultaProduto(frame);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButtonConsultarProdutoActionPerformed

    private void jButtonAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarProdutoActionPerformed
        int idProduto = (Integer) jSpinnerIdProduto.getValue();
        Produto original = controladores.ControladorProduto.getInstanciaControladorProduto().buscarProdutos(idProduto);

        if (original == null) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "ID de produto inexistente.",
                    "Produto inexistente",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Produto copia = new Produto(original.getIdProduto(), original.getIdFornecedor(), original.getNome(), original.getDescricao(), original.getPrecoCompra(), original.getPrecoVenda(), original.getQuantidadeEstoque());

        copia.setQuantidade(0.0);

        vendaAtual.getListaProdutosVenda().add(copia);

        carregarTabela();
    }//GEN-LAST:event_jButtonAdicionarProdutoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionarProduto;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonConsultarCliente;
    private javax.swing.JButton jButtonConsultarProduto;
    private javax.swing.JButton jButtonConsultarVendedor;
    private javax.swing.JLabel jLabelIdCliente;
    private javax.swing.JLabel jLabelIdProduto;
    private javax.swing.JLabel jLabelIdVendedor;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTotalVenda;
    private javax.swing.JLabel jLabelvalorTotalVenda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerIdCliente;
    private javax.swing.JSpinner jSpinnerIdProduto;
    private javax.swing.JSpinner jSpinnerIdVendedor;
    private javax.swing.JTable jTableListaProdutosVenda;
    // End of variables declaration//GEN-END:variables
}
