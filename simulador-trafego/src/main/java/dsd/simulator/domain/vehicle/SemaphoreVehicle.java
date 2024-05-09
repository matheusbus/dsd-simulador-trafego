package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.SemaphoreRoadSection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementação do veículo com lógica de exclusão mútua utilizando semaphore
 * @author matheus
 */
public class SemaphoreVehicle extends Vehicle {

    public SemaphoreVehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork) {
        super(color, sleepTime, roadNetwork);
    }

    @Override
    public void run() {
        // 1- Escolhe um entryPoint da malha para tentar entrar - Enquanto não entrar, fica buscando entryPoint aleatório para entrar
        while (!active) {
            try {
                List<RoadSection> entryPoints = roadNetwork.getEntryPoints();
                int randomIndexEntryPoint = rand.nextInt(entryPoints.size());

                SemaphoreRoadSection roadSectionToEnter = (SemaphoreRoadSection) entryPoints.get(randomIndexEntryPoint);

                active = roadSectionToEnter.tryEnter(sleepTime);
                
                if(active) {
                    this.roadSection = roadSectionToEnter;
                    this.roadSection.setVehicle(this);
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // 2 - Depois que entrou, enquanto estiver ativo, caminha pela malha
        while(active) {
            // Tenta entrar na próxima posição
            
            // Se conseguiu entrar na próxima seção, então libera a seção na qual estava
            
            // Se for um cruzamento, então busca os caminhos possíveis e escolhe um randomicamente para tentar entrar
            // Depois de escolhido, deve percorrer a lista até chegar em outro ponto que é uma RoadSection normal (sem ser cruzamento)
            // Se conseguir entrar em todos, então se movimenta pelo caminho criado, liberando cada vez que passa pela seção
        }
    }

}
