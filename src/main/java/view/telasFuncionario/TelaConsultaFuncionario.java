/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.telasFuncionario;

/**
 *
 * @author Mario
 */
public class TelaConsultaFuncionario extends javax.swing.JPanel {
    
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form TelaConsultaFuncionario
     */
    public TelaConsultaFuncionario() {
        initComponents();
        
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Mantém exatamente todos os seus métodos anteriores intactos:
        configurarGrupoBotoes();
        popularTabelaFuncionarios();
        configurarSorterTabela();
        configurarMascarasEEventos();
        configurarFiltroTempoReal();

        // Garante que a tabela inicia 100% vazia
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFuncionarios.getModel();
        modelo.setRowCount(0);
    }
    
    // Agrupa os botões para exclusividade
    private void configurarGrupoBotoes() {
        javax.swing.ButtonGroup grupoConsulta = new javax.swing.ButtonGroup();
        grupoConsulta.add(jRadioButtonNome);
        grupoConsulta.add(jRadioButtonEmail);
        grupoConsulta.add(jRadioButtonTelefone);
        grupoConsulta.add(jRadioButtonCargo);
        jRadioButtonNome.setSelected(true);
    }

    // Inicializa o ordenador/filtrador na JTable
    private void configurarSorterTabela() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFuncionarios.getModel();
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTableListaFuncionarios.setRowSorter(sorter);
    }

    // Altera dinamicamente as máscaras do campo de busca conforme a seleção
    private void configurarMascarasEEventos() {
        jRadioButtonNome.addActionListener(e -> alternarMascara(null));
        jRadioButtonEmail.addActionListener(e -> alternarMascara(null));
        jRadioButtonCargo.addActionListener(e -> alternarMascara(null));

        jRadioButtonTelefone.addActionListener(e -> {
            try {
                javax.swing.text.MaskFormatter mascaraTel = new javax.swing.text.MaskFormatter("(##) #####-####");
                mascaraTel.setPlaceholderCharacter('_');
                alternarMascara(mascaraTel);
            } catch (java.text.ParseException ex) {
                java.util.logging.Logger.getLogger(TelaConsultaFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        ((javax.swing.table.DefaultTableModel) jTableListaFuncionarios.getModel()).setRowCount(0);
    }

    public void popularTabelaFuncionarios() {
        // 1. Pega o modelo da tabela e limpa qualquer linha residual que venha do design
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFuncionarios.getModel();
        modelo.setRowCount(0);

        // 2. Busca a lista completa de funcionarios através do seu Controlador
        java.util.List<entidades.Funcionario> lista = repositorio.Repositorio.getInstanciaRepositorio().getListaFuncionarios();

        // 3. Varre a lista adicionando cada funcionarios como uma nova linha na tabela
        if (lista != null) {
            for (entidades.Funcionario func : lista) {
                modelo.addRow(new Object[]{
                    func.getIdentificacao(), // Coluna 0 - ID
                    func.getNome(), // Coluna 1 - Nome
                    func.getCpf(), // Coluna 2 - CPF
                    func.getEmail(), // Coluna 3 - E-mail
                    func.getTelefone(), // Coluna 4 - Telefone
                    func.getEndereco().getLogradouro(), // Coluna 5 - Logradouro
                    func.getEndereco().getNumero(), // Coluna 6 - Número
                    func.getEndereco().getBairro(), // Coluna 7 - Bairro
                    func.getEndereco().getCidade(), // Coluna 8 - Cidade
                    func.getEndereco().getEstado(), // Coluna 9 - Estado
                    func.getEndereco().getCep(), // Coluna 10 - CEP
                    func.getCargo(),
                    func.getDataAdmissao(),
                    func.getDataDemissao()
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
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTableListaFuncionarios.getModel();

                // Se o campo estiver vazio ou redefinido para a máscara vazia, zera a tabela e para
                if (texto.isEmpty() || texto.equals("(__) _____-____")) {
                    modelo.setRowCount(0);
                    return;
                }

                // Busca a lista atualizada direto do seu controlador
                java.util.List<entidades.Funcionario> listaCompleta = repositorio.Repositorio.getInstanciaRepositorio().getListaFuncionarios();
                modelo.setRowCount(0); // Limpa para renderizar os novos resultados correspondentes

                if (listaCompleta == null) {
                    return;
                }

                for (entidades.Funcionario func : listaCompleta) {
                    boolean atendeAoFiltro = false;

                    if (jRadioButtonNome.isSelected()) {
                        atendeAoFiltro = func.getNome().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonEmail.isSelected()) {
                        atendeAoFiltro = func.getEmail().toLowerCase().contains(texto.toLowerCase());
                    } else if (jRadioButtonTelefone.isSelected()) {
                        // Remove parênteses, hífens, espaços e sublinhados tanto do que foi digitado quanto do banco
                        String digitadoLimpo = texto.replaceAll("[^0-9]", "");
                        String telefoneFuncenteLimpo = func.getTelefone().replaceAll("[^0-9]", "");

                        atendeAoFiltro = telefoneFuncenteLimpo.contains(digitadoLimpo);
                    } else if (jRadioButtonCargo.isSelected()) {
                        atendeAoFiltro = func.getCargo().toLowerCase().contains(texto.toLowerCase());
                    }

                    // Se o funcionario atual bater com o critério digitado, joga ele na tabela
                    if (atendeAoFiltro) {
                        modelo.addRow(new Object[]{
                            func.getIdentificacao(),
                            func.getNome(),
                            func.getCpf(),
                            func.getEmail(),
                            func.getTelefone(),
                            func.getEndereco().getLogradouro(),
                            func.getEndereco().getNumero(),
                            func.getEndereco().getBairro(),
                            func.getEndereco().getCidade(),
                            func.getEndereco().getEstado(),
                            func.getEndereco().getCep(),
                            func.getCargo(),
                            func.getDataAdmissao(),
                            func.getDataDemissao()
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
        jTableListaFuncionarios = new javax.swing.JTable();
        jRadioButtonCargo = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(153, 153, 255));

        jLabelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("CONSULTAR FUNCIONÁRIOS");

        jRadioButtonNome.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonNome.setText("Nome");

        jRadioButtonEmail.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonEmail.setText("E-mail");

        jRadioButtonTelefone.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonTelefone.setText("Telefone");

        jTableListaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "E-mail", "Telefone", "Logradouro", "Número", "Bairro", "Cidade", "Estado", "CEP", "Cargo", "Admissão", "Desligamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListaFuncionarios);

        jRadioButtonCargo.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButtonCargo.setText("Cargo");

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
                                .addComponent(jRadioButtonCargo))
                            .addComponent(jFormattedTextFieldBusca))
                        .addGap(0, 132, Short.MAX_VALUE))
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
                    .addComponent(jRadioButtonCargo))
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
    private javax.swing.JRadioButton jRadioButtonCargo;
    private javax.swing.JRadioButton jRadioButtonEmail;
    private javax.swing.JRadioButton jRadioButtonNome;
    private javax.swing.JRadioButton jRadioButtonTelefone;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListaFuncionarios;
    // End of variables declaration//GEN-END:variables
}
