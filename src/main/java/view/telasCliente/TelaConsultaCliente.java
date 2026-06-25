/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasCliente;

/**
 *
 * @author Mario
 */
public class TelaConsultaCliente extends javax.swing.JPanel {

    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form TelaConsultaCliente
     */
    public TelaConsultaCliente() {
        initComponents();
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Mantém exatamente todos os seus métodos anteriores intactos:
        configurarGrupoBotoes();
        popularTabelaClientes();
        configurarSorterTabela();
        configurarMascarasEEventos();
        configurarFiltroTempoReal();

        // Garante que a tabela inicia 100% vazia
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaClientes.getModel();
        modelo.setRowCount(0);
    }

    // Agrupa os botões para exclusividade
    private void configurarGrupoBotoes() {
        javax.swing.ButtonGroup grupoConsulta = new javax.swing.ButtonGroup();
        grupoConsulta.add(jRadioButtonNome);
        grupoConsulta.add(jRadioButtonEmail);
        grupoConsulta.add(jRadioButtonTelefone);
        jRadioButtonNome.setSelected(true);
    }

    // Inicializa o ordenador/filtrador na JTable
    private void configurarSorterTabela() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaClientes.getModel();
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTableListaClientes.setRowSorter(sorter);
    }

    // Altera dinamicamente as máscaras do campo de busca conforme a seleção
    private void configurarMascarasEEventos() {
        jRadioButtonNome.addActionListener(e -> alternarMascara(null));
        jRadioButtonEmail.addActionListener(e -> alternarMascara(null));

        jRadioButtonTelefone.addActionListener(e -> {
            try {
                javax.swing.text.MaskFormatter mascaraTel = new javax.swing.text.MaskFormatter("(##) #####-####");
                mascaraTel.setPlaceholderCharacter('_');
                alternarMascara(mascaraTel);
            } catch (java.text.ParseException ex) {
                java.util.logging.Logger.getLogger(TelaConsultaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    private void alternarMascara(javax.swing.text.MaskFormatter mascara) {
        jFormattedTextFieldBusca.setValue(null);
        if (mascara != null) {
            jFormattedTextFieldBusca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascara));
        } else {
            jFormattedTextFieldBusca.setFormatterFactory(null);
        }
        jFormattedTextFieldBusca.setText("");
        jFormattedTextFieldBusca.requestFocus();

        // Limpa a tabela ao mudar o tipo de filtro
        ((javax.swing.table.DefaultTableModel) jTableListaClientes.getModel()).setRowCount(0);
    }

    private void popularTabelaClientes() {
        // 1. Pega o modelo da tabela e limpa qualquer linha residual que venha do design
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaClientes.getModel();
        modelo.setRowCount(0);

        // 2. Busca a lista completa de clientes através do seu Controlador
        java.util.List<entidades.Cliente> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaClientes();

        // 3. Varre a lista adicionando cada cliente como uma nova linha na tabela
        if (lista != null) {
            for (entidades.Cliente c : lista) {
                modelo.addRow(new Object[]{
                    c.getIdentificacao(), // Coluna 0 - ID
                    c.getNome(), // Coluna 1 - Nome
                    c.getCpf(), // Coluna 2 - CPF
                    c.getEmail(), // Coluna 3 - E-mail
                    c.getTelefone(), // Coluna 4 - Telefone
                    c.getEndereco().getLogradouro(), // Coluna 5 - Logradouro
                    c.getEndereco().getNumero(), // Coluna 6 - Número
                    c.getEndereco().getBairro(), // Coluna 7 - Bairro
                    c.getEndereco().getCidade(), // Coluna 8 - Cidade
                    c.getEndereco().getEstado(), // Coluna 9 - Estado
                    c.getEndereco().getCep() // Coluna 10 - CEP
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
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaClientes.getModel();

                // Se o campo estiver vazio ou redefinido para a máscara vazia, zera a tabela e para
                if (texto.isEmpty() || texto.equals("(__) _____-____")) {
                    modelo.setRowCount(0);
                    return;
                }

                // Busca a lista atualizada direto do seu controlador
                java.util.List<entidades.Cliente> listaCompleta = repositorio.Repositorio.getInstanciaRepositorio().getListaClientes();
                modelo.setRowCount(0); // Limpa para renderizar os novos resultados correspondentes

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Cliente c : listaCompleta) {
                    boolean atendeAoFiltro = false;

                    if (jRadioButtonNome.isSelected()) {
                        atendeAoFiltro = c.getNome().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonEmail.isSelected()) {
                        atendeAoFiltro = c.getEmail().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonTelefone.isSelected()) {
                        // Remove parênteses, hífens, espaços e sublinhados tanto do que foi digitado quanto do banco
                        String digitadoLimpo = texto.replaceAll("[^0-9]", "");
                        String telefoneClienteLimpo = c.getTelefone().replaceAll("[^0-9]", "");

                        atendeAoFiltro = telefoneClienteLimpo.contains(digitadoLimpo);
                    }

                    // Se o cliente atual bater com o critério digitado, joga ele na tabela
                    if (atendeAoFiltro) {
                        modelo.addRow(new Object[]{
                            c.getIdentificacao(),
                            c.getNome(),
                            c.getCpf(),
                            c.getEmail(),
                            c.getTelefone(),
                            c.getEndereco().getLogradouro(),
                            c.getEndereco().getNumero(),
                            c.getEndereco().getBairro(),
                            c.getEndereco().getCidade(),
                            c.getEndereco().getEstado(),
                            c.getEndereco().getCep()
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

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabelTitulo = new javax.swing.JLabel();
        jRadioButtonNome = new javax.swing.JRadioButton();
        jRadioButtonEmail = new javax.swing.JRadioButton();
        jRadioButtonTelefone = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaClientes = new javax.swing.JTable();

        jFormattedTextField1.setText("jFormattedTextField1");

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR CLIENTES");

        jRadioButtonNome.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonNome.setText("Nome");

        jRadioButtonEmail.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonEmail.setText("E-mail");

        jRadioButtonTelefone.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonTelefone.setText("Telefone");

        jTableListaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "E-mail", "Telefone", "Logradouro", "Número", "Bairro", "Cidade", "Estado", "CEP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jRadioButtonNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonTelefone)))
                .addContainerGap(196, Short.MAX_VALUE))
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonNome)
                    .addComponent(jRadioButtonEmail)
                    .addComponent(jRadioButtonTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(91, 91, 91)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonEmail;
    private javax.swing.JRadioButton jRadioButtonNome;
    private javax.swing.JRadioButton jRadioButtonTelefone;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaClientes;
    // End of variables declaration//GEN-END:variables
}
