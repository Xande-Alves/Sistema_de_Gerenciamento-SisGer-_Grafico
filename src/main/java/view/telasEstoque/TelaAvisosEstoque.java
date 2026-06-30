/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasEstoque;

/**
 *
 * @author Mario
 */
public class TelaAvisosEstoque extends javax.swing.JPanel {

    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form TelaAvisosEstoque
     */
    public TelaAvisosEstoque() {
        initComponents();

    jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    javax.swing.text.AbstractDocument document =
            (javax.swing.text.AbstractDocument) jTextFieldValorBusca.getDocument();

    document.setDocumentFilter(new javax.swing.text.DocumentFilter() {

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {

            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                String text, javax.swing.text.AttributeSet attrs)
                throws javax.swing.text.BadLocationException {

            if (text == null) {
                return;
            }

            String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());

            String novoTexto = textoAtual.substring(0, offset)
                    + text
                    + textoAtual.substring(offset + length);

            // Campo vazio é permitido
            if (novoTexto.isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
                return;
            }

            // Aceita apenas números com um único ponto OU uma única vírgula
            if (novoTexto.matches("\\d+([.,]\\d*)?")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    });

    configurarSorterTabela();
    configurarFiltroTempoReal();

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();
    modelo.setRowCount(0);
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

        // 2. Busca a lista completa de produtos através do seu Controlador
        java.util.List<entidades.Produto> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaProduto();

        // 3. Varre a lista adicionando cada produto como uma nova linha na tabela
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

    private void configurarFiltroTempoReal() {

        jTextFieldValorBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

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

                javax.swing.table.DefaultTableModel modelo
                        = (javax.swing.table.DefaultTableModel) jTableListaProdutos.getModel();

                modelo.setRowCount(0);

                String texto = jTextFieldValorBusca.getText().trim();

                // Campo vazio
                if (texto.isEmpty()) {
                    return;
                }

                // Aceita tanto vírgula quanto ponto
                texto = texto.replace(",", ".");

                double valorBusca;

                try {
                    valorBusca = Double.parseDouble(texto);
                } catch (NumberFormatException ex) {
                    // Enquanto o usuário digita um número inválido,
                    // simplesmente não mostra resultados.
                    return;
                }

                java.util.List<entidades.Produto> listaCompleta
                        = repositorio.Repositorio.getInstanciaRepositorio().getListaProduto();

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Produto p : listaCompleta) {

                    if (p.getQuantidadeEstoque() <= valorBusca) {

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

        jLabelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaProdutos = new javax.swing.JTable();
        jLabelValorConsulta = new javax.swing.JLabel();
        jTextFieldValorBusca = new javax.swing.JTextField();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR PRODUTOS");

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

        jLabelValorConsulta.setForeground(new java.awt.Color(0, 0, 0));
        jLabelValorConsulta.setText("Consulta estoques com valor abaixo do informado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelValorConsulta)
                    .addComponent(jTextFieldValorBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(103, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabelValorConsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldValorBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabelTitulo)
                    .addGap(67, 67, 67)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(9, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelValorConsulta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaProdutos;
    private javax.swing.JTextField jTextFieldValorBusca;
    // End of variables declaration//GEN-END:variables
}
