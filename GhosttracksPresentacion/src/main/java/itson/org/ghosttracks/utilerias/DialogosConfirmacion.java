/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.utilerias;

import itson.org.ghosttracks.dtos.ProductoDTO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author emyla
 */
public class DialogosConfirmacion {
    /**
     * Muestra la primera alerta: ¿Está seguro de querer eliminar?
     */
    public static boolean solicitarConfirmacionEliminar(Window ventanaPadre, ProductoDTO producto) {
        final boolean[] resultado = {false};
        
        JDialog dialog = new JDialog(ventanaPadre, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0)); 

        PanelRedondeado pnlAlerta = new PanelRedondeado();
        pnlAlerta.setBackground(new Color(220, 220, 220)); 
        pnlAlerta.setLayout(new BoxLayout(pnlAlerta, BoxLayout.Y_AXIS));
        pnlAlerta.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblPregunta = new JLabel(
            "<html><center>¿Está seguro de querer eliminar<br>el artículo “" + producto.getTitulo() + "”?</center></html>"
        );
        lblPregunta.setFont(new Font("Corbel", Font.BOLD, 20));
        lblPregunta.setForeground(new Color(30, 30, 30));
        lblPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

        BotonRedondeado btnConfirmar = new BotonRedondeado();
        btnConfirmar.setBackground(new Color(209, 69, 36)); 
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setText("Sí, estoy seguro");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 18));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.setMaximumSize(new Dimension(280, 45));
        btnConfirmar.addActionListener(e -> {
            resultado[0] = true;
            dialog.dispose();
        });

        BotonRedondeado btnCancelar = new BotonRedondeado();
        btnCancelar.setBackground(new Color(153, 153, 153)); 
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setMaximumSize(new Dimension(280, 45));
        btnCancelar.addActionListener(e -> dialog.dispose());

        pnlAlerta.add(lblPregunta);
        pnlAlerta.add(Box.createVerticalStrut(20));
        pnlAlerta.add(btnConfirmar);
        pnlAlerta.add(Box.createVerticalStrut(12));
        pnlAlerta.add(btnCancelar);

        dialog.setContentPane(pnlAlerta);
        dialog.pack();
        dialog.setSize(400, 230); 
        dialog.setLocationRelativeTo(ventanaPadre);
        dialog.setVisible(true);

        return resultado[0];
    }

    /**
     * Muestra la segunda alerta: Solicita la contraseña del Administrador.
     * Retorna el texto ingresado o null si canceló.
     */
    public static String solicitarContraseniaAdmin(Window ventanaPadre) {
        final String[] contraseniaIngresada = {null};

        JDialog dialog = new JDialog(ventanaPadre, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        PanelRedondeado pnlAlerta = new PanelRedondeado();
        pnlAlerta.setBackground(new Color(220, 220, 220));
        pnlAlerta.setLayout(new BoxLayout(pnlAlerta, BoxLayout.Y_AXIS));
        pnlAlerta.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Autorización Requerida");
        lblTitulo.setFont(new Font("Corbel", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblIndicacion = new JLabel("Ingrese su contraseña de administrador:");
        lblIndicacion.setFont(new Font("Corbel", Font.PLAIN, 16));
        lblIndicacion.setForeground(new Color(60, 60, 60));
        lblIndicacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.setMaximumSize(new Dimension(280, 40));

        BotonRedondeado btnVerificar = new BotonRedondeado();
        btnVerificar.setBackground(new Color(230, 94, 7));
        btnVerificar.setForeground(Color.WHITE);
        btnVerificar.setText("Confirmar");
        btnVerificar.setFont(new Font("Arial", Font.BOLD, 18));
        btnVerificar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerificar.setMaximumSize(new Dimension(280, 45));
        btnVerificar.addActionListener(e -> {
            contraseniaIngresada[0] = new String(txtPassword.getPassword());
            dialog.dispose();
        });

        BotonRedondeado btnCancelar = new BotonRedondeado();
        btnCancelar.setBackground(new Color(153, 153, 153));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setMaximumSize(new Dimension(280, 45));
        btnCancelar.addActionListener(e -> dialog.dispose());

        pnlAlerta.add(lblTitulo);
        pnlAlerta.add(Box.createVerticalStrut(10));
        pnlAlerta.add(lblIndicacion);
        pnlAlerta.add(Box.createVerticalStrut(15));
        pnlAlerta.add(txtPassword);
        pnlAlerta.add(Box.createVerticalStrut(20));
        pnlAlerta.add(btnVerificar);
        pnlAlerta.add(Box.createVerticalStrut(12));
        pnlAlerta.add(btnCancelar);

        dialog.setContentPane(pnlAlerta);
        dialog.pack();
        dialog.setSize(400, 280); 
        dialog.setLocationRelativeTo(ventanaPadre);
        dialog.setVisible(true);

        return contraseniaIngresada[0];
    }
}
