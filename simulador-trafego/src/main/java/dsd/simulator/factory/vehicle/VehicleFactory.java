package dsd.simulator.factory.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.domain.vehicle.VehicleColor;
import java.util.Random;

/**
 * Fábrica para criação de veículos.
 * Esta classe é responsável por criar novos veículos para serem adicionados à rede de estradas.
 * 
 * @author Matheus
 */
public class VehicleFactory {

    protected static final Random random = new Random();
    
    /**
     * Cria um novo veículo com base na rede de estradas fornecida.
     * 
     * @param roadNetwork A rede de estradas na qual o veículo será adicionado.
     * @return O veículo criado.
     */
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
