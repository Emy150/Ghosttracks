/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.utilerias;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Nahomi Figueroa (Adaptado para ComboBox)
 * @param <E> El tipo de elementos que contendrá el ComboBox
 */
public class ComboBoxRedondeado<E> extends JComboBox<E> implements Serializable {

    private int arcAncho = 20;
    private int arcAlto = 20;
    private Color borderColor = Color.WHITE;
    private int borderThickness = 1;

    public ComboBoxRedondeado() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Esto es clave: modificamos la interfaz de usuario (UI) del ComboBox
        // para quitar el fondo cuadrado del botón de la flechita.
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintar el fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcAncho, arcAlto);
        
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintar el borde redondeado
        g2.setStroke(new BasicStroke(borderThickness));
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcAncho, arcAlto);
        
        g2.dispose();
    }

    // --- Getters y Setters (Iguales a los de tu TextField) ---

    public int getArcAncho() {
        return arcAncho;
    }

    public void setArcAncho(int arcAncho) {
        this.arcAncho = arcAncho;
        repaint(); // Es buena práctica redibujar si cambia en tiempo de ejecución
    }

    public int getArcAlto() {
        return arcAlto;
    }

    public void setArcAlto(int arcAlto) {
        this.arcAlto = arcAlto;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
    
    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        repaint();
    }
}