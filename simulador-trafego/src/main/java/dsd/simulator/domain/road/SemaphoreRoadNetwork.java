package dsd.simulator.domain.road;

import dsd.simulator.factory.vehicle.SemaphoreVehicleFactory;
import dsd.simulator.factory.vehicle.VehicleFactory;

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
            while(isActive()) {
                if(activeVehicles.size() < maxActiveVechiles) {
                    
                    // Insere um novo veículo na malha
                    var v = factory.createVehicle(this);
                    activeVehicles.add(v);
                    
                    v.start();
                }
                
                // Intervalo de tempo de inserção
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        simulationThread.start();
    }

    @Override
    public void stopSimulation() {
        // Verificar se só setando a malha como inativa ela vai parar de inserir (é pra parar rsrs)
        this.active = false;
    }
    
}
