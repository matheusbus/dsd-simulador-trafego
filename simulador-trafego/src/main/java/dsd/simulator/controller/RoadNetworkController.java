package dsd.simulator.controller;

import dsd.simulator.domain.ImplementationType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.factory.road.RoadNetworkFactory;
import dsd.simulator.view.RoadNetworkView;

/**
 * Controlador da malha viária
 * 
 * Este controlador gerencia as interações relacionadas à simulação da malha viária,
 * incluindo iniciar e parar o tráfego.
 * 
 * @author Matheus
 */
public final class RoadNetworkController {

    private final RoadNetworkView rnv;
    private final RoadNetwork roadNetwork;

    /**
     * Construtor da classe RoadNetworkController
     * 
     * @param roadFileName O nome do arquivo de estrada
     * @param implementationType O tipo de implementação da malha viária
     */
    public RoadNetworkController(String roadFileName, ImplementationType implementationType) {
        this.roadNetwork = new RoadNetworkFactory().createRoadNetwork(roadFileName, implementationType);
        this.rnv = new RoadNetworkView(roadNetwork);
        initButtons();
        this.rnv.setVisible(true);
    }

    /**
     * Inicializa os botões da interface de malha viária
     */
    public void initButtons() {
        rnv.addActionToInitButton(((e) -> {
            startTraffic();
        }));
        rnv.addActionToStopButton((e) -> {
            stopTraffic();
        });
        rnv.addActionToStopImmediatelyButton((e) -> {
            immediatelyStopTraffic();
        });
        rnv.addActionToRestartButton((e) -> {
            restartTraffic();
        });
    }

    /**
     * Inicia o tráfego na malha viária
     */
    public void startTraffic() {
        Integer maxActiveVehicles = rnv.getSelectedNumberVehicles();
        Integer insertionRange = rnv.getSelectedInsertionRange();
        
        if (maxActiveVehicles == 0) {
            showMessage("A quantidade de veículos não pode ser zero.", "Erro [Quantidade de veículos]");
            return;
        }
        if (insertionRange < 100) {
            showMessage("O intervalo de inserção não deve ser menor que cem millisegundos.", "Erro [Intervalo de inserção]");
            return;
        }
        
        if(maxActiveVehicles > roadNetwork.getMaxPossibleActiveVehicles()) {
            showMessage(String.format("A quantidade máxima de veículos para essa rodovia é de %s veículos!", roadNetwork.getMaxPossibleActiveVehicles()), "Erro [Quantidade de veículos]");
            return;
        }

        roadNetwork
                .setInsertionRange(insertionRange)
                .setMaxActiveVehicles(maxActiveVehicles)
                .startSimulation();

        rnv.setBtnInitEnabled(false);
        rnv.setTxtNumberVehiclesEnabled(false);
        rnv.setTxtInsertionRangeEnabled(false);

        rnv.setBtnStopEnabled(true);
        rnv.setBtnStopImmediatelyEnabled(true);

    }

    /**
     * Para o tráfego na malha viária aguardando os veículos ativos saírem da malha
     */
    public void stopTraffic() {
        try {
            roadNetwork.stopSimulation();
        } catch (InterruptedException ex) {
            showMessage(ex.getLocalizedMessage(), "Erro [Encerramento]");
        }
        rnv.setBtnStopEnabled(false);
        rnv.setBtnStopImmediatelyEnabled(false);
        rnv.setLblFinalizadoVisible(true);
    }

    /**
     * Para imediatamente o tráfego na malha viária retirando os veículos da malha
     */
    public void immediatelyStopTraffic() {
        try {
            roadNetwork.immediatelyStopSimulation();
        } catch (InterruptedException ex) {
            showMessage(ex.getLocalizedMessage(), "Erro [Encerramento imediato]");
        }
        rnv.setBtnStopEnabled(false);
        rnv.setBtnStopImmediatelyEnabled(false);
        rnv.setLblFinalizadoVisible(true);
    }
    
    public void restartTraffic() {
        roadNetwork.restartSimulation();
        
        rnv.setBtnInitEnabled(true);
        rnv.setLblFinalizadoVisible(false);
        rnv.setTxtNumberVehiclesEnabled(true);
        rnv.setTxtInsertionRangeEnabled(true);
        rnv.setBtnRestartEnabled(false);
    }

    /**
     * Imprime a representação da malha viária no console
     * 
     * @param roadNetwork A rede viária a ser impressa
     */
    public void printRoadNetwork(RoadNetwork roadNetwork) {
        RoadSection[][] roadSections = roadNetwork.getRoadSections();

        for (int i = 0; i < roadSections.length; i++) {
            for (int j = 0; j < roadSections[i].length; j++) {
                System.out.print(roadSections[i][j].getType().getCode() + "[" + i + "]" + "[" + j + "] "
                        + (roadSections[i][j].getVehicle() == null ? "[X]" : "[ ]"));
            }
            System.out.println();
        }
    }

    /**
     * Exibe uma mensagem na interface de malha viária
     * 
     * @param message A mensagem a ser exibida
     * @param title O título da mensagem
     */
    public void showMessage(String message, String title) {
        rnv.showMessage(message, title);
    }
}
