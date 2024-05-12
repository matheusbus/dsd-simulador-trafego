package dsd.simulator.controller;

import dsd.simulator.domain.ImplementationType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.factory.road.RoadNetworkFactory;
import dsd.simulator.view.RoadNetworkView;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Matheus
 */
public final class RoadNetworkController {

    private final RoadNetworkView rnv;
    private final RoadNetwork roadNetwork;

    public RoadNetworkController(String roadFileName, ImplementationType implementationType) {
        this.roadNetwork = new RoadNetworkFactory().createRoadNetwork(roadFileName, implementationType);
        this.rnv = new RoadNetworkView(roadNetwork);
        initButtons();
        this.rnv.setVisible(true);
    }

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
    }

    public void startTraffic() {
        Integer maxActiveVehicles = rnv.getSelectedNumberVehicles();
        Integer insertionRange = rnv.getSelectedInsertionRange();

        if (maxActiveVehicles == 0) {
            showMessage("A quantidade de veículos não pode ser zero.", "Erro [Quantidade de veículos]");
            return;
        } else if (insertionRange < 100) {
            showMessage("O intervalo de inserção deve ser menor que cem millisegundos.", "Erro [Intervalo de inserção]");
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

    public void showMessage(String message, String title) {
        rnv.showMessage(message, title);
    }
}
