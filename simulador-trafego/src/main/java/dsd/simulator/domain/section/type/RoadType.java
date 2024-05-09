/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain.section.type;

/**
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

    RoadType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
        public static RoadType valueOf(int code) {
        for(RoadType rt : RoadType.values()) {
            if(code == rt.getCode()) {
                return rt;
            }
        }
        throw new IllegalArgumentException("Road type does not exists.");
    }

}
