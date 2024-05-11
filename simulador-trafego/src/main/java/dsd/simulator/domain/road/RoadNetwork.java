package dsd.simulator.domain.road;

import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.observer.RoadNetworkObservable;
import dsd.simulator.observer.RoadNetworkObserver;
import java.util.ArrayList;
import java.util.List;

public abstract class RoadNetwork implements RoadNetworkObservable {
    
    private final List<RoadNetworkObserver> observers = new ArrayList<>();
    protected RoadSection[][] roadSections;
    
    protected final int lengthX;
    protected final int lengthY;
    
    protected boolean active;
    protected List<Vehicle> activeVehicles;
    protected Integer maxActiveVechiles;
    protected Integer insertionRange;

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
    public abstract void immediatelyStopSimulation();

    public RoadNetwork setMaxActiveVehicles(Integer maxActiveVehicles) {
        this.maxActiveVechiles = maxActiveVehicles;
        return this;
    }
    
    public Integer getMaxActiveVehicles() {
        return maxActiveVechiles;
    }
    
    public void addVehicle(Vehicle v) {
        this.activeVehicles.add(v);
        
        notify(this.activeVehicles.size());
    }
    
    public void removeVehicle(Vehicle v) {
        if(activeVehicles.indexOf(v) != -1) {
            v.setActive(false);
            activeVehicles.remove(v);
        }
        
        notify(this.activeVehicles.size());
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

    public Integer getInsertionRange() {
        return insertionRange;
    }

    public RoadNetwork setInsertionRange(Integer insertionRange) {
        this.insertionRange = insertionRange;
        return this;
    }

    public RoadSection getRoadSectionAt(int x, int y) {
        RoadSection rs;
        try {
            rs = this.roadSections[y][x];
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

    @Override
    public void addObserver(RoadNetworkObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(RoadNetworkObserver observer) {
        this.observers.remove(observer);
    }
    
    @Override
    public void notify(int numberOfVehicles) {
        for(RoadNetworkObserver o : this.observers) {
            o.onNumberOfVehiclesChanged(numberOfVehicles);
        }
    }
    
}
