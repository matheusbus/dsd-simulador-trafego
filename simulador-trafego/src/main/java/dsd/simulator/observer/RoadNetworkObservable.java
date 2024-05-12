package dsd.simulator.observer;

/**
 * Interface para objetos observáveis de redes de estradas.
 * Os objetos que desejam ser observados implementam esta interface para permitir que observadores se inscrevam e sejam notificados sobre mudanças.
 * 
 * @author Matheus
 */
public interface RoadNetworkObservable {
    
    /**
     * Adiciona um observador para receber notificações sobre mudanças na rede de estradas.
     * 
     * @param observer O observador a ser adicionado.
     */
    public void addObserver(RoadNetworkObserver observer);
    
    /**
     * Remove um observador que não deseja mais receber notificações sobre mudanças na rede de estradas.
     * 
     * @param observer O observador a ser removido.
     */
    public void removeObserver(RoadNetworkObserver observer);
    
    /**
     * Notifica todos os observadores registrados sobre uma mudança na rede de estradas.
     * 
     * @param numberOfVehicles O número atual de veículos na rede de estradas.
     */
    public void notify(int numberOfVehicles);
}