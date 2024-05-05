package dsd.simulator.domain.vehicle;

public enum VehicleColor {
    
    BLACK(1),
    BLUE(2),
    GREEN(3),
    PINK(4),
    WHITE(5),
    YELLOW(6);
    
    
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
