package dsd.simulator;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicIJTheme;
import dsd.simulator.controller.InitialMenuController;

/**
 *
 * @author Matheus
 */
public class App {
    
    public static void main(String[] args) {
        FlatMaterialOceanicIJTheme.setup();
        
        // Chamada do controlador da tela inicial
        var c = new InitialMenuController();
    }
    
}
