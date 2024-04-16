package dsd.simulator.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Vehicle extends Thread {

    private final RoadNetwork roadNetwork;
    private final VehicleColor color;
    private final Integer sleepTime;

    public Vehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork) {
        this.color = color;
        this.sleepTime = sleepTime;
        this.roadNetwork = roadNetwork;
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

    @Override
    public void run() {
        super.run();
        try {

            while (true) {
                Thread.sleep(sleepTime);
                roadNetwork.move(this);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
