/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.observer;

/**
 *
 * @author Matheus
 */
public interface RoadNetworkObservable {
    
    public void addObserver(RoadNetworkObserver observer);
    public void removeObserver(RoadNetworkObserver observer);
    public void notify(int numberOfVehicles);
    
}
