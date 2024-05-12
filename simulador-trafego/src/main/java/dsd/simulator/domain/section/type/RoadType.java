package dsd.simulator.domain.section.type;

/**
 * Enumeração que representa os tipos de estradas possíveis na malha.
 * Cada tipo de estrada é associado a um código inteiro único.
 * 
 * @author Matheus
 */
public enum RoadType {

    NONE(0),
    ROAD_UP(1),
    ROAD_RIGHT(2),
    ROAD_DOWN(3),
    ROAD_LEFT(4),
    CROSSROAD_UP(5),
    CROSSROAD_RIGHT(6),
    CROSSROAD_DOWN(7),
    CROSSROAD_LEFT(8),
    CROSSROAD_UP_RIGHT(9),
    CROSSROAD_UP_LEFT(10),
    CROSSROAD_DOWN_RIGHT(11),
    CROSSROAD_DOWN_LEFT(12);

    private final int code;

    /**
     * Construtor privado da enumeração RoadType.
     * Associa um código inteiro a cada tipo de estrada.
     * 
     * @param code O código inteiro associado ao tipo de estrada.
     */
    RoadType(int code) {
        this.code = code;
    }

    /**
     * Obtém o código inteiro associado ao tipo de estrada.
     * 
     * @return O código inteiro associado ao tipo de estrada.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retorna o tipo de estrada correspondente ao código inteiro especificado.
     * 
     * @param code O código inteiro do tipo de estrada desejado.
     * @return O tipo de estrada correspondente ao código especificado.
     * @throws IllegalArgumentException Se o código especificado não corresponder a nenhum tipo de estrada existente.
     */
    public static RoadType valueOf(int code) {
        for (RoadType rt : RoadType.values()) {
            if (code == rt.getCode()) {
                return rt;
            }
        }
        throw new IllegalArgumentException("Road type does not exist.");
    }

}