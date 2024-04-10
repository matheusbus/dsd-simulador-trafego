/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.view.lixo;

import dsd.simulator.domain.VehicleColor;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matheus
 */
public class MalhaViariaDemo extends JFrame {
    
    private static final int TAMANHO_MALHA = 10;
    private static final int[][] MALHA = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 7, 6, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public MalhaViariaDemo() {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Adiciona as células da malha viária
        for (int i = 0; i < TAMANHO_MALHA; i++) {
            for (int j = 0; j < TAMANHO_MALHA; j++) {
                int tipoCelula = MALHA[i][j];
                if (tipoCelula != 0) {
                    JPanel celulaPanel = criarCelulaPanel(tipoCelula);
                    celulaPanel.setBounds(j * 50, i * 50, 50, 50);
                    layeredPane.add(celulaPanel, Integer.valueOf(0)); // Adiciona na camada de fundo (índice 0)
                }
            }
        }

        // Adiciona um veículo em uma posição específica (por exemplo, na célula [1,1])
        JLabel veiculoLabel = criarVeiculoLabel();
        veiculoLabel.setBounds(50, 50, 50, 50); // Posição desejada
        layeredPane.add(veiculoLabel, Integer.valueOf(1)); // Adiciona na camada superior (índice 1)
    }

    private JPanel criarCelulaPanel(int tipoCelula) {
        JPanel celulaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\GitHub\\dsd-simulador-trafego\\simulador-trafego\\src\\main\\resources\\road-piece.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        celulaPanel.setPreferredSize(new Dimension(50, 50));
        return celulaPanel;
    }

    private JLabel criarVeiculoLabel() {
        Random r = new Random();
        int code = r.nextInt(1, 6);
        VehicleColor vc = VehicleColor.valueOf(code);
        String vcString = vc.toString().toLowerCase();
        ImageIcon icon = new ImageIcon("C:\\GitHub\\dsd-simulador-trafego\\simulador-trafego\\src\\main\\resources\\vehicle-"+vcString+".png");
        JLabel label = new JLabel(icon);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MalhaViariaDemo malhaViaria = new MalhaViariaDemo();
            malhaViaria.setVisible(true);
        });
    }
    
}
