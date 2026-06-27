/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.telasFornecedor;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import view.telasFuncionario.TelaConsultaFuncionario;

/**
 *
 * @author Mario
 */
public class DialogConsultaFuncionario extends JDialog{
    
    public DialogConsultaFuncionario(Frame parent) {
        super(parent, "Consulta de Funcionários", true); // true = modal

        setLayout(new BorderLayout());

        // Adiciona o painel de consulta
        add(new TelaConsultaFuncionario(), BorderLayout.CENTER);

        pack();
        setSize(900, 600); // aumenta em relação ao tamanho calculado
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
}
