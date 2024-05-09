package dsd.simulator.observer;

import dsd.simulator.domain.section.RoadSection;
/**
 *
 * @author matheus
 */
public interface RoadSectionObservable {
    
    public void addObserver(RoadSectionObserver observer);
    public void removeObserver(RoadSectionObserver observer);
    public void notify(RoadSection roadSection, boolean hasVehicle);
    
}
