/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.view.component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Matheus
 */
public class BorderedPanel extends JPanel {
    
    // Cor da borda
    private Color borderColor = Color.RED;
    
    // Largura da borda
    private int borderWidth = 1;

    // Construtor
    public BorderedPanel() {
        // Define a borda vermelha
        setBorder(BorderFactory.createLineBorder(borderColor, borderWidth));
    }

    // Método para personalizar a cor da borda
    public void setBorderColor(Color color) {
        this.borderColor = color;
        // Atualiza a borda com a nova cor
        setBorder(BorderFactory.createLineBorder(borderColor, borderWidth));
        // Força a repintura do painel
        repaint();
    }

    // Método para personalizar a largura da borda
    public void setBorderWidth(int width) {
        this.borderWidth = width;
        // Atualiza a borda com a nova largura
        setBorder(BorderFactory.createLineBorder(borderColor, borderWidth));
        // Força a repintura do painel
        repaint();
    }

    // Método para desenhar a borda
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha a borda
        g.setColor(borderColor);
        for (int i = 0; i < borderWidth; i++) {
            g.drawRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1);
        }
    }
}