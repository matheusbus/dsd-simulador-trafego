package dsd.simulator.controller;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.factory.road.MonitorRoadNetworkFactory;
import dsd.simulator.factory.road.RoadNetworkFactory;
import dsd.simulator.factory.road.SemaphoreRoadNetworkFactory;
import dsd.simulator.view.RoadNetworkView;

/**
 *
 * @author Matheus
 */
public final class RoadNetworkController {

    private final RoadNetworkView rnv;
    private final RoadNetwork roadNetwork;

    public RoadNetworkController(String roadFileName, String implementationType) {
        RoadNetworkFactory factory;

        switch (implementationType) {
            case "Semaphore" ->
                factory = new SemaphoreRoadNetworkFactory();
            case "Monitor" ->
                factory = new MonitorRoadNetworkFactory();
            default ->
                throw new IllegalArgumentException("Selected implementation type does not exists.");
        }
        this.roadNetwork = factory.createRoadNetwork(roadFileName);

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
    }

    public void startTraffic() {
        Integer maxActiveVehicles = rnv.getSelectedNumberVehicles();

        roadNetwork
            .setMaxActiveVehicles(maxActiveVehicles)
            .startSimulation();
    }
    
    public void stopTraffic() {
        roadNetwork.stopSimulation();
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
}
