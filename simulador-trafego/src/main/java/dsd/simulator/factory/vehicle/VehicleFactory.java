package dsd.simulator.factory.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.road.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.domain.vehicle.VehicleColor;
import java.util.Random;

public class VehicleFactory {

    private static final Random random = new Random();
    
    public static Vehicle createVehicle(RoadNetwork roadNetwork, RoadSection roadSection) {
        VehicleColor color = getRandomColor();
        int sleepTime = getRandomSleepTime();
        return new Vehicle(color, sleepTime, roadNetwork, roadSection);
    }
    
    private static VehicleColor getRandomColor() {
        VehicleColor[] colors = VehicleColor.values();
        return colors[random.nextInt(colors.length)];
    }
    
    private static Integer getRandomSleepTime() {
        // Aqui validar regra com professor. Gerando time aleatório entre 0.3s e 2s
        return random.nextInt(300, 2000);
    }

}
