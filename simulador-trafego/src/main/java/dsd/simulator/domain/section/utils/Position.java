package dsd.simulator.domain.section.utils;

/**
 * Classe utilitária para representação de uma posição na malha.
 * Contém as coordenadas x e y da posição.
 * 
 * @author Matheus
 */
public class Position {
    
    public Integer x;
    public Integer y;

    /**
     * Construtor da classe Position.
     * 
     * @param x A coordenada x da posição.
     * @param y A coordenada y da posição.
     */
    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}