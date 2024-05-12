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
import java.awt.TrayIcon.MessageType;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matheus
 */
public final class RoadNetworkView extends JFrame implements RoadSectionObserver, RoadNetworkObserver {

    private JSpinner txtNumberVehicles;
    private JSpinner txtInsertionRange;
    private JLabel lblNumVeiculosAtivos;
    private JLabel lblFinalizado;
    private JButton btnInit;
    private JButton btnStop;
    private JButton btnStopImmediately;
    private JButton btnRestart;

    private JPanel[][] celulaPanels;
    private final Integer defaultSize = 40;

    public RoadNetworkView(RoadNetwork network) {
        super("Malha Viária com Veículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents(network);
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

    public Integer getSelectedInsertionRange() {
        int num = 0;
        try {
            num = Integer.parseInt(txtInsertionRange.getValue().toString());
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
    
    public void addActionToRestartButton(ActionListener actionListener) {
        btnRestart.addActionListener(actionListener);
    }

    public void setBtnInitEnabled(boolean enabled) {
        btnInit.setEnabled(enabled);
    }

    public void setBtnStopEnabled(boolean enabled) {
        btnStop.setEnabled(enabled);
    }

    public void setBtnStopImmediatelyEnabled(boolean enabled) {
        btnStopImmediately.setEnabled(enabled);
    }
    
    public void setBtnRestartEnabled(boolean enabled) {
        btnRestart.setEnabled(enabled);
    }

    public void setTxtNumberVehiclesEnabled(boolean enabled) {
        txtNumberVehicles.setEnabled(enabled);
    }

    public void setTxtInsertionRangeEnabled(boolean enabled) {
        txtInsertionRange.setEnabled(enabled);
    }

    public void setLblFinalizadoVisible(boolean visible) {
        lblFinalizado.setVisible(visible);
    }

    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(rootPane, message, title, MessageType.ERROR.ordinal());
    }

    @Override
    public void onNumberOfVehiclesChanged(int numberOfActiveVehicles, boolean isActive) {
        lblNumVeiculosAtivos.setText(String.valueOf(numberOfActiveVehicles));

        if (!isActive) {
            if(numberOfActiveVehicles == 0) {
                lblFinalizado.setText("Finalizado com sucesso!");
                lblFinalizado.setForeground(Color.ORANGE);
                setBtnRestartEnabled(true);
            }
            lblNumVeiculosAtivos.setForeground(new Color(176, 190, 197));
        } else {
            if (numberOfActiveVehicles == Integer.parseInt(txtNumberVehicles.getValue().toString())) {
                lblNumVeiculosAtivos.setForeground(new Color(0,191,99));
            } else {
                lblNumVeiculosAtivos.setForeground(new Color(255,87,87));
            }
        }
    }

    public void initComponents(RoadNetwork network) {
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
        int screenWidth = (cols + 10) * cellWidth + margin + 50;
        int screenHeight = rows == 10 ? 600 : ((rows + 1) * cellHeight + margin);

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
        JPanel pnlFunctions = new JPanel();
        GridLayout gridLayout = new GridLayout(10, 1); // Define o layout com 30 linhas e 1 coluna;
        gridLayout.setVgap(10); // Define o espaçamento vertical entre os componentes como 10 pixels
        pnlFunctions.setLayout(gridLayout);

        Font fonte = new Font("Monospaced", Font.BOLD, 16);

        JLabel lblInsertionRange = new JLabel("Intervalo inserção:");
        lblInsertionRange.setForeground(Color.ORANGE);
        lblInsertionRange.setFont(fonte);
        this.txtInsertionRange = new JSpinner(new SpinnerNumberModel(100, 100, 1500, 100));
        txtInsertionRange.setSize(new Dimension(50, 30));
        JPanel pnlInsertionRange = new JPanel();
        pnlInsertionRange.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlInsertionRange.add(lblInsertionRange);
        pnlInsertionRange.add(txtInsertionRange);
        JLabel lblVehicle = new JLabel("Veículos:");
        lblVehicle.setForeground(Color.ORANGE);
        lblVehicle.setFont(fonte);
        this.txtNumberVehicles = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        txtNumberVehicles.setSize(new Dimension(50, 30));
        JPanel pnlNumberVehicles = new JPanel();
        pnlNumberVehicles.add(lblVehicle);
        pnlNumberVehicles.add(txtNumberVehicles);
        pnlNumberVehicles.setLayout(new FlowLayout(FlowLayout.LEFT));

        pnlFunctions.add(pnlInsertionRange);
        pnlFunctions.add(pnlNumberVehicles);

        JLabel lblVeiculosAtivos = new JLabel("Veículos ativos:");
        lblVeiculosAtivos.setFont(fonte);
        lblVeiculosAtivos.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblFinalizado = new JLabel("Finalizando...");
        lblFinalizado.setFont(fonte);
        lblFinalizado.setHorizontalAlignment(SwingConstants.CENTER);
        lblFinalizado.setVisible(false);
        this.lblNumVeiculosAtivos = new JLabel("0");
        lblNumVeiculosAtivos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumVeiculosAtivos.setFont(new Font("Monospaced", Font.BOLD, 36));

        Font btnFont = new Font("Monospaced", Font.BOLD, 16);

        btnInit = new JButton("Iniciar");
        btnInit.setFont(btnFont);
        btnStop = new JButton("Encerrar e Aguardar");
        btnStop.setFont(btnFont);
        btnStop.setEnabled(false);
        btnStopImmediately = new JButton("Encerrar Imediatamente");
        btnStopImmediately.setFont(btnFont);
        btnStopImmediately.setEnabled(false);
        
        btnRestart = new JButton("Reiniciar");
        btnRestart.setFont(btnFont);
        btnRestart.setEnabled(false);

        pnlFunctions.add(btnInit);
        pnlFunctions.add(btnStop);
        pnlFunctions.add(btnStopImmediately);
        pnlFunctions.add(lblVeiculosAtivos);
        pnlFunctions.add(lblNumVeiculosAtivos);
        pnlFunctions.add(lblFinalizado);
        pnlFunctions.add(btnRestart);

        pnlFunctions.setBounds(screenWidth - 375, 25, 350, 500);
        layeredPane.add(pnlFunctions, Integer.valueOf(1)); // Adiciona na camada superior (índice 1)

        setLocationRelativeTo(null);
    }

}
