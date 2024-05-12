package dsd.simulator.observer;

/**
 * Interface para observadores de redes de estradas.
 * Os observadores implementam esta interface para receber notificações sobre mudanças no número de veículos ativos na rede de estradas.
 * 
 * @author Matheus
 */
public interface RoadNetworkObserver {
    
    /**
     * Método chamado quando o número de veículos ativos na rede de estradas é alterado.
     * 
     * @param numberOfActiveVehicles O novo número de veículos ativos.
     * @param isActive Indica se a rede de estradas ainda está ativa.
     */
    void onNumberOfVehiclesChanged(int numberOfActiveVehicles, boolean isActive);
}