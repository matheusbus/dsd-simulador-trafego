package dsd.simulator.domain.road;

import dsd.simulator.domain.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class RoadNetwork {

    private RoadSection[][] roadSections;
    private Vehicle[][] vehiclePositions;
    
    private final List<Vehicle> vehicles;
    
    private final List<RoadSection> entryPoints;

    public RoadNetwork() {
        vehicles = new ArrayList<>();
        entryPoints = new ArrayList<>();
    }

    public RoadSection[][] getRoadSections() {
        return roadSections;
    }

    public Vehicle[][] getVehiclePositions() {
        return vehiclePositions;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setRoadSections(RoadSection[][] roadSections) {
        this.roadSections = roadSections;
    }

    public void setVehiclePositions(Vehicle[][] vehiclePositions) {
        this.vehiclePositions = vehiclePositions;
    }
    
    public synchronized boolean move(Vehicle vehicle, RoadSection actual, RoadSection next) {
        int[] vehiclePosition = {actual.getPositionX(), actual.getPositionY()};
        int[] nextPosition = {next.getPositionX(), next.getPositionY()};

        if (nextPosition[1] < roadSections[0].length && vehiclePositions[nextPosition[0]][nextPosition[1]] == null) {
            // If the next section is within bounds and is free, move the vehicle
            //vehiclePositions[currentPosition[0]][currentPosition[1]] = null; // Release current position
            vehiclePositions[nextPosition[0]][nextPosition[1]] = vehicle; // Update vehicle position
            return true;
        } else {
            // Se não conseguir mover
            return false;
        }
    }
    
    public void addEntryPoint(RoadSection entryPoint) {
        this.entryPoints.add(entryPoint);
    }
    
    public List<RoadSection> getEntryPoints() {
        return this.entryPoints;
    }
}
