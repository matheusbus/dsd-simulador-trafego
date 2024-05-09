package dsd.simulator.domain.vehicle;

import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.section.RoadSection;
import java.util.Random;

public abstract class Vehicle extends Thread {

    protected Random rand = new Random();

    protected RoadNetwork roadNetwork;
    protected RoadSection roadSection;
    protected VehicleColor color;
    protected Integer sleepTime;

    protected String imagePathStr;
    protected boolean active;

    public Vehicle() {
    }

    public Vehicle(VehicleColor color, Integer sleepTime, RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
        this.color = color;
        this.sleepTime = sleepTime;

        this.imagePathStr = System.getProperty("user.dir") + "/src/main/resources/vehicle-" + color.toString().toLowerCase() + ".png";
    }

    public String getImagePathStr() {
        return imagePathStr;
    }

    public VehicleColor getColor() {
        return color;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /*
    @Override
    public void run() {
        Random rand = new Random();

        boolean entered = false;
        try {
            while (!entered) {
                entered = roadSection.enter(sleepTime);

                if (entered) {
                    roadSection.setVehicle(this);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            
            
            RoadSection next = getNextRoadSection();
            
            while (next != null) {
                entered = next.enter(sleepTime);

                if (entered) {
                    this.roadSection.exit();
                    this.roadSection = next;
                } else {
                    sleep(rand.nextInt(500));
                }
            }
            
            sleep(sleepTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RoadSection getNextRoadSection() {
        int[] newPosition;

        switch (roadSection.getType()) {
            case ROAD_UP:
                newPosition = new int[]{roadSection.getPositionX(), roadSection.getPositionY() + 1};
                break;
            case ROAD_DOWN:
                newPosition = new int[]{roadSection.getPositionX(), roadSection.getPositionY() - 1};
                break;
            case ROAD_LEFT:
                newPosition = new int[]{roadSection.getPositionX() - 1, roadSection.getPositionY()};
                break;
            case ROAD_RIGHT:
                newPosition = new int[]{roadSection.getPositionX() + 1, roadSection.getPositionY()};
                break;
            case NONE:
                return null;
            default:
                throw new AssertionError();
        }
        return roadNetwork.getRoadSectionAt(newPosition[0], newPosition[1]);
    }
     */
}
