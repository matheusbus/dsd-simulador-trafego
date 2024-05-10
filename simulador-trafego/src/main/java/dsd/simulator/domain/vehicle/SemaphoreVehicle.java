package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.Position;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.SemaphoreRoadSection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementação do veículo com lógica de exclusão mútua utilizando semaphore
 *
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

                if (active) {
                    this.roadSection = roadSectionToEnter;
                    this.roadSection.setVehicle(this);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // 2 - Depois que entrou, enquanto estiver ativo, caminha pela malha
        while (active) {
            try {
                // Para associar a velocidade, ao final de toda movimentação aguarda um período de tempo
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Tenta entrar na próxima posição
            List<Position> possiblePaths = roadSection.getPossiblePaths();

            // Caso não haja mais caminhos, então acabou a rodovia
            if (possiblePaths.isEmpty()) {
                this.roadSection.setVehicle(null);
                ((SemaphoreRoadSection) roadSection).exit();
                setActive(false);
            } else {
                Position nextPosition = possiblePaths.get(0);
                SemaphoreRoadSection roadSectionToEnter = (SemaphoreRoadSection) roadNetwork.getRoadSectionAt(nextPosition.x, nextPosition.y);

                // Caso seja uma seção normal, então vai seguir uma seção para frente
                if (!roadSectionToEnter.isCrossroad()) {

                    try {
                        boolean entered = roadSectionToEnter.tryEnter(sleepTime);

                        // Enquanto não entrar, continua tentando
                        while (!entered) {
                            entered = roadSectionToEnter.tryEnter(sleepTime);

                            if (!entered) {
                                sleep(rand.nextInt(500));
                            }
                        }

                        // Sai da seção em que estava
                        this.roadSection.setVehicle(null);
                        ((SemaphoreRoadSection) this.roadSection).exit();

                        // Atribui-se à nova seção
                        this.roadSection = roadSectionToEnter;
                        this.roadSection.setVehicle(this);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // Se for um cruzamento, então devo montar o caminho de forma aleatória e verificar se todas as seções estão disponíveis
                    List<RoadSection> roadSectionsToAquire = new ArrayList<>();
                    roadSectionsToAquire.add(roadSectionToEnter);

                    possiblePaths = roadSectionToEnter.getPossiblePaths();
                    Position chosenPath = possiblePaths.get(rand.nextInt(possiblePaths.size()));

                    RoadSection nextRoadSection = roadNetwork.getRoadSectionAt(chosenPath.x, chosenPath.y);
                    roadSectionsToAquire.add(nextRoadSection);

                    while (nextRoadSection.isCrossroad()) {
                        possiblePaths = nextRoadSection.getPossiblePaths();
                        chosenPath = possiblePaths.get(rand.nextInt(possiblePaths.size()));
                        nextRoadSection = roadNetwork.getRoadSectionAt(chosenPath.x, chosenPath.y);
                        roadSectionsToAquire.add(nextRoadSection);
                    }

                    boolean allSectionsAvailable = false;

                    while (!allSectionsAvailable) {
                        for (RoadSection section : roadSectionsToAquire) {
                            try {
                                if (!((SemaphoreRoadSection) section).tryEnter(sleepTime)) {
                                    allSectionsAvailable = false;
                                    break;
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            allSectionsAvailable = true;
                        }

                        if (!allSectionsAvailable) {
                            // Se não conseguiu adquirir todo o caminho, então libera
                            roadSectionsToAquire.forEach(section -> {
                                ((SemaphoreRoadSection) section).exit();
                            });

                            try {
                                sleep(rand.nextInt(500));
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            // Se conseguiu acessar todas, então vai andando conforme sua velocidade
                            roadSectionsToAquire.forEach(section -> {
                                // Sai da seção em que estava
                                this.roadSection.setVehicle(null);
                                ((SemaphoreRoadSection) this.roadSection).exit();

                                // Atribui-se à nova seção
                                this.roadSection = section;
                                this.roadSection.setVehicle(this);
                                try {
                                    sleep(sleepTime);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(SemaphoreVehicle.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                        }
                    }

                }

            }
        }
        roadNetwork.removeVehicle(this);
    }

}
