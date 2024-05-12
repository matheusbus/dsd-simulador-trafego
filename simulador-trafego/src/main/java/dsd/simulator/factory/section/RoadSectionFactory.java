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
 * Fábrica abstrata para criação de seções de estrada.
 * Esta classe é a base para fábricas concretas que criam seções de estrada específicas.
 * 
 * @author matheus
 */
public abstract class RoadSectionFactory {
    
    /**
     * Método abstrato para criação de uma seção de estrada.
     * 
     * @param roadNetwork A rede de estradas à qual a seção de estrada pertence.
     * @param type O tipo de seção de estrada a ser criado.
     * @param vehicle O veículo associado à seção de estrada (pode ser nulo).
     * @param position A posição da seção de estrada na rede de estradas.
     * @param isEntryPoint Indica se esta seção de estrada é um ponto de entrada na rede de estradas.
     * @return A seção de estrada criada.
     */
    public abstract RoadSection createRoadSection(RoadNetwork roadNetwork, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint);
}
