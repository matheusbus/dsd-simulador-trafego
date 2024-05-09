/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain.section;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.vehicle.Vehicle;

/**
 *
 * @author matheus
 */
public class MonitorRoadSection extends RoadSection {
    
    // Possivelmente vai ter uma lista de lockers - cada lock vai ser a seção que o carro quer entrar
    
    public MonitorRoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position) {
        super(network, type, vehicle, position);
    }
    
}
