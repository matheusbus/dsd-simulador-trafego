/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.view.lixo;

import dsd.simulator.domain.RoadNetwork;
import dsd.simulator.domain.RoadSection;
import dsd.simulator.domain.RoadType;
import dsd.simulator.domain.VehicleColor;
import dsd.simulator.factory.RoadNetworkFactory;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

    private static final int CELL_SIZE = 25;
    private static final int MARGIN = 10;

    public MalhaViariaDemo() {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);
        RoadNetworkFactory factory = new RoadNetworkFactory();
        RoadNetwork network = factory.createRoadNetwork(2);
        RoadSection[][] roadSections = network.getRoadSections();

        int rows = roadSections.length;
        int cols = roadSections[0].length;

        int cellWidth = 25; // Largura da célula
        int cellHeight = 25; // Altura da célula
        int margin = 10; // Margem adicional

        // Calcula o tamanho necessário da tela
        int screenWidth = (cols + 10) * cellWidth + margin;
        int screenHeight = (rows + 1) * cellHeight + margin;

        // Define o tamanho da tela
        setSize(screenWidth, screenHeight);

        // Adiciona as células da malha viária
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                RoadSection section = roadSections[i][j];
                RoadType roadType = section.getType();
                if (roadType != null) {
                    JPanel celulaPanel = criarCelulaPanel(roadType);
                    celulaPanel.setBounds(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    layeredPane.add(celulaPanel, Integer.valueOf(0)); // Adiciona na camada de fundo (índice 0)
                }
            }
        }

        // Cria um JPanel para os botões
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new GridLayout(3, 1, 0, 5)); // Layout GridLayout com 3 linhas e 1 coluna
        JButton button1 = new JButton("Botão 1");
        JButton button2 = new JButton("Botão 2");
        JButton button3 = new JButton("Botão 3");
        botoesPanel.add(button1);
        botoesPanel.add(button2);
        botoesPanel.add(button3);
        layeredPane.add(botoesPanel, BorderLayout.EAST); // Adiciona à direita do JPanel da malha viária

        // Adiciona um veículo em uma posição específica (por exemplo, na célula [1,1])
        JLabel veiculoLabel = criarVeiculoLabel();
        veiculoLabel.setBounds(25, 25, cellWidth, cellHeight); // Posição desejada
        layeredPane.add(veiculoLabel, Integer.valueOf(1)); // Adiciona na camada superior (índice 1)
    }

    private JPanel criarCelulaPanel(RoadType roadType) {
        final String arqName;
        switch (roadType) {
            case NONE:
                arqName = "none";
                break;
            case ROAD_UP:
                arqName = "road-up";
                break;
            case ROAD_DOWN:
                arqName = "road-down";
                break;
            case ROAD_LEFT:
                arqName = "road-left";
                break;
            case ROAD_RIGHT:
                arqName = "road-right";
                break;
            case CROSSROAD_DOWN:
                arqName = "chess";
                break;
            case CROSSROAD_DOWN_LEFT:
                arqName = "chess";
                break;
            case CROSSROAD_LEFT:
                arqName = "chess";
                break;
            case CROSSROAD_RIGHT:
                arqName = "chess";
                break;
            case CROSSROAD_UP:
                arqName = "chess";
                break;
            case CROSSROAD_UP_LEFT:
                arqName = "chess";
                break;
            case CROSSROAD_UP_RIGHT:
                arqName = "chess";
                break;
            case CROSSROAD_RIGHT_DOWN:
                arqName = "chess";
                break;
            default:
                throw new AssertionError();
        }

        JPanel celulaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String path = System.getProperty("user.dir") + "/src/main/resources/" + arqName + ".png";
                ImageIcon imageIcon = new ImageIcon(path);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        celulaPanel.setPreferredSize(new Dimension(25, 25));
        return celulaPanel;
    }

    private JLabel criarVeiculoLabel() {
        Random r = new Random();
        int code = r.nextInt(1, 6);
        VehicleColor vc = VehicleColor.valueOf(code);
        String vcString = vc.toString().toLowerCase();
        String path = System.getProperty("user.dir") + "/src/main/resources/vehicle-" + vcString + ".png";
        ImageIcon icon = new ImageIcon(path);
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(25, 25));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MalhaViariaDemo malhaViaria = new MalhaViariaDemo();
            malhaViaria.setVisible(true);
        });
    }

}
