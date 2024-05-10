package dsd.simulator.view;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.vehicle.VehicleColor;
import dsd.simulator.observer.RoadSectionObservable;
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
import dsd.simulator.observer.RoadSectionObserver;
import dsd.simulator.view.component.NumberOfVehiclesTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matheus
 */
public class RoadNetworkView extends JFrame implements RoadSectionObserver {

    private final JTextField txtNumberVehicles;
    private final JButton btnInit;
    private final JPanel[][] celulaPanels;
    private final Integer defaultSize = 40;

    public RoadNetworkView(RoadNetwork network) {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        RoadSection[][] roadSections = network.getRoadSections();

        int rows = roadSections.length;
        int cols = roadSections[0].length;
        celulaPanels = new JPanel[rows][cols];

        int cellWidth = defaultSize; // Largura da célula
        int cellHeight = defaultSize; // Altura da célula
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
                JPanel celulaPanel = criarCelulaPanel(section);
                celulaPanel.setBounds(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                layeredPane.add(celulaPanel, Integer.valueOf(0)); // Adiciona na camada de fundo (índice 0)
                celulaPanels[i][j] = celulaPanel; // Adiciona o painel à matriz

                // Registra esta instância como observador da seção de estrada
                section.addObserver(this);
            }
        }

        // Cria um JPanel para os elementos à direita
        JPanel direitaPanel = new JPanel();
        //direitaPanel.setLayout(new GridLayout(30, 1));
        direitaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Veículos:");
        this.txtNumberVehicles = new NumberOfVehiclesTextField();
        txtNumberVehicles.setPreferredSize(new Dimension(50, 30));
        btnInit = new JButton("Iniciar");
        direitaPanel.add(label);
        direitaPanel.add(txtNumberVehicles);
        direitaPanel.add(btnInit);
        direitaPanel.setBounds(screenWidth - 150, 0, 150, screenHeight); // Posição à direita
        layeredPane.add(direitaPanel, Integer.valueOf(1)); // Adiciona na camada superior (índice 1)

        
        setLocationRelativeTo(null);
    }

    private JPanel criarCelulaPanel(RoadSection roadSection) {
        JPanel celulaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String path = roadSection.getImageStrPath();
                ImageIcon imageIcon = new ImageIcon(path);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        celulaPanel.setPreferredSize(new Dimension(defaultSize, defaultSize));
        return celulaPanel;
    }

    // Método para atualizar a posição do veículo na tela
    @Override
    public synchronized void onSectionStateChanged(RoadSection roadSection, boolean hasVehicle) {
        int x = roadSection.getPosition().x;
        int y = roadSection.getPosition().y;

        if (!hasVehicle) {
            addVehicleIconToPanel(x, y, roadSection.getVehicle().getImagePathStr());
        } else {
            removeVehicleIconFromPanel(x, y, roadSection.getImageStrPath());
        }
    }

    private void addVehicleIconToPanel(int x, int y, String imagePath) {
        SwingUtilities.invokeLater(() -> {
            ImageIcon vehicleIcon = new ImageIcon(imagePath);
            Image vehicleImage = vehicleIcon.getImage();
            Image scaledVehicleImage = vehicleImage.getScaledInstance(defaultSize, defaultSize, Image.SCALE_SMOOTH);
            vehicleIcon = new ImageIcon(scaledVehicleImage);

            JLabel vehicleLabel = new JLabel(vehicleIcon);

            JPanel panel = celulaPanels[y][x];
            panel.setLayout(new BorderLayout());
            panel.add(vehicleLabel, BorderLayout.CENTER);
            panel.revalidate();
            panel.repaint();
        });
    }

    private void removeVehicleIconFromPanel(int x, int y, String sectionImagePath) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = celulaPanels[y][x];
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        });
    }

    public Integer getSelectedNumberVehicles() {
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
