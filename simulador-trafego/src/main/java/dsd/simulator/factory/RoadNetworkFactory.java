/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.factory;

import dsd.simulator.domain.RoadNetwork;
import dsd.simulator.domain.RoadSection;
import dsd.simulator.domain.RoadType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Matheus
 */
public class RoadNetworkFactory {
    // implementar a leitura de acordo com o número passado por parâmetro

    public static RoadNetwork createRoadNetwork(int type) {
        // O tipo deve estar entre: 1,2,3
        if (!Arrays.asList(1, 2, 3).contains(type)) {
            throw new IllegalArgumentException("O tipo de rodovia não existe.");
        }

        String path = System.getProperty("user.dir") + "/src/main/resources/malhas_exemplo/malha-exemplo-" + type + ".txt";
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            Integer y = Integer.valueOf(br.readLine().trim());
            Integer x = Integer.valueOf(br.readLine().trim());
            RoadSection[][] roadSections = new RoadSection[y][x];

            for (int i = 0; i < y; i++) {
                String line = br.readLine();
                String[] values = line.split("	");
                for (int j = 0; j < x; j++) {
                    Integer rtStr = Integer.valueOf(values[j]);
                    RoadType rt = RoadType.valueOf(rtStr);
                    RoadSection roadSection = new RoadSection(rt, null, j, i);
                    roadSections[i][j] = roadSection;
                }
            }
            
            RoadNetwork network = new RoadNetwork();
            network.setRoadSections(roadSections);
            return network;

        } catch (IOException ex) {
            System.out.println("Erro ao fazer leitura do arquivo da malha: " + ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
    
    public int[] calculateNextSections(RoadType rt, int i, int j) {
        return switch (rt) {
            case NONE -> null;
            case ROAD_DOWN -> new int[]{i, j+1};
            case ROAD_UP -> new int[]{i, j-1};
            case ROAD_LEFT -> new int[]{i-1, j};
            case ROAD_RIGHT -> new int[]{i+1, j};
            default -> null;
        };
    }

    public static void imprimir(RoadNetwork roadNetwork) {
        RoadSection[][] roadSections = roadNetwork.getRoadSections();
        // Print the matrix
        System.out.println("Matrix:");
        for (int i = 0; i < roadSections.length; i++) {
            for (int j = 0; j < roadSections[i].length; j++) {
                System.out.print(roadSections[i][j].getType().getCode() + "[" + i + "]" + "[" + j + "] ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }

    public static void main(String[] args) {
        imprimir(createRoadNetwork(1));

    }

}
