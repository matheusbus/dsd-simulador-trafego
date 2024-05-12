package dsd.simulator.factory.section;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.SemaphoreRoadSection;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.vehicle.Vehicle;

/**
 * Fábrica para criação de seções de estrada com semáforos.
 * Esta classe é responsável por criar seções de estrada com semáforos.
 * 
 * @author matheus
 */
public class SemaphoreRoadSectionFactory extends RoadSectionFactory {

    @Override
    public RoadSection createRoadSection(RoadNetwork roadNetwork, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        return new SemaphoreRoadSection(roadNetwork, type, vehicle, position, isEntryPoint);
    }
    
}
