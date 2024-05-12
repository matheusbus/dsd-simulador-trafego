package dsd.simulator.domain.section;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.vehicle.Vehicle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementação concreta da seção da malha utilizando Monitor como mecanismo de controle para exclusão mútua.
 * Cada MonitorRoadSection possui um Lock que controla o acesso ao recurso.
 * 
 * @author Matheus
 */
public class MonitorRoadSection extends RoadSection {

    private final Lock lock;

    /**
     * Constrói uma nova seção de estrada com mecanismo de controle de acesso baseado em Monitor.
     * 
     * @param network A rede de estradas à qual a seção pertence.
     * @param type O tipo de estrada.
     * @param vehicle O veículo associado à seção.
     * @param position A posição da seção na malha.
     * @param isEntryPoint Indica se a seção é um ponto de entrada.
     */
    public MonitorRoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        super(network, type, vehicle, position, isEntryPoint);
        this.lock = new ReentrantLock(true);
    }

    /**
     * Tenta adquirir o Lock da seção da estrada dentro de um determinado tempo.
     * 
     * @param time O tempo de espera.
     * @return true se o Lock foi adquirido com sucesso, false caso contrário.
     * @throws InterruptedException Se ocorrer uma interrupção enquanto espera para adquirir o Lock.
     */
    @Override
    public boolean tryEnter(long time) throws InterruptedException {
        return lock.tryLock(time, TimeUnit.MILLISECONDS);
    }

    /**
     * Libera o Lock da seção da estrada.
     */
    @Override
    public void exit() {
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            // Lidar com a exceção, se necessário
            e.printStackTrace();
        }
    }

}