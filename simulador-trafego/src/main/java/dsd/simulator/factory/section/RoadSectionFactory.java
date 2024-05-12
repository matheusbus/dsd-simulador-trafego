/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.factory.section;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.vehicle.Vehicle;

/**
 *
 * @author matheus
 */
public abstract class RoadSectionFactory {
    
    public abstract RoadSection createRoadSection(RoadNetwork roadNetwork, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint);
    
}
