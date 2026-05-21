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
import javax.swing.JTextField;

/**
 * Utilería para el despliegue de ventanas emergentes de confirmación personalizadas.
 * @author emyla
 */
public class DialogosConfirmacion {

    
    public static boolean solicitarConfirmacionEliminar(Window ventanaPadre, ProductoDTO producto) { // Aquí le preguntamos al usuario si está seguro de lo q hará
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

    public static String[] solicitarCredencialesAdmin(Window ventanaPadre) { // Se piden las credenciales
        final String[][] credenciales = {null};

        JDialog dialog = new JDialog(ventanaPadre, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        PanelRedondeado pnlAlerta = new PanelRedondeado();
        pnlAlerta.setBackground(new Color(220, 220, 220));
        pnlAlerta.setLayout(new BoxLayout(pnlAlerta, BoxLayout.Y_AXIS));
        pnlAlerta.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Autorización de Administrador");
        lblTitulo.setFont(new Font("Corbel", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Para el correo
        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setFont(new Font("Corbel", Font.PLAIN, 15));
        lblCorreo.setForeground(new Color(60, 60, 60));
        lblCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 16));
        txtCorreo.setMaximumSize(new Dimension(280, 35));
        txtCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Para la contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Corbel", Font.PLAIN, 15));
        lblPassword.setForeground(new Color(60, 60, 60));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPassword.setMaximumSize(new Dimension(280, 35));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones
        BotonRedondeado btnConfirmar = new BotonRedondeado();
        btnConfirmar.setBackground(new Color(230, 94, 7));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setText("Autorizar");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 18));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.setMaximumSize(new Dimension(280, 42));

        btnConfirmar.addActionListener(e -> {
            String correoText = (txtCorreo.getText() != null) ? txtCorreo.getText().trim() : "";
            String passText = (txtPassword.getPassword() != null) ? new String(txtPassword.getPassword()) : "";
            
            credenciales[0] = new String[]{correoText, passText};
            dialog.dispose();
        });

        BotonRedondeado btnCancelar = new BotonRedondeado();
        btnCancelar.setBackground(new Color(153, 153, 153));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setMaximumSize(new Dimension(280, 42));
        btnCancelar.addActionListener(e -> dialog.dispose());

        // Ensamblado del Layout Estructurado
        pnlAlerta.add(lblTitulo);
        pnlAlerta.add(Box.createVerticalStrut(15));
        pnlAlerta.add(lblCorreo);
        pnlAlerta.add(Box.createVerticalStrut(5));
        pnlAlerta.add(txtCorreo);
        pnlAlerta.add(Box.createVerticalStrut(10));
        pnlAlerta.add(lblPassword);
        pnlAlerta.add(Box.createVerticalStrut(5));
        pnlAlerta.add(txtPassword);
        pnlAlerta.add(Box.createVerticalStrut(20));
        pnlAlerta.add(btnConfirmar);
        pnlAlerta.add(Box.createVerticalStrut(10));
        pnlAlerta.add(btnCancelar);

        dialog.setContentPane(pnlAlerta);
        dialog.pack();
        dialog.setSize(400, 360); // Ajuste de altura balanceado para contener ambos formularios
        dialog.setLocationRelativeTo(ventanaPadre);
        dialog.setVisible(true);

        return credenciales[0];
    }
}