package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.road.RoadSection;
import dsd.simulator.domain.road.RoadType;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vehicle extends Thread {

    private final RoadNetwork roadNetwork;
    private final RoadSection roadSection;
    private final VehicleColor color;
    private final Integer sleepTime;

    public Vehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork, RoadSection roadSection) {
        this.roadNetwork = roadNetwork;
        this.roadSection = roadSection;
        this.color = color;
        this.sleepTime = sleepTime;
    }

    public VehicleColor getColor() {
        return color;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        try {
            // Enquanto não sair da malha
            while (!roadSection.getType().equals(RoadType.NONE)) {
                
                // Se não é um cruzamento, valida apenas a próxima posição para então mover
                if(!roadSection.getType().toString().contains("CROSSROAD")) {
                    RoadSection rd = roadSection.getUniqueNextSection();
                    boolean entered = rd.enter(sleepTime);
                    
                    if(entered) {
                        roadNetwork.move(this, this.getRoadSection(), rd);
                        
                        rd.exit();
                    } else {
                        
                    }
                }
                
                sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
