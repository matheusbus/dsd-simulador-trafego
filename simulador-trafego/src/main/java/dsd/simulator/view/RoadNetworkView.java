/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.view;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.road.RoadSection;
import dsd.simulator.domain.road.RoadType;
import dsd.simulator.domain.vehicle.VehicleColor;
import dsd.simulator.factory.road.RoadNetworkFactory;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matheus
 */
public class RoadNetworkView extends JFrame {

    private final JTextField txtNumberVehicles;
    private final JButton btnInit;
    
    public RoadNetworkView(RoadNetwork network) {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);
        
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

        // Cria um JPanel para os elementos à direita
        JPanel direitaPanel = new JPanel();
        //direitaPanel.setLayout(new GridLayout(30, 1));
        direitaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel label = new JLabel("Veículos:");
        this.txtNumberVehicles = new JTextField();
        txtNumberVehicles.setPreferredSize(new Dimension(50, 20));
        btnInit = new JButton("Iniciar");
        direitaPanel.add(label);
        direitaPanel.add(txtNumberVehicles);
        direitaPanel.add(btnInit);
        direitaPanel.setBounds(screenWidth - 150, 0, 150, screenHeight); // Posição à direita
        layeredPane.add(direitaPanel, Integer.valueOf(1)); // Adiciona na camada superior (índice 1)

    }

    private JPanel criarCelulaPanel(RoadType roadType) {
        final String arqName;
        
        switch (roadType) {
            case NONE -> arqName = "none";
            case ROAD_UP -> arqName = "road-up";
            case ROAD_DOWN -> arqName = "road-down";
            case ROAD_LEFT -> arqName = "road-left";
            case ROAD_RIGHT -> arqName = "road-right";
            case CROSSROAD_DOWN -> arqName = "chess";
            case CROSSROAD_DOWN_LEFT -> arqName = "chess";
            case CROSSROAD_LEFT -> arqName = "chess";
            case CROSSROAD_RIGHT -> arqName = "chess";
            case CROSSROAD_UP -> arqName = "chess";
            case CROSSROAD_UP_LEFT -> arqName = "chess";
            case CROSSROAD_UP_RIGHT -> arqName = "chess";
            case CROSSROAD_RIGHT_DOWN -> arqName = "chess";
            default -> throw new AssertionError();
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
    
    public Integer getNumberVehicles() {
        int num = 0;
        try {
            num = Integer.parseInt(txtNumberVehicles.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return num;
    }
    
    public void addActionToInitButton(ActionListener actionListener) {
        btnInit.addActionListener(actionListener);
    }

}
