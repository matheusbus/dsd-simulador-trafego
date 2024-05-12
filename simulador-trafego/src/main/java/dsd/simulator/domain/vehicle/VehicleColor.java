package dsd.simulator.domain.vehicle;

/**
 * Enumerador das cores possíveis de um veículo.
 * Cada cor é associada a um código inteiro único.
 * 
 * @author Matheus
 */
public enum VehicleColor {
    
    BLACK(1),
    BLUE(2),
    BROWN(3),
    CIAN(4),
    GRAY(5),
    GREEN(6),
    ORANGE(7),
    PINK(8),
    PURPLE(9),
    RED(10),
    WHITE(11),
    YELLOW(12);
    
    private int code;
    
    /**
     * Construtor privado da enumeração VehicleColor.
     * Associa um código inteiro a cada cor.
     * 
     * @param code O código inteiro associado à cor.
     */
    private VehicleColor(int code) {
        this.code = code;
    }
    
    /**
     * Obtém o código inteiro associado à cor.
     * 
     * @return O código inteiro associado à cor.
     */
    public int getCode() {
        return code;
    }
    
    /**
     * Retorna a cor correspondente ao código inteiro especificado.
     * 
     * @param code O código inteiro da cor desejada.
     * @return A cor correspondente ao código especificado.
     * @throws IllegalArgumentException Se o código especificado não corresponder a nenhuma cor existente.
     */
    public static VehicleColor valueOf(int code) {
        for(VehicleColor vc : VehicleColor.values()) {
            if(code == vc.getCode()) {
                return vc;
            }
        }
        throw new IllegalArgumentException("Vehicle color does not exist.");
    }
}
