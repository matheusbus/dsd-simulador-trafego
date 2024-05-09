package dsd.simulator.domain.road;

import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;

public abstract class RoadNetwork {

    protected RoadSection[][] roadSections;
    
    protected final int lengthX;
    protected final int lengthY;
    
    protected boolean active;
    protected List<Vehicle> activeVehicles;
    protected Integer maxActiveVechiles;

    private final List<RoadSection> entryPoints;


    public RoadNetwork(int lengthX, int lengthY) {
        this.entryPoints = new ArrayList<>();
        this.activeVehicles = new ArrayList<>();
        this.lengthX = lengthX;
        this.lengthY = lengthY;
        
        this.active = true;
    }
    
    public abstract void startSimulation();
    public abstract void stopSimulation();

    public RoadNetwork setMaxActiveVehicles(Integer maxActiveVehicles) {
        this.maxActiveVechiles = maxActiveVehicles;
        return this;
    }
    
    public Integer getMaxActiveVehicles() {
        return maxActiveVechiles;
    }
    
    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public RoadSection[][] getRoadSections() {
        return roadSections;
    }

    public void setRoadSections(RoadSection[][] roadSections) {
        this.roadSections = roadSections;
    }

    public RoadSection getRoadSectionAt(int x, int y) {
        RoadSection rs;
        try {
            rs = this.roadSections[x][y];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
        return rs;
    }

    public void addEntryPoint(RoadSection entryPoint) {
        this.entryPoints.add(entryPoint);
    }

    public List<RoadSection> getEntryPoints() {
        return this.entryPoints;
    }
}
