/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.controller;

import dsd.simulator.view.InitialMenuView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author matheus
 */
public final class InitialMenuController {

    private InitialMenuView imv;

    public InitialMenuController() {
        this.imv = new InitialMenuView();
        initComponents();

        imv.setVisible(true);
    }

    public void initComponents() {
        imv.addActionBtnLetsStart((ActionEvent e) -> {
            letsStartApplication();
        });

        imv.setCbImplementationTypeItems(
                loadImplementationTypes()
        );

        imv.setCbRoadFileItems(
                loadNameOfRoadFiles()
        );
    }

    public void letsStartApplication() {
        String selectedImplementation = imv.getSelectedImplementationType();
        String selectedRoad = imv.getSelectedRoadFile();
        var c = new RoadNetworkController(selectedRoad, selectedImplementation);

        imv.dispose();
    }
    
    public List<String> loadImplementationTypes() {
        return Arrays.asList("Semaphore", "Monitor", "Troca de Mensagem");
    }

    public List<String> loadNameOfRoadFiles() {
        List<String> fileNames = new ArrayList<>();
        String path = System.getProperty("user.dir") + "/src/main/resources/malhas_exemplo";
        File dir = new File(path);
        
        if(dir.isDirectory()) {
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            });
            
            if(files != null && files.length > 0) {
                for(File f: files) {
                    fileNames.add(f.getName());
                }
            }
            
            fileNames.sort(String::compareToIgnoreCase);
        }
        
        return fileNames;
    }
}
