package dsd.simulator.controller;

import dsd.simulator.domain.ImplementationType;
import dsd.simulator.view.InitialMenuView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador da tela inicial da aplicação
 * 
 * Este controlador gerencia as interações da tela inicial da aplicação, incluindo
 * a seleção de tipos de implementação e arquivos de estradas.
 * 
 * @author matheus
 */
public final class InitialMenuController {

    private InitialMenuView imv;

    /**
     * Construtor da classe InitialMenuController
     */
    public InitialMenuController() {
        this.imv = new InitialMenuView();
        initComponents();

        imv.setVisible(true);
    }

    /**
     * Inicializa os componentes da tela inicial
     */
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

    /**
     * Inicia a aplicação com a implementação e o arquivo de estrada selecionados
     */
    public void letsStartApplication() {
        ImplementationType selectedImplementation = ImplementationType.valueOf(imv.getSelectedImplementationType());
        String selectedRoad = imv.getSelectedRoadFile();
        var c = new RoadNetworkController(selectedRoad, selectedImplementation);

        imv.dispose();
    }

    /**
     * Carrega os tipos de implementação disponíveis
     * 
     * @return Uma lista de strings representando os tipos de implementação
     */
    public List<String> loadImplementationTypes() {
        ImplementationType[] types = ImplementationType.values();
        List<String> typeStrings = new ArrayList<>();
        for (ImplementationType type : types) {
            typeStrings.add(type.toString());
        }
        return typeStrings;
    }
    
    /**
     * Carrega os nomes dos arquivos de estrada disponíveis
     * 
     * @return Uma lista de strings representando os nomes dos arquivos de estrada
     */
    public List<String> loadNameOfRoadFiles() {
        List<String> fileNames = new ArrayList<>();
        String path = System.getProperty("user.dir") + "/src/main/resources/malhas_exemplo";
        File dir = new File(path);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles((File dir1, String name) -> name.toLowerCase().endsWith(".txt"));

            if (files != null && files.length > 0) {
                for (File f : files) {
                    fileNames.add(f.getName());
                }
            }

            fileNames.sort(String::compareToIgnoreCase);
        }

        return fileNames;
    }
}
