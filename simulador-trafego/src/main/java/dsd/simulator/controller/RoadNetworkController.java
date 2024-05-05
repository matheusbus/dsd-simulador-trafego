/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.controller;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.road.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.factory.road.RoadNetworkFactory;
import dsd.simulator.factory.vehicle.VehicleFactory;
import dsd.simulator.view.RoadNetworkView;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matheus
 */
public final class RoadNetworkController {

    private RoadNetworkView view;
    private RoadNetwork network;
    private int activeVehicles;
    private int maxVehicles;

    public RoadNetworkController(int indexOfFile) {
        this.network = RoadNetworkFactory.createRoadNetwork(indexOfFile);
        this.view = new RoadNetworkView(network);
        initButtons();
        
        this.view.setVisible(true);
    }
    
    public void initButtons() {
        view.addActionToInitButton(((e) -> {
            startTraffic();
        }));
    }
    
    public void startTraffic() {
        this.maxVehicles = view.getNumberVehicles();
        
        for(int i = 0; i < maxVehicles; i++) {
            addVehicleInNetwork();
        }
    }
    
    public void addVehicleInNetwork() {
        Random r = new Random();
        int numberOfEntryPoints = network.getEntryPoints().size();
        
        int entry = r.nextInt(numberOfEntryPoints);
        RoadSection rs = network.getEntryPoints().get(entry);
        
        Vehicle v = VehicleFactory.createVehicle(network, rs);
        
        try {
            if(rs.enter(5000)) {
                rs.setVehicle(v);
                v.start();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(RoadNetworkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
