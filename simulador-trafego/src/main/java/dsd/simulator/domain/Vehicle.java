package dsd.simulator.domain;

public class Vehicle extends Thread {

    private final VehicleColor color;
    private final Integer sleepTime;

    public Vehicle(VehicleColor color, Integer sleepTime) {
        this.color = color;
        this.sleepTime = sleepTime;
    }

    public VehicleColor getColor() {
        return color;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    @Override
    public void run() {
        super.run();
    }
    
    

}
