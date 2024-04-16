/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain;

/**
 *
 * @author matheus
 */
public class RoadSection {
    
    private RoadType type;
    private Vehicle vehicle;

    public RoadSection(RoadType type, Vehicle vehicle) {
        this.type = type;
        this.vehicle = vehicle;
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
    
}
