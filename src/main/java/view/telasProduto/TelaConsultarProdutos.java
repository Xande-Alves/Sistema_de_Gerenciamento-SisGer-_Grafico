/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasProduto;

/**
 *
 * @author Mario
 */
public class TelaConsultarProdutos extends javax.swing.JPanel {

    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form TelaConsultarProdutos
     */
    public TelaConsultarProdutos() {
        initComponents();

        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Mantém exatamente todos os seus métodos anteriores intactos:
        configurarGrupoBotoes();
        configurarSorterTabela();
        configurarFiltroTempoReal();

        // Garante que a tabela inicia 100% vazia
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();
        modelo.setRowCount(0);
    }

    // Agrupa os botões para exclusividade
    private void configurarGrupoBotoes() {
        javax.swing.ButtonGroup grupoConsulta = new javax.swing.ButtonGroup();
        grupoConsulta.add(jRadioButtonNome);
        grupoConsulta.add(jRadioButtonIdFornecedor);
        grupoConsulta.add(jRadioButtonDescricao);

        jRadioButtonNome.setSelected(true);
    }

    // Inicializa o ordenador/filtrador na JTable
    private void configurarSorterTabela() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTableListaProdutos.setRowSorter(sorter);
    }

    public void popularTabelaProdutos() {
        // 1. Pega o modelo da tabela e limpa qualquer linha residual que venha do design
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();
        modelo.setRowCount(0);

        // 2. Busca a lista completa de funcionarios através do seu Controlador
        java.util.List<entidades.Produto> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaProduto();

        // 3. Varre a lista adicionando cada funcionarios como uma nova linha na tabela
        if (lista != null) {
            for (entidades.Produto p : lista) {
                modelo.addRow(new Object[]{
                    p.getIdProduto(),
                    p.getIdFornecedor(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getPrecoCompra(),
                    p.getPrecoVenda(),
                    p.getQuantidadeEstoque()
                });
            }
        }
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
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();

                // Se o campo estiver vazio ou redefinido para a máscara vazia, zera a tabela e para
                if (texto.isEmpty()) {
                    modelo.setRowCount(0);
                    return;
                }

                // Busca a lista atualizada direto do seu controlador
                java.util.List<entidades.Produto> listaCompleta = repositorio.Repositorio.getInstanciaRepositorio().getListaProduto();
                modelo.setRowCount(0); // Limpa para renderizar os novos resultados correspondentes

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Produto p : listaCompleta) {
                    boolean atendeAoFiltro = false;

                    if (jRadioButtonNome.isSelected()) {
                        atendeAoFiltro = p.getNome().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonDescricao.isSelected()) {
                        atendeAoFiltro = p.getDescricao().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonIdFornecedor.isSelected()) {
                        try {
                            int idFornecedor = Integer.parseInt(texto);
                            atendeAoFiltro = p.getIdFornecedor() == idFornecedor;
                        } catch (NumberFormatException e) {
                            atendeAoFiltro = false;
                        }
                    }

                    // Se o funcionario atual bater com o critério digitado, joga ele na tabela
                    if (atendeAoFiltro) {
                        modelo.addRow(new Object[]{
                            p.getIdProduto(),
                            p.getIdFornecedor(),
                            p.getNome(),
                            p.getDescricao(),
                            p.getPrecoCompra(),
                            p.getPrecoVenda(),
                            p.getQuantidadeEstoque()
                        }
                        );
                    }
                }
            }
        }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jRadioButtonNome = new javax.swing.JRadioButton();
        jRadioButtonDescricao = new javax.swing.JRadioButton();
        jRadioButtonIdFornecedor = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaProdutos = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR PRODUTOS");

        jRadioButtonNome.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonNome.setText("Nome");

        jRadioButtonDescricao.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonDescricao.setText("Descrição");

        jRadioButtonIdFornecedor.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonIdFornecedor.setText("ID Fornecedor");

        jTableListaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Produtos", "ID Fornecedor", "Nome", "Descrição", "Preço Compra", "Preço Venda", "Quantidade Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaProdutos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jRadioButtonNome)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jRadioButtonDescricao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jRadioButtonIdFornecedor)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 115, Short.MAX_VALUE)))
                            .addContainerGap()))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabelTitulo)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButtonNome)
                        .addComponent(jRadioButtonDescricao)
                        .addComponent(jRadioButtonIdFornecedor))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(9, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonDescricao;
    private javax.swing.JRadioButton jRadioButtonIdFornecedor;
    private javax.swing.JRadioButton jRadioButtonNome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaProdutos;
    // End of variables declaration//GEN-END:variables
}
