package dsd.simulator.factory.road;

import dsd.simulator.domain.ImplementationType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.factory.section.MonitorRoadSectionFactory;
import dsd.simulator.factory.section.RoadSectionFactory;
import dsd.simulator.factory.section.SemaphoreRoadSectionFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Fábrica para criação de redes de estradas.
 * Esta classe é responsável por criar redes de estradas com base em um arquivo de configuração e no tipo de implementação fornecida.
 * 
 * @author Matheus
 */
public class RoadNetworkFactory {

    /**
     * Cria uma nova rede de estradas com base no arquivo e no tipo de implementação fornecidos.
     * 
     * @param fileName O nome do arquivo de configuração da rede de estradas.
     * @param it O tipo de implementação a ser utilizado na criação das seções de estrada.
     * @return A rede de estradas criada.
     */
    public RoadNetwork createRoadNetwork(String fileName, ImplementationType it) {
        String path = System.getProperty("user.dir") + "/src/main/resources/malhas_exemplo/" + fileName;
        RoadSectionFactory roadSectionFactory = getRoadSectionFactory(it);
        BufferedReader br;
        FileReader fr;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            Integer lengthY = Integer.valueOf(br.readLine().trim());
            Integer lengthX = Integer.valueOf(br.readLine().trim());
            Integer maxPossibleActiveVehicles = 0;
            RoadNetwork network = new RoadNetwork(lengthX, lengthY);
            RoadSection[][] roadSections = new RoadSection[lengthY][lengthX];

            for (int i = 0; i < lengthY; i++) {
                String line = br.readLine();
                String[] values = line.split("	");
                for (int j = 0; j < lengthX; j++) {
                    Integer rtStr = Integer.valueOf(values[j]);
                    RoadType rt = RoadType.valueOf(rtStr);
                    RoadSection roadSection;
                    // Adiciona pontos de entrada na malha
                    if ((i == 0 && rt.equals(RoadType.ROAD_DOWN))
                        || (i == (lengthY - 1) && rt.equals(RoadType.ROAD_UP))
                        || (j == 0 && rt.equals(RoadType.ROAD_RIGHT))
                        || (j == (lengthX - 1) && rt.equals(RoadType.ROAD_LEFT))) {

                        roadSection = roadSectionFactory.createRoadSection(network, rt, null, new Position(j, i), true);
                        network.addEntryPoint(roadSection);
                    } else {
                        roadSection = roadSectionFactory.createRoadSection(network, rt, null, new Position(j, i), false);

                    }

                    if(!roadSection.isCrossroad() && !roadSection.getType().equals(RoadType.NONE)) {
                        maxPossibleActiveVehicles++;
                    }
                    
                    roadSections[i][j] = roadSection;
                }
            }

            network.setMaxPossibleActiveVehicles(maxPossibleActiveVehicles);
            network.setRoadSections(roadSections);
            return network;

        } catch (IOException ex) {
            System.out.println("Erro ao fazer leitura do arquivo da malha: " + ex.getMessage());
        }

        return null;
    }
    
    private RoadSectionFactory getRoadSectionFactory(ImplementationType it) {
        switch (it) {
            case SEMAPHORE -> {
                return new SemaphoreRoadSectionFactory();
            }
            case MONITOR -> {
                return new MonitorRoadSectionFactory();
            }
            default -> throw new AssertionError();
        }
    }
}
