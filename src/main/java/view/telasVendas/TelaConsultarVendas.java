/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasVendas;

import entidades.Venda;
import controladores.ControladorVenda;

/**
 *
 * @author Mario
 */
public class TelaConsultarVendas extends javax.swing.JPanel {

    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;
    private java.util.List<entidades.Venda> listaVendas;

    /**
     * Creates new form TelaConsultarVendas
     */
    public TelaConsultarVendas() {
        initComponents();

        // Mantém exatamente todos os seus métodos anteriores intactos:
        configurarGrupoBotoes();
        configurarSorterTabela();
        configurarFiltroTempoReal();
        configurarLimpezaBusca();

        carregarTabela();

        configurarCliqueTabela();

        // Garante que a tabela inicia 100% vazia
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaVendas.getModel();
        modelo.setRowCount(0);

    }

    // Agrupa os botões para exclusividade
    private void configurarGrupoBotoes() {
        javax.swing.ButtonGroup grupoConsulta = new javax.swing.ButtonGroup();
        grupoConsulta.add(jRadioButtonIdVenda);
        grupoConsulta.add(jRadioButtonIdCliente);
        grupoConsulta.add(jRadioButtonIdVendedor);

        jRadioButtonIdVenda.setSelected(true);
    }

    // Inicializa o ordenador/filtrador na JTable
    private void configurarSorterTabela() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaVendas.getModel();
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTableListaVendas.setRowSorter(sorter);
    }

    public void carregarTabela() {
        // 1. Pega a lista de funcionarios do seu repositório
        listaVendas = repositorio.Repositorio.getInstanciaRepositorio().getListaVenda();

        // 2. Captura o modelo da tabela para manipular as linhas
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaVendas.getModel();

        // Limpa a tabela antes de carregar
        modelo.setNumRows(0);

        // 3. Varre a lista de funcionarios e adiciona cada um como uma nova linha
        for (entidades.Venda v : listaVendas) {
            modelo.addRow(new Object[]{
                v.getIdVenda(),
                v.getIdClienteVenda(),
                v.getIdVendedorVenda(),
                v.getDataVenda(),
                v.getVendaAtiva() ? "Ativa" : "Cancelada",
                v.getValorTotalVenda()
            });
        }
    }

    private void configurarCliqueTabela() {

        jTableListaVendas.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (evt.getClickCount() == 2) {

                    int linha = jTableListaVendas.getSelectedRow();

                    if (linha != -1) {

                        // Se houver RowSorter, converte para o índice do modelo
                        linha = jTableListaVendas.convertRowIndexToModel(linha);

                        int idVenda = (Integer) jTableListaVendas.getModel().getValueAt(linha, 0);

                        Venda vendaSelecionada
                                = ControladorVenda.getInstanciaControladorVenda()
                                        .buscarVendaCopia(idVenda);

                        abrirDetalhesVenda(vendaSelecionada);
                    }

                }

            }

        });

    }

    private void abrirDetalhesVenda(entidades.Venda venda) {

        java.awt.Frame frame
                = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);

        DialogListaProdutosVenda dialog
                = new DialogListaProdutosVenda(frame, venda);

        dialog.setVisible(true);

    }

    // Escuta o teclado e aplica o filtro na tabela em tempo real
    private void configurarFiltroTempoReal() {
        jFormattedTextFieldBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = jFormattedTextFieldBusca.getText().trim();
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaVendas.getModel();

                // Se o campo estiver vazio ou redefinido para a máscara vazia, zera a tabela e para
                if (texto.isEmpty()) {
                    modelo.setRowCount(0);
                    return;
                }

                // Busca a lista atualizada direto do seu controlador
                java.util.List<entidades.Venda> listaCompleta = repositorio.Repositorio.getInstanciaRepositorio().getListaVenda();
                modelo.setRowCount(0); // Limpa para renderizar os novos resultados correspondentes

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Venda v : listaCompleta) {
                    boolean atendeAoFiltro = false;

                    if (jRadioButtonIdVenda.isSelected()) {
                        try {
                            int idVenda = Integer.parseInt(texto);
                            atendeAoFiltro = v.getIdVenda() == idVenda;
                        } catch (NumberFormatException e) {
                            atendeAoFiltro = false;
                        }
                    } else if (jRadioButtonIdCliente.isSelected()) {
                        try {
                            int idCliente = Integer.parseInt(texto);
                            atendeAoFiltro = v.getIdClienteVenda() == idCliente;
                        } catch (NumberFormatException e) {
                            atendeAoFiltro = false;
                        }
                    } else if (jRadioButtonIdVendedor.isSelected()) {
                        try {
                            int idVendedor = Integer.parseInt(texto);
                            atendeAoFiltro = v.getIdVendedorVenda() == idVendedor;
                        } catch (NumberFormatException e) {
                            atendeAoFiltro = false;
                        }
                    }

                    // Se o funcionario atual bater com o critério digitado, joga ele na tabela
                    if (atendeAoFiltro) {
                        modelo.addRow(new Object[]{
                            v.getIdVenda(),
                            v.getIdClienteVenda(),
                            v.getIdVendedorVenda(),
                            v.getDataVenda(),
                            v.getVendaAtiva() ? "Ativa" : "Cancelada",
                            v.getValorTotalVenda()
                        }
                        );
                    }
                }
            }
        }
        );
    }
    
    private void configurarLimpezaBusca() {

    java.awt.event.ActionListener limpar = e -> {
        jFormattedTextFieldBusca.setText("");

        // Opcional: limpa também a tabela
        ((javax.swing.table.DefaultTableModel) jTableListaVendas.getModel())
                .setRowCount(0);
    };

    jRadioButtonIdVenda.addActionListener(limpar);
    jRadioButtonIdCliente.addActionListener(limpar);
    jRadioButtonIdVendedor.addActionListener(limpar);
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
        jRadioButtonIdVenda = new javax.swing.JRadioButton();
        jRadioButtonIdCliente = new javax.swing.JRadioButton();
        jRadioButtonIdVendedor = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaVendas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 255));
        setLayout(new java.awt.GridBagLayout());

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR VENDAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 6, 0, 0);
        add(jLabelTitulo, gridBagConstraints);

        jRadioButtonIdVenda.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonIdVenda.setText("ID venda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jRadioButtonIdVenda, gridBagConstraints);

        jRadioButtonIdCliente.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonIdCliente.setText("ID Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        add(jRadioButtonIdCliente, gridBagConstraints);

        jRadioButtonIdVendedor.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonIdVendedor.setText("ID Vendedor");
        jRadioButtonIdVendedor.setActionCommand("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jRadioButtonIdVendedor, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 232;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jFormattedTextFieldBusca, gridBagConstraints);

        jTableListaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Venda", "ID Cliente", "ID Vendedor", "Data", "Situação", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaVendas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 372;
        gridBagConstraints.ipady = 161;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 6, 9, 6);
        add(jScrollPane1, gridBagConstraints);

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Duplo click na linha da venda para abrir sua lista de produtos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        add(jLabel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonIdCliente;
    private javax.swing.JRadioButton jRadioButtonIdVenda;
    private javax.swing.JRadioButton jRadioButtonIdVendedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaVendas;
    // End of variables declaration//GEN-END:variables
}
