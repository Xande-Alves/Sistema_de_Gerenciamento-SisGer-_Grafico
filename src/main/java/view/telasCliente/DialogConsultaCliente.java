/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.telasCliente;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author Mario
 */
public class DialogConsultaCliente extends JDialog{
    
    public DialogConsultaCliente(Frame parent) {
        super(parent, "Consulta de Clientes", true); // true = modal

        setLayout(new BorderLayout());

        // Adiciona o painel de consulta
        add(new TelaConsultaCliente(), BorderLayout.CENTER);

        pack();
        setSize(900, 600); // aumenta em relação ao tamanho calculado
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
