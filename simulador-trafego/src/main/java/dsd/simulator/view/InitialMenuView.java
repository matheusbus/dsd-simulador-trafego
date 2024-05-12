package dsd.simulator.view;

import dsd.simulator.domain.ImplementationType;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author matheus
 */
public final class InitialMenuView extends javax.swing.JFrame {

    public InitialMenuView() {
        initLayout();
    }
    
    public void initLayout() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(365,300);
    }
    
    public void addActionBtnLetsStart(ActionListener action) {
        this.btnStart.addActionListener(action);
    }
    
    public void setCbImplementationTypeItems(List<String> items) {
        items.forEach((i) -> {
            cbImplementationType.addItem(i);
        });
    }
    
    public void setCbRoadFileItems(List<String> items) {
        items.forEach((i) -> {
            cbRoadFile.addItem(i);
        });
    }
    
    public String getSelectedImplementationType() {
        return cbImplementationType.getSelectedItem().toString();
    }
    
    public String getSelectedRoadFile() {
        return cbRoadFile.getSelectedItem().toString();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbImplementationType = new javax.swing.JComboBox<>();
        lblImplementationType = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        lblRoadFile = new javax.swing.JLabel();
        cbRoadFile = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Malha Viária [Threads]");

        cbImplementationType.setToolTipText("");

        lblImplementationType.setFont(new java.awt.Font("Monospaced", 1, 15)); // NOI18N
        lblImplementationType.setText("Select implementation type:");

        btnStart.setText("Let's Start");

        lblRoadFile.setFont(new java.awt.Font("Monospaced", 1, 15)); // NOI18N
        lblRoadFile.setText("Select road file:");

        cbRoadFile.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImplementationType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbImplementationType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRoadFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbRoadFile, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblImplementationType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbImplementationType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblRoadFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox<String> cbImplementationType;
    private javax.swing.JComboBox<String> cbRoadFile;
    private javax.swing.JLabel lblImplementationType;
    private javax.swing.JLabel lblRoadFile;
    // End of variables declaration//GEN-END:variables
}
