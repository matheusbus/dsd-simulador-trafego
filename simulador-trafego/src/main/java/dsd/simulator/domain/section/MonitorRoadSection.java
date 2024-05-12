/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain.section;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.vehicle.Vehicle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author matheus
 */
public class MonitorRoadSection extends RoadSection {

    private final Lock lock;

    public MonitorRoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        super(network, type, vehicle, position, isEntryPoint);
        this.lock = new ReentrantLock(true);
    }

    @Override
    public boolean tryEnter(long time) throws InterruptedException {
        return lock.tryLock(time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void exit() {
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            // Lidar com a exceção, se necessário
            e.printStackTrace();
        }
    }

}
