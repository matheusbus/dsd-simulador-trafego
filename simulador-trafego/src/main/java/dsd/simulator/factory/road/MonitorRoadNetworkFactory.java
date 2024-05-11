package dsd.simulator.factory.road;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.MonitorRoadSection;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.section.type.RoadType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Fábrica responsável por criar uma malha contendo seções com exclusão mútua do
 * tipo monitor
 *
 * @author matheus
 */
public class MonitorRoadNetworkFactory extends RoadNetworkFactory {

    @Override
    public RoadNetwork createRoadNetwork(String fileName) {
        String path = System.getProperty("user.dir") + "/src/main/resources/malhas_exemplo/" + fileName;
        BufferedReader br;
        FileReader fr;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            Integer lengthY = Integer.valueOf(br.readLine().trim());
            Integer lengthX = Integer.valueOf(br.readLine().trim());
            RoadNetwork network = new RoadNetwork(lengthX, lengthY);
            RoadSection[][] roadSections = new MonitorRoadSection[lengthY][lengthX];

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

                        roadSection = new MonitorRoadSection(network, rt, null, new Position(j, i), true);
                        network.addEntryPoint(roadSection);
                    } else {
                        roadSection = new MonitorRoadSection(network, rt, null, new Position(j, i), false);

                    }

                    roadSections[i][j] = roadSection;
                }
            }

            network.setRoadSections(roadSections);
            return network;

        } catch (IOException ex) {
            System.out.println("Erro ao fazer leitura do arquivo da malha: " + ex.getMessage());
        }

        return null;
    }

}
