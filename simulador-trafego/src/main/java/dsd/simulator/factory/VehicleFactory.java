package dsd.simulator.factory;

import dsd.simulator.domain.Vehicle;
import dsd.simulator.domain.VehicleColor;
import java.util.Random;

public class VehicleFactory {

    private static final Random random = new Random();
    
    public static Vehicle createVehicle() {
        VehicleColor color = getRandomColor();
        int sleepTime = getRandomSleepTime();
        return new Vehicle(color, sleepTime);
    }
    
    private static VehicleColor getRandomColor() {
        VehicleColor[] colors = VehicleColor.values();
        return colors[random.nextInt(colors.length)];
    }
    
    private static Integer getRandomSleepTime() {
        // Aqui validar regra com professor. Gerando time aleatório entre 0.1s e 5s
        return random.nextInt(100, 5000);
    }

}
