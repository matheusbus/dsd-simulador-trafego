package dsd.simulator.factory.section;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.MonitorRoadSection;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.vehicle.Vehicle;

/**
 * Fábrica para criação de seções de estrada com monitores.
 * Esta classe é responsável por criar seções de estrada com monitores.
 * 
 * @author matheus
 */
public class MonitorRoadSectionFactory extends RoadSectionFactory {

    @Override
    public RoadSection createRoadSection(RoadNetwork roadNetwork, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        return new MonitorRoadSection(roadNetwork, type, vehicle, position, isEntryPoint);
    }
    
}
