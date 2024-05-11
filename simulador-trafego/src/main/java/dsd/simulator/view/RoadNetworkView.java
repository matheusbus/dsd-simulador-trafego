package dsd.simulator.view;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.observer.RoadNetworkObserver;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import dsd.simulator.observer.RoadSectionObserver;
import dsd.simulator.view.component.BorderedPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matheus
 */
public class RoadNetworkView extends JFrame implements RoadSectionObserver, RoadNetworkObserver {

    //private final JTextField txtNumberVehicles;
    private final JSpinner txtNumberVehicles;
    private final JLabel lblNumVeiculosAtivos;
    private final JButton btnInit;
    private final JButton btnStop;
    private final JButton btnStopImmediately;

    private final JPanel[][] celulaPanels;
    private final Integer defaultSize = 40;

    public RoadNetworkView(RoadNetwork network) {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        network.addObserver(this);
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
        JPanel direitaPanel = new BorderedPanel();
        GridLayout gridLayout = new GridLayout(10, 1); // Define o layout com 30 linhas e 1 coluna;
        gridLayout.setVgap(10); // Define o espaçamento vertical entre os componentes como 10 pixels
        direitaPanel.setLayout(gridLayout);

        Font fonte = new Font("Monospaced", Font.BOLD, 20);
        JLabel label = new JLabel("Veículos:");
        label.setFont(fonte);

        JLabel lblVeiculosAtivos = new JLabel("Veículos ativos:");
        lblVeiculosAtivos.setFont(fonte);
        lblVeiculosAtivos.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblNumVeiculosAtivos = new JLabel("0");
        lblNumVeiculosAtivos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumVeiculosAtivos.setFont(new Font("Monospaced", Font.BOLD, 50));

        this.txtNumberVehicles = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        txtNumberVehicles.setPreferredSize(new Dimension(50, 30));

        Font btnFont = new Font("Monospaced", Font.BOLD, 12);

        btnInit = new JButton("Iniciar");
        btnInit.setFont(btnFont);
        btnStop = new JButton("Terminar");
        btnStop.setFont(btnFont);
        btnStopImmediately = new JButton("Terminar Imediatamente");
        btnStopImmediately.setFont(btnFont);

        direitaPanel.add(label);
        direitaPanel.add(txtNumberVehicles);
        direitaPanel.add(btnInit);
        direitaPanel.add(btnStop);
        direitaPanel.add(btnStopImmediately);
        direitaPanel.add(lblVeiculosAtivos);
        direitaPanel.add(lblNumVeiculosAtivos);

        direitaPanel.setBounds(screenWidth - 300, 0, 200, screenHeight); // Posição à direita
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
            num = Integer.parseInt(txtNumberVehicles.getValue().toString());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return num;
    }

    public void addActionToInitButton(ActionListener actionListener) {
        btnInit.addActionListener(actionListener);
    }

    public void addActionToStopButton(ActionListener actionListener) {
        btnStop.addActionListener(actionListener);
    }

    public void addActionToStopImmediatelyButton(ActionListener actionListener) {
        btnStopImmediately.addActionListener(actionListener);
    }

    public void setBtnInitEnabled(boolean enabled) {
        btnInit.setEnabled(enabled);
    }

    @Override
    public void onNumberOfVehiclesChanged(int numberOfActiveVehicles) {
        lblNumVeiculosAtivos.setText(String.valueOf(numberOfActiveVehicles));
    }

}
