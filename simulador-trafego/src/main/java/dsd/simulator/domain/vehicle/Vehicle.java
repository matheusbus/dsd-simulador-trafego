package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import java.util.Random;

public abstract class Vehicle extends Thread {

    protected Random rand = new Random();

    protected RoadNetwork roadNetwork;
    protected RoadSection roadSection;
    protected VehicleColor color;
    protected Integer sleepTime;

    protected String imagePathStr;
    protected boolean active;

    public Vehicle() {
    }

    public Vehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
        this.color = color;
        this.sleepTime = sleepTime;

        this.imagePathStr = System.getProperty("user.dir") + "/src/main/resources/vehicle-" + color.toString().toLowerCase() + ".png";
    }

    public String getImagePathStr() {
        return imagePathStr;
    }

    public VehicleColor getColor() {
        return color;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }
    
    public void setRoadSection(RoadSection roadSection) {
        this.roadSection = roadSection;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
