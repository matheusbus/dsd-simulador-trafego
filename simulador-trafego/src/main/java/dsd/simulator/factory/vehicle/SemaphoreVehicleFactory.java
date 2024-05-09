/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.factory.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.SemaphoreVehicle;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.domain.vehicle.VehicleColor;

/**
 *
 * @author matheus
 */
public class SemaphoreVehicleFactory extends VehicleFactory {

    @Override
    public Vehicle createVehicle(RoadNetwork roadNetwork) {
        VehicleColor color = getRandomColor();
        int sleepTime = getRandomSleepTime();
        return new SemaphoreVehicle(color, sleepTime, roadNetwork);
    }

}
