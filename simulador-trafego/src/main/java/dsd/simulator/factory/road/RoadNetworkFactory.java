package dsd.simulator.factory.road;

import dsd.simulator.domain.road.RoadNetwork;

/**
 *
 * @author Matheus
 */
public abstract class RoadNetworkFactory {

    public abstract RoadNetwork createRoadNetwork(String fileName);

}
