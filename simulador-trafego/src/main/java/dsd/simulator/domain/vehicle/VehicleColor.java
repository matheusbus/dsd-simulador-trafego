package dsd.simulator.domain.vehicle;

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
    
    private VehicleColor(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static VehicleColor valueOf(int code) {
        for(VehicleColor vc : VehicleColor.values()) {
            if(code == vc.getCode()) {
                return vc;
            }
        }
        throw new IllegalArgumentException("Vehicle color does not exists.");
    }
}
