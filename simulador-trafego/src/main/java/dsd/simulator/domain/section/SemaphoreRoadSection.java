package dsd.simulator.domain.section;

import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author matheus
 */
public class SemaphoreRoadSection extends RoadSection {
    
    private final Semaphore semaphore;
    
    public SemaphoreRoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        super(network, type, vehicle, position, isEntryPoint);
        this.semaphore = new Semaphore(1);
    }

    public boolean tryEnter(long time) throws InterruptedException {
        return semaphore.tryAcquire(time, TimeUnit.MILLISECONDS);
    }

    public void exit() {
        semaphore.release();
    }
    
}
