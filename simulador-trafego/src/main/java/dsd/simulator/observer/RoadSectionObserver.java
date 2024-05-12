package dsd.simulator.observer;

import dsd.simulator.domain.section.RoadSection;

/**
 * Interface para observadores de seções de estrada.
 * Os observadores implementam esta interface para receber notificações sobre mudanças no estado de uma seção de estrada.
 * 
 * @author Matheus
 */
public interface RoadSectionObserver {
    
    /**
     * Método chamado quando o estado de uma seção de estrada muda.
     * 
     * @param roadSection A seção de estrada cujo estado mudou.
     * @param hasVehicle Indica se a seção de estrada agora contém um veículo.
     */
    void onSectionStateChanged(RoadSection roadSection, boolean hasVehicle);
}