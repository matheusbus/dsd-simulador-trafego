package dsd.simulator.domain.road;

import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.factory.vehicle.SemaphoreVehicleFactory;
import dsd.simulator.factory.vehicle.VehicleFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matheus
 */
public class SemaphoreRoadNetwork extends RoadNetwork {

    public SemaphoreRoadNetwork(int lengthX, int lengthY) {
        super(lengthX, lengthY);
    }

    @Override
    public void startSimulation() {
        VehicleFactory factory = new SemaphoreVehicleFactory();

        Thread simulationThread = new Thread(() -> {
            // Enquanto estiver ativa irá inserir veículos respeitando o limite definido
            while (isActive()) {
                if (activeVehicles.size() < maxActiveVechiles) {

                    // Insere um novo veículo na malha
                    var v = factory.createVehicle(this);
                    addVehicle(v);

                    v.start();
                }

                // Intervalo de tempo de inserção
                try {
                    Thread.sleep(insertionRange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        simulationThread.start();
    }

    @Override
    public void stopSimulation() {
        this.active = false;
    }

    @Override
    public void immediatelyStopSimulation() {
        this.active = false;

        for (Vehicle v : activeVehicles) {
            v.setActive(false);
        }
    }

}
