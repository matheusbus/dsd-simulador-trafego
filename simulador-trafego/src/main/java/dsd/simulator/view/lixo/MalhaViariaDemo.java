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

    public MalhaViariaDemo() {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);
        RoadNetworkFactory factory = new RoadNetworkFactory();
        RoadNetwork network = factory.createRoadNetwork(3);
        RoadSection[][] roadSections = network.getRoadSections();

        int rows = roadSections.length;
        int cols = roadSections[0].length;

        // Adiciona as células da malha viária
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                RoadSection section = roadSections[i][j];
                // Assuming you have a method to map RoadType to cell type (tipoCelula)
                RoadType roadType = section.getType();
                if (roadType != null) {
                    JPanel celulaPanel = criarCelulaPanel(roadType);
                    celulaPanel.setBounds(j * 25, i * 25, 25, 25);
                    layeredPane.add(celulaPanel, Integer.valueOf(0)); // Adiciona na camada de fundo (índice 0)
                }
            }
        }

        // Adiciona um veículo em uma posição específica (por exemplo, na célula [1,1])
        JLabel veiculoLabel = criarVeiculoLabel();
        veiculoLabel.setBounds(25,25,25,25); // Posição desejada
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
                ImageIcon imageIcon = new ImageIcon("/home/matheus/Documentos/GitHub/dsd-simulador-trafego/simulador-trafego/src/main/resources/"+arqName+".png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        celulaPanel.setPreferredSize(new Dimension(25,25));
        return celulaPanel;
    }

    private JLabel criarVeiculoLabel() {
        Random r = new Random();
        int code = r.nextInt(1, 6);
        VehicleColor vc = VehicleColor.valueOf(code);
        String vcString = vc.toString().toLowerCase();
        ImageIcon icon = new ImageIcon("/home/matheus/Documentos/GitHub/dsd-simulador-trafego/simulador-trafego/src/main/resources/vehicle-" + vcString + ".png");
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(25,25));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MalhaViariaDemo malhaViaria = new MalhaViariaDemo();
            malhaViaria.setVisible(true);
        });
    }

}
