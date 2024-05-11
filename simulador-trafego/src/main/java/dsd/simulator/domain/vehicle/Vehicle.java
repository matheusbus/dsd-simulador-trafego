package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.section.RoadSection;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Vehicle extends Thread {

    protected Random rand = new Random();

    protected RoadNetwork roadNetwork;
    protected RoadSection roadSection;
    protected VehicleColor color;
    protected Integer sleepTime;

    protected String imagePathStr;
    protected boolean active;

    public Vehicle() {
    }

    public Vehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
        this.color = color;
        this.sleepTime = sleepTime;

        this.imagePathStr = System.getProperty("user.dir") + "/src/main/resources/vehicle-" + color.toString().toLowerCase() + ".png";
    }

    public String getImagePathStr() {
        return imagePathStr;
    }

    public VehicleColor getColor() {
        return color;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }

    public void setRoadSection(RoadSection roadSection) {
        this.roadSection = roadSection;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        this.setActive(true);

        while (active) {
            boolean entered = false;
            // 1- Escolhe um entryPoint da malha para tentar entrar - Enquanto não entrar, fica buscando entryPoint aleatório para entrar
            while (!entered) {
                try {
                    List<RoadSection> entryPoints = roadNetwork.getEntryPoints();
                    int randomIndexEntryPoint = rand.nextInt(entryPoints.size());

                    RoadSection roadSectionToEnter = entryPoints.get(randomIndexEntryPoint);

                    entered = roadSectionToEnter.tryEnter(sleepTime);

                    if (entered) {
                        this.roadSection = roadSectionToEnter;
                        this.roadSection.setVehicle(this);
                    }

                } catch (InterruptedException ex) {
                    setActive(false);
                    return;
                }
            }

            // 2 - Depois que entrou, enquanto estiver ativo, caminha pela malha
            while (active) {
                try {
                    if (Thread.interrupted()) {
                        break;
                    }
                    // Para associar a velocidade, ao final de toda movimentação aguarda um período de tempo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {
                    setActive(false);
                    return;
                }

                // Tenta entrar na próxima posição
                List<Position> possiblePaths = roadSection.getPossiblePaths();

                // Caso não haja mais caminhos, então acabou a rodovia
                if (possiblePaths.isEmpty()) {
                    this.roadSection.setVehicle(null);
                    roadSection.exit();
                    setActive(false);
                } else {
                    Position nextPosition = possiblePaths.get(0);
                    RoadSection roadSectionToEnter = roadNetwork.getRoadSectionAt(nextPosition.x, nextPosition.y);

                    // Caso seja uma seção normal, então vai seguir uma seção para frente
                    if (!roadSectionToEnter.isCrossroad()) {

                        try {
                            entered = roadSectionToEnter.tryEnter(sleepTime);

                            // Enquanto não entrar, continua tentando
                            while (!entered) {
                                entered = roadSectionToEnter.tryEnter(sleepTime);

                                if (!entered) {
                                    sleep(rand.nextInt(500));
                                }
                            }

                            // Sai da seção em que estava
                            this.roadSection.setVehicle(null);
                            this.roadSection.exit();

                            // Atribui-se à nova seção
                            this.roadSection = roadSectionToEnter;
                            this.roadSection.setVehicle(this);

                        } catch (InterruptedException ex) {
                            setActive(false);
                            return;
                        }
                    } else {
                        // Se for um cruzamento, então devo montar o caminho de forma aleatória e verificar se todas as seções estão disponíveis
                        List<RoadSection> roadSectionsPath = new ArrayList<>();
                        roadSectionsPath.add(roadSectionToEnter);

                        possiblePaths = roadSectionToEnter.getPossiblePaths();
                        Position chosenPath = possiblePaths.get(rand.nextInt(possiblePaths.size()));
                        RoadSection nextRoadSection = roadNetwork.getRoadSectionAt(chosenPath.x, chosenPath.y);
                        roadSectionsPath.add(nextRoadSection);

                        while (nextRoadSection.isCrossroad()) {

                            // Validação para não entrar em loop no cruzamento
                            if (roadSectionsPath.size() == 3) {
                                chosenPath = nextRoadSection.forceExitCrossroad();
                            } else {
                                possiblePaths = nextRoadSection.getPossiblePaths();
                                chosenPath = possiblePaths.get(rand.nextInt(possiblePaths.size()));
                            }
                            nextRoadSection = roadNetwork.getRoadSectionAt(chosenPath.x, chosenPath.y);
                            roadSectionsPath.add(nextRoadSection);
                        }

                        List<RoadSection> roadSectionsAcquired = new ArrayList<>();

                        for (RoadSection section : roadSectionsPath) {
                            try {
                                if (section.tryEnter(sleepTime)) {
                                    roadSectionsAcquired.add(section);
                                }
                            } catch (InterruptedException ex) {
                                setActive(false);
                                return;
                            }
                        }

                        // Se conseguiu reservar todas, então se move
                        if (roadSectionsAcquired.size() == roadSectionsPath.size()) {

                            roadSectionsAcquired.forEach(section -> {

                                // Sai da seção em que estava
                                this.roadSection.setVehicle(null);
                                this.roadSection.exit();

                                // Atribui-se à nova seção
                                this.roadSection = section;
                                this.roadSection.setVehicle(this);
                                try {
                                    sleep(sleepTime);
                                } catch (InterruptedException ex) {
                                    setActive(false);
                                    return;
                                }
                            });
                        } else {
                            // Se não conseguiu acessar todas, então libera as que pegou
                            roadSectionsAcquired.forEach(section -> {
                                section.exit();
                            });

                            try {
                                sleep(rand.nextInt(500));
                            } catch (InterruptedException ex) {
                                setActive(false);
                                return;
                            }
                        }

                    }
                }
            }
            roadNetwork.removeVehicle(this);
        }
    }

}
