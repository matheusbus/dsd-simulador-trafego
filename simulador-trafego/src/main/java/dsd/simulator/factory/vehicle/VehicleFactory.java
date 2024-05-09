package dsd.simulator.factory.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.domain.vehicle.VehicleColor;
import java.util.Random;

public abstract class VehicleFactory {

    protected static final Random random = new Random();
    
    public abstract Vehicle createVehicle(RoadNetwork roadNetwork);
    
    protected VehicleColor getRandomColor() {
        VehicleColor[] colors = VehicleColor.values();
        return colors[random.nextInt(colors.length)];
    }
    
    protected Integer getRandomSleepTime() {
        // Validar possibilidade de permitir o usuário escolher velocidade mínima e velocidade máxima
        return random.nextInt(300, 2000);
    }

}
