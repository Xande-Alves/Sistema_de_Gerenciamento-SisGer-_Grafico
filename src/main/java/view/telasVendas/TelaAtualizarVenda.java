/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasVendas;

import controladores.ControladorVenda;
import controladores.ControladorProduto;
import entidades.Cliente;
import entidades.Funcionario;
import entidades.Produto;
import java.awt.Frame;
import javax.swing.SwingUtilities;
import view.telasCliente.DialogConsultaCliente;
import view.telasFornecedor.DialogConsultaFuncionario;
import view.telasProduto.DialogConsultaProduto;

/**
 *
 * @author Mario
 */
public class TelaAtualizarVenda extends javax.swing.JPanel {

    private entidades.Venda vendaAtual;
    private entidades.Venda vendaOriginal;
    private boolean escutadorAtivo = false;  // Controla para não ativar o botão enquanto carrega os dados
    private boolean atualizandoTabela = false;

    /**
     * Creates new form TelaAtualizarVenda
     */
    public TelaAtualizarVenda() {
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

        jSpinnerIdVenda.setModel(new javax.swing.SpinnerNumberModel(
                0, // valor inicial
                0, // valor mínimo
                null, // valor máximo (sem limite)
                1 // incremento
        ));

        configurarEscutadoresDeModificacao();

        // O botão de atualizar inicia desativado até que alguma modificação ocorra
        jButtonAtualizar.setEnabled(false);
    }

    public void carregarTabela() {

        atualizandoTabela = true;

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

        atualizandoTabela = false;
    }

    private void configurarListenerTabela() {

        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTableListaProdutosVenda.getModel();

        modelo.addTableModelListener(e -> {

            if (atualizandoTabela) {
                return;
            }

            // Apenas quando editar a coluna Quantidade de Venda
            if (e.getColumn() == 5) {

                int linha = e.getFirstRow();

                if (linha < 0 || linha >= vendaAtual.getListaProdutosVenda().size()) {
                    return;
                }

                try {

                    Object valor = modelo.getValueAt(linha, 5);

                    double quantidade = Double.parseDouble(valor.toString());

                    Produto produtoAtual = vendaAtual.getListaProdutosVenda().get(linha);

                    // Atualiza o objeto
                    produtoAtual.setQuantidade(quantidade);

                    // AVISA SOBRE A QUANTIDADE DE VENDA MAIOR QUE A DE ESTOQUE
                    if (produtoAtual.getQuantidade() > produtoAtual.getQuantidadeEstoque()) {

                        javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "O produto \"" + produtoAtual.getNome()
                                + "\" possui apenas "
                                + produtoAtual.getQuantidadeEstoque()
                                + " unidades em estoque.",
                                "Estoque insuficiente",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );
                    }

                    // Calcula o total
                    double total = produtoAtual.getPrecoVenda() * quantidade;

                    // Atualiza o total da venda
                    double valorTotalVenda = controladores.ControladorVenda.getInstanciaControladorVenda().calcularValorTotalVenda(vendaAtual);
                    vendaAtual.setValorTotalVenda(valorTotalVenda);
                    jLabelvalorTotalVenda.setText(String.valueOf(valorTotalVenda));

                    // Atualiza a coluna Total Item
                    atualizandoTabela = true;
                    modelo.setValueAt(total, linha, 6);
                    atualizandoTabela = false;

                    // Habilita o botão Atualizar
                    verificarAlteracao();

                } catch (NumberFormatException ex) {

                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Quantidade inválida.");

                    modelo.setValueAt(0, linha, 5);
                    modelo.setValueAt(0.0, linha, 6);
                }

            }

        });

    }

    // Monitora se o usuário alterar qualquer dado após a busca
    private void configurarEscutadoresDeModificacao() {
        // Listener específico do spinner
        jSpinnerIdCliente.addChangeListener(e -> verificarAlteracao());
        jSpinnerIdVendedor.addChangeListener(e -> verificarAlteracao());
    }

    private void verificarAlteracao() {
        if (escutadorAtivo && vendaAtual != null) {
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelTitulo = new javax.swing.JLabel();
        jLabelIdCliente = new javax.swing.JLabel();
        jSpinnerIdCliente = new javax.swing.JSpinner();
        jButtonAtualizar = new javax.swing.JButton();
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
        jLabelIdIdVenda = new javax.swing.JLabel();
        jSpinnerIdVenda = new javax.swing.JSpinner();
        jButtonBuscar2 = new javax.swing.JButton();
        jButtonDeletaProdutoVenda = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 153, 255));
        setLayout(new java.awt.GridBagLayout());

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("ATUALIZAR VENDA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 50;
        gridBagConstraints.ipadx = 307;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabelTitulo, gridBagConstraints);

        jLabelIdCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdCliente.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdCliente.setText("ID Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 6, 0, 0);
        add(jLabelIdCliente, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 6, 0, 0);
        add(jSpinnerIdCliente, gridBagConstraints);

        jButtonAtualizar.setBackground(new java.awt.Color(102, 102, 255));
        jButtonAtualizar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAtualizar.setText("Atualizar Venda");
        jButtonAtualizar.addActionListener(this::jButtonAtualizarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 25;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 22, 6, 0);
        add(jButtonAtualizar, gridBagConstraints);

        jLabelIdVendedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdVendedor.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdVendedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdVendedor.setText("ID Vendedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 0, 0);
        add(jLabelIdVendedor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 0, 0);
        add(jSpinnerIdVendedor, gridBagConstraints);

        jLabelIdProduto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdProduto.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdProduto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdProduto.setText("ID Produto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelIdProduto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 7, 0, 0);
        add(jSpinnerIdProduto, gridBagConstraints);

        jButtonConsultarCliente.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarCliente.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarCliente.setText("Consultar");
        jButtonConsultarCliente.addActionListener(this::jButtonConsultarClienteActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 26;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 11, 0, 0);
        add(jButtonConsultarCliente, gridBagConstraints);

        jButtonConsultarVendedor.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarVendedor.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarVendedor.setText("Consultar");
        jButtonConsultarVendedor.addActionListener(this::jButtonConsultarVendedorActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 26;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 11, 0, 0);
        add(jButtonConsultarVendedor, gridBagConstraints);

        jButtonConsultarProduto.setBackground(new java.awt.Color(102, 102, 255));
        jButtonConsultarProduto.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConsultarProduto.setText("Consultar");
        jButtonConsultarProduto.addActionListener(this::jButtonConsultarProdutoActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 27;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
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
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 50;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 426;
        gridBagConstraints.ipady = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 0);
        add(jScrollPane1, gridBagConstraints);

        jButtonAdicionarProduto.setBackground(new java.awt.Color(51, 204, 255));
        jButtonAdicionarProduto.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAdicionarProduto.setText("Adicionar");
        jButtonAdicionarProduto.addActionListener(this::jButtonAdicionarProdutoActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 47;
        gridBagConstraints.gridy = 10;
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelTotalVenda, gridBagConstraints);

        jLabelvalorTotalVenda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelvalorTotalVenda.setForeground(new java.awt.Color(0, 0, 0));
        jLabelvalorTotalVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelvalorTotalVenda.setText("        ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLabelvalorTotalVenda, gridBagConstraints);

        jLabelIdIdVenda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdIdVenda.setForeground(new java.awt.Color(0, 0, 0));
        jLabelIdIdVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdIdVenda.setText("ID Venda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        add(jLabelIdIdVenda, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        add(jSpinnerIdVenda, gridBagConstraints);

        jButtonBuscar2.setBackground(new java.awt.Color(102, 102, 255));
        jButtonBuscar2.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBuscar2.setText("Buscar");
        jButtonBuscar2.addActionListener(this::jButtonBuscar2ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 11, 0, 0);
        add(jButtonBuscar2, gridBagConstraints);

        jButtonDeletaProdutoVenda.setBackground(new java.awt.Color(255, 51, 51));
        jButtonDeletaProdutoVenda.setForeground(new java.awt.Color(0, 0, 0));
        jButtonDeletaProdutoVenda.setText("Remove");
        jButtonDeletaProdutoVenda.addActionListener(this::jButtonDeletaProdutoVendaActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 48;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jButtonDeletaProdutoVenda, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
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

        //ATUALIZA O ESTOQUE
        controladores.ControladorEstoque.getInstanciaControladorEstoque().alteraQuantidadeEstoqueVenda(vendaAtual, vendaOriginal);

        // 1. Envia os dados para salvar
        controladores.ControladorVenda.getInstanciaControladorVenda().atualizarVenda(vendaAtual, idCliente, idVendedor, vendaAtual.getListaProdutosVenda(), vendaAtual.getValorTotalVenda());

        // =================================================================
        // NOVOS COMPORTAMENTOS: AVISO E LIMPEZA
        // =================================================================
        // 2. Mostra a caixinha de aviso de Sucesso
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Venda atualizada com sucesso!",
                "Sucesso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        jSpinnerIdVenda.requestFocus();
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

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
        verificarAlteracao();
    }//GEN-LAST:event_jButtonAdicionarProdutoActionPerformed

    private void jButtonBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscar2ActionPerformed
        int idVenda = (Integer) jSpinnerIdVenda.getValue();

        if (idVenda <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, digite o ID da venda para buscar.", "Campo Vazio", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Faz a chamada ao Controlador que configuramos anteriormente
        vendaAtual = ControladorVenda.getInstanciaControladorVenda().buscarVendaCopia(idVenda);
        vendaOriginal = ControladorVenda.getInstanciaControladorVenda().buscarVendaRepositorio(idVenda);

        if (vendaAtual == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Venda não encontrada no sistema.", "Não Localizada", javax.swing.JOptionPane.ERROR_MESSAGE);
            jButtonAtualizar.setEnabled(false);
            return;
        }

        // Bloqueia o escutador para não ativar o botão de "Atualizar" enquanto os dados entram nos campos
        escutadorAtivo = false;

        jSpinnerIdCliente.setValue(vendaAtual.getIdClienteVenda());
        jSpinnerIdVendedor.setValue(vendaAtual.getIdVendedorVenda());
        carregarTabela();
        jLabelvalorTotalVenda.setText(String.valueOf(vendaAtual.getValorTotalVenda()));

        // Libera o escutador. Qualquer modificação que o usuário fizer a partir de agora ativará o botão "Atualizar"
        escutadorAtivo = true;
        jButtonAtualizar.setEnabled(false);
    }//GEN-LAST:event_jButtonBuscar2ActionPerformed

    private void jButtonDeletaProdutoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletaProdutoVendaActionPerformed
        if (jTableListaProdutosVenda.isEditing()) {
            jTableListaProdutosVenda.getCellEditor().stopCellEditing();
        }

        int linha = jTableListaProdutosVenda.getSelectedRow();

        if (linha == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto para remover.",
                    "Nenhum produto selecionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Produto produto = vendaAtual.getListaProdutosVenda().get(linha);

        int resposta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Deseja remover o produto \"" + produto.getNome() + "\" da venda?",
                "Confirmar remoção",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (resposta == javax.swing.JOptionPane.YES_OPTION) {

            vendaAtual.getListaProdutosVenda().remove(linha);

            // Recalcula o valor total da venda
            double totalVenda = ControladorVenda.getInstanciaControladorVenda()
                    .calcularValorTotalVenda(vendaAtual);

            vendaAtual.setValorTotalVenda(totalVenda);
            jLabelvalorTotalVenda.setText(String.valueOf(totalVenda));

            // Atualiza a tabela
            carregarTabela();

            // Habilita o botão Atualizar
            verificarAlteracao();
        }
    }//GEN-LAST:event_jButtonDeletaProdutoVendaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionarProduto;
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonBuscar1;
    private javax.swing.JButton jButtonBuscar2;
    private javax.swing.JButton jButtonConsultarCliente;
    private javax.swing.JButton jButtonConsultarProduto;
    private javax.swing.JButton jButtonConsultarVendedor;
    private javax.swing.JButton jButtonDeletaProdutoVenda;
    private javax.swing.JLabel jLabelIdCliente;
    private javax.swing.JLabel jLabelIdIdVenda;
    private javax.swing.JLabel jLabelIdProduto;
    private javax.swing.JLabel jLabelIdVendedor;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTotalVenda;
    private javax.swing.JLabel jLabelvalorTotalVenda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerIdCliente;
    private javax.swing.JSpinner jSpinnerIdProduto;
    private javax.swing.JSpinner jSpinnerIdVenda;
    private javax.swing.JSpinner jSpinnerIdVendedor;
    private javax.swing.JTable jTableListaProdutosVenda;
    // End of variables declaration//GEN-END:variables
}
