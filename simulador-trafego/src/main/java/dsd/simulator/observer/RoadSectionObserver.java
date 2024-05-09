/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.observer;

import dsd.simulator.domain.section.RoadSection;

/**
 *
 * @author Matheus
 */
public interface RoadSectionObserver {
    
    void onSectionStateChanged(RoadSection roadSection, boolean hasVehicle);

}
