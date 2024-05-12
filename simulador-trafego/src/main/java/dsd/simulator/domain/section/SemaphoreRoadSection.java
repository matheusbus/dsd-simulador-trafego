package dsd.simulator.domain.section;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Implementação concreta da seção da malha utilizando Semaforo como mecanismo de controle para exclusão mútua.
 * Cada SemaphoreRoadSection possui um Semaphore que controla o acesso ao recurso.
 * 
 * @author Matheus
 */
public class SemaphoreRoadSection extends RoadSection {
    
    private final Semaphore semaphore;
    
    /**
     * Constrói uma nova seção de estrada com mecanismo de controle de acesso baseado em Semáforo.
     * 
     * @param network A rede de estradas à qual a seção pertence.
     * @param type O tipo de estrada.
     * @param vehicle O veículo associado à seção.
     * @param position A posição da seção na malha.
     * @param isEntryPoint Indica se a seção é um ponto de entrada.
     */
    public SemaphoreRoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        super(network, type, vehicle, position, isEntryPoint);
        this.semaphore = new Semaphore(1);
    }

    /**
     * Tenta adquirir o Semaphore da seção da estrada dentro de um determinado tempo.
     * 
     * @param time O tempo de espera.
     * @return true se o Semaphore foi adquirido com sucesso, false caso contrário.
     * @throws InterruptedException Se ocorrer uma interrupção enquanto espera para adquirir o Semaphore.
     */
    @Override
    public boolean tryEnter(long time) throws InterruptedException {
        return semaphore.tryAcquire(time, TimeUnit.MILLISECONDS);
    }

    /**
     * Libera o Semaphore da seção da estrada.
     */
    @Override
    public void exit() {
        semaphore.release();
    }
    
}