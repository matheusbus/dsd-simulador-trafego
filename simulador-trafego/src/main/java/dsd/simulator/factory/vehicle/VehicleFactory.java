package dsd.simulator.factory.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.domain.vehicle.VehicleColor;
import java.util.Random;

public class VehicleFactory {

    protected static final Random random = new Random();
    
    public Vehicle createVehicle(RoadNetwork roadNetwork) {
        VehicleColor color = getRandomColor();
        int sleepTime = getRandomSleepTime();
        return new Vehicle(color, sleepTime, roadNetwork);
    }
    
    protected VehicleColor getRandomColor() {
        VehicleColor[] colors = VehicleColor.values();
        return colors[random.nextInt(colors.length)];
    }
    
    protected Integer getRandomSleepTime() {
        return random.nextInt(300, 1000);
    }

}
