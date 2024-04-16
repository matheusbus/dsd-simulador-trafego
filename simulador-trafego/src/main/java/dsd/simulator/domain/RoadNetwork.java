package dsd.simulator.domain;

import java.util.ArrayList;
import java.util.List;

public class RoadNetwork {

    private RoadSection[][] roadSections;
    private Vehicle[][] vehiclePositions;
    
    private final List<Vehicle> vehicles;

    public RoadNetwork() {
        vehicles = new ArrayList<>();
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
    
    public synchronized boolean move(Vehicle vehicle) {
        int[] currentPosition = findVehiclePosition(vehicle);
        
        int[] nextPosition = {currentPosition[0], currentPosition[1] + 1};
        if (nextPosition[1] < roadSections[0].length && vehiclePositions[nextPosition[0]][nextPosition[1]] == null) {
            // If the next section is within bounds and is free, move the vehicle
            vehiclePositions[currentPosition[0]][currentPosition[1]] = null; // Release current position
            vehiclePositions[nextPosition[0]][nextPosition[1]] = vehicle; // Update vehicle position
            return true;
        } else {
            // If the next section is not free or is out of bounds, cannot move the vehicle
            return false;
        }
    }
    
    private int[] findVehiclePosition(Vehicle vehicle) {
        // Find the position of the vehicle in the vehicle positions matrix
        for (int i = 0; i < vehiclePositions.length; i++) {
            for (int j = 0; j < vehiclePositions[i].length; j++) {
                if (vehiclePositions[i][j] == vehicle) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Vehicle not found
    }
}
