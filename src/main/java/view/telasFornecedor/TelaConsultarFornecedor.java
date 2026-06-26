/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasFornecedor;

/**
 *
 * @author Mario
 */
public class TelaConsultarFornecedor extends javax.swing.JPanel {

    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form TelaConsultarFornecedor
     */
    public TelaConsultarFornecedor() {
        initComponents();

        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Mantém exatamente todos os seus métodos anteriores intactos:
        configurarGrupoBotoes();
        popularTabelaFornecedores();
        configurarSorterTabela();
        configurarMascarasEEventos();
        configurarFiltroTempoReal();

        // Garante que a tabela inicia 100% vazia
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFornecedores.getModel();
        modelo.setRowCount(0);
    }

    // Agrupa os botões para exclusividade
    private void configurarGrupoBotoes() {
        javax.swing.ButtonGroup grupoConsulta = new javax.swing.ButtonGroup();
        grupoConsulta.add(jRadioButtonNome);
        grupoConsulta.add(jRadioButtonEmail);
        grupoConsulta.add(jRadioButtonTelefone);
        grupoConsulta.add(jRadioButtonRazaoSocial);
        grupoConsulta.add(jRadioButtonCnpj);
        jRadioButtonNome.setSelected(true);
    }

    // Inicializa o ordenador/filtrador na JTable
    private void configurarSorterTabela() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFornecedores.getModel();
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTableListaFornecedores.setRowSorter(sorter);
    }

    // Altera dinamicamente as máscaras do campo de busca conforme a seleção
    private void configurarMascarasEEventos() {
        jRadioButtonNome.addActionListener(e -> alternarMascara(null));
        jRadioButtonEmail.addActionListener(e -> alternarMascara(null));
        jRadioButtonRazaoSocial.addActionListener(e -> alternarMascara(null));

        jRadioButtonCnpj.addActionListener(e -> {
            try {
                // CNPJ
                javax.swing.text.MaskFormatter mascaraCnpj = new javax.swing.text.MaskFormatter("##.###.###/####-##");
                mascaraCnpj.setPlaceholderCharacter('_');
                alternarMascara(mascaraCnpj);

            } catch (java.text.ParseException ex) {
                java.util.logging.Logger.getLogger(TelaConsultarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });

        jRadioButtonTelefone.addActionListener(e -> {
            try {
                javax.swing.text.MaskFormatter mascaraTel = new javax.swing.text.MaskFormatter("(##) #####-####");
                mascaraTel.setPlaceholderCharacter('_');
                alternarMascara(mascaraTel);

            } catch (java.text.ParseException ex) {
                java.util.logging.Logger.getLogger(TelaConsultarFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        ((javax.swing.table.DefaultTableModel) jTableListaFornecedores.getModel()).setRowCount(0);
    }

    public void popularTabelaFornecedores() {
        // 1. Pega o modelo da tabela e limpa qualquer linha residual que venha do design
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFornecedores.getModel();
        modelo.setRowCount(0);

        // 2. Busca a lista completa de funcionarios através do seu Controlador
        java.util.List<entidades.Fornecedor> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaFornecedores();

        // 3. Varre a lista adicionando cada funcionarios como uma nova linha na tabela
        if (lista != null) {
            for (entidades.Fornecedor fornec : lista) {
                modelo.addRow(new Object[]{
                    fornec.getIdentificacao(), // Coluna 0 - ID
                    fornec.getNome(), // Coluna 1 - Nome
                    fornec.getCpf(), // Coluna 2 - CPF
                    fornec.getEmail(), // Coluna 3 - E-mail
                    fornec.getTelefone(), // Coluna 4 - Telefone
                    fornec.getEndereco().getLogradouro(), // Coluna 5 - Logradouro
                    fornec.getEndereco().getNumero(), // Coluna 6 - Número
                    fornec.getEndereco().getBairro(), // Coluna 7 - Bairro
                    fornec.getEndereco().getCidade(), // Coluna 8 - Cidade
                    fornec.getEndereco().getEstado(), // Coluna 9 - Estado
                    fornec.getEndereco().getCep(), // Coluna 10 - CEP
                    fornec.getRepresentaEmpresaNome(),
                    fornec.getRepresentaEmpresaCnpj()
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
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFornecedores.getModel();

                // Se o campo estiver vazio ou redefinido para a máscara vazia, zera a tabela e para
                if (texto.isEmpty() || texto.equals("(__) _____-____") || texto.equals("__.___.___/____-__")) {
                    modelo.setRowCount(0);
                    return;
                }

                // Busca a lista atualizada direto do seu controlador
                java.util.List<entidades.Fornecedor> listaCompleta = repositorio.Repositorio.getInstanciaRepositorio().getListaFornecedores();
                modelo.setRowCount(0); // Limpa para renderizar os novos resultados correspondentes

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Fornecedor fornec : listaCompleta) {
                    boolean atendeAoFiltro = false;

                    if (jRadioButtonNome.isSelected()) {
                        atendeAoFiltro = fornec.getNome().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonEmail.isSelected()) {
                        atendeAoFiltro = fornec.getEmail().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonTelefone.isSelected()) {
                        // Remove parênteses, hífens, espaços e sublinhados tanto do que foi digitado quanto do banco
                        String digitadoLimpo = texto.replaceAll("[^0-9]", "");
                        String telefoneFuncenteLimpo = fornec.getTelefone().replaceAll("[^0-9]", "");

                        atendeAoFiltro = telefoneFuncenteLimpo.contains(digitadoLimpo);
                    } else if (jRadioButtonRazaoSocial.isSelected()) {
                        atendeAoFiltro = fornec.getRepresentaEmpresaNome().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonCnpj.isSelected()) {
                        String digitadoLimpo = texto.replaceAll("[^0-9]", "");
                        String cnpjFornecLimpo = fornec.getRepresentaEmpresaCnpj().replaceAll("[^0-9]", "");

                        atendeAoFiltro = cnpjFornecLimpo.contains(digitadoLimpo);
                    }

                    // Se o funcionario atual bater com o critério digitado, joga ele na tabela
                    if (atendeAoFiltro) {
                        modelo.addRow(new Object[]{
                            fornec.getIdentificacao(),
                            fornec.getNome(),
                            fornec.getCpf(),
                            fornec.getEmail(),
                            fornec.getTelefone(),
                            fornec.getEndereco().getLogradouro(),
                            fornec.getEndereco().getNumero(),
                            fornec.getEndereco().getBairro(),
                            fornec.getEndereco().getCidade(),
                            fornec.getEndereco().getEstado(),
                            fornec.getEndereco().getCep(),
                            fornec.getRepresentaEmpresaNome(),
                            fornec.getRepresentaEmpresaCnpj()
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
        jRadioButtonNome = new javax.swing.JRadioButton();
        jRadioButtonEmail = new javax.swing.JRadioButton();
        jRadioButtonTelefone = new javax.swing.JRadioButton();
        jFormattedTextFieldBusca = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaFornecedores = new javax.swing.JTable();
        jRadioButtonRazaoSocial = new javax.swing.JRadioButton();
        jRadioButtonCnpj = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR FORNECEDORES");

        jRadioButtonNome.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonNome.setText("Nome");

        jRadioButtonEmail.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonEmail.setText("E-mail");

        jRadioButtonTelefone.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonTelefone.setText("Telefone");

        jTableListaFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "E-mail", "Telefone", "Logradouro", "Número", "Bairro", "Cidade", "Estado", "CEP", "Razão Social", "CNPJ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaFornecedores);

        jRadioButtonRazaoSocial.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonRazaoSocial.setText("R. Social");

        jRadioButtonCnpj.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCnpj.setText("CNPJ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonTelefone)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonRazaoSocial))
                            .addComponent(jFormattedTextFieldBusca))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonCnpj)
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(jRadioButtonTelefone)
                    .addComponent(jRadioButtonRazaoSocial)
                    .addComponent(jRadioButtonCnpj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextFieldBusca;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JRadioButton jRadioButtonCnpj;
    private javax.swing.JRadioButton jRadioButtonEmail;
    private javax.swing.JRadioButton jRadioButtonNome;
    private javax.swing.JRadioButton jRadioButtonRazaoSocial;
    private javax.swing.JRadioButton jRadioButtonTelefone;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaFornecedores;
    // End of variables declaration//GEN-END:variables
}
