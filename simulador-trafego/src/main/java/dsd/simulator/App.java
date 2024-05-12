package dsd.simulator;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import dsd.simulator.controller.InitialMenuController;

/**
 * Classe principal da aplicação
 * 
 * Esta classe é responsável por iniciar a aplicação e chamar o controlador da tela inicial.
 * 
 * @author Matheus
 */
public class App {
    
    /**
     * Método principal da aplicação
     * 
     * Este método inicia a aplicação, configurando o tema da interface e chamando o controlador da tela inicial.
     * 
     * @param args Os argumentos da linha de comando (não utilizados neste caso)
     */
    public static void main(String[] args) {
        // Configura o tema da interface
        FlatMaterialDarkerIJTheme.setup();
        
        // Chamada do controlador da tela inicial
        var initialMenuController = new InitialMenuController();
    }
    
}
