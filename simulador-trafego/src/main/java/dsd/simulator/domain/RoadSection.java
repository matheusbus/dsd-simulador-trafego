/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author matheus
 */
public class RoadSection {
    
    private RoadType type;
    private Vehicle vehicle;
    private final List<RoadSection> nextSections = new ArrayList<>();
    private final boolean crossroad;
    
    private int positionX;
    private int positionY;
    
    private Semaphore semaphore;
    

    public RoadSection(RoadType type, Vehicle vehicle, int positionX, int positionY) {
        this.type = type;
        this.vehicle = vehicle;
        this.positionX = positionX;
        this.positionY = positionY;
        
        this.crossroad = type.toString().contains("CROSSROAD");
        this.semaphore = new Semaphore(1);
    }
    
    public boolean enter(long time) throws InterruptedException {
        return semaphore.tryAcquire(time, TimeUnit.MILLISECONDS);
    }
    
    public void exit() {
        semaphore.release();
    }

    public RoadType getType() {
        return type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public void addNextSection(RoadSection section) {
        this.nextSections.add(section);
    }
    
    public RoadSection getUniqueNextSection() {
        return this.nextSections.getFirst();
    }

    public boolean isCrossRoad() {
        return crossroad;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
}
