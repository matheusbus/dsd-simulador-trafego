package dsd.simulator.observer;

import dsd.simulator.domain.section.RoadSection;

/**
 * Interface para objetos observáveis de seções de estrada.
 * Os objetos que desejam ser observados implementam esta interface para permitir que observadores se inscrevam e sejam notificados sobre mudanças.
 * 
 * @author Matheus
 */
public interface RoadSectionObservable {
    
    /**
     * Adiciona um observador para receber notificações sobre mudanças nesta seção de estrada.
     * 
     * @param observer O observador a ser adicionado.
     */
    public void addObserver(RoadSectionObserver observer);
    
    /**
     * Remove um observador que não deseja mais receber notificações sobre mudanças nesta seção de estrada.
     * 
     * @param observer O observador a ser removido.
     */
    public void removeObserver(RoadSectionObserver observer);
    
    /**
     * Notifica todos os observadores registrados sobre uma mudança nesta seção de estrada.
     * 
     * @param roadSection A seção de estrada cujo estado mudou.
     * @param hasVehicle Indica se a seção de estrada agora contém um veículo.
     */
    public void notify(RoadSection roadSection, boolean hasVehicle);
}