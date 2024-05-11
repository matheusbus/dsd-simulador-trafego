package dsd.simulator.domain.section;

import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.road.RoadNetwork;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_DOWN;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_DOWN_LEFT;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_LEFT;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_RIGHT;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_UP;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_UP_LEFT;
import static dsd.simulator.domain.section.type.RoadType.CROSSROAD_UP_RIGHT;
import static dsd.simulator.domain.section.type.RoadType.NONE;
import static dsd.simulator.domain.section.type.RoadType.ROAD_DOWN;
import static dsd.simulator.domain.section.type.RoadType.ROAD_LEFT;
import static dsd.simulator.domain.section.type.RoadType.ROAD_RIGHT;
import static dsd.simulator.domain.section.type.RoadType.ROAD_UP;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.observer.RoadSectionObservable;
import dsd.simulator.observer.RoadSectionObserver;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matheus
 */
public abstract class RoadSection implements RoadSectionObservable {

    private final List<RoadSectionObserver> observers = new ArrayList<>();

    protected final RoadNetwork roadNetwork;
    protected final RoadType type;
    protected final String imagePathStr;
    protected final Position position;
    protected final boolean isCrossroad;
    protected boolean isExitPoint;
    protected boolean isEntryPoint;

    protected Vehicle vehicle;

    private List<Position> possiblePaths;

    public RoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        this.roadNetwork = network;
        this.type = type;
        this.vehicle = vehicle;
        this.position = position;
        this.isCrossroad = type.toString().contains("CROSSROAD");
        this.isEntryPoint = isEntryPoint;
        this.possiblePaths = loadPossiblePaths();
        this.imagePathStr = loadImageStrPath();
    }

    private List<Position> loadPossiblePaths() {
        List<Position> possiblePaths = new ArrayList<>();

        if ((position.x == 0 && type.equals(ROAD_LEFT))
                || (position.x == roadNetwork.getLengthX() - 1 && type.equals(ROAD_RIGHT))) {
            this.isExitPoint = true;
            return possiblePaths;
        }

        if ((position.y == 0 && type.equals(ROAD_UP))
                || (position.y == roadNetwork.getLengthY() - 1 && type.equals(ROAD_DOWN))) {
            this.isExitPoint = true;
            return possiblePaths;
        }

        switch (type) {
            case ROAD_UP:
                possiblePaths.add(new Position(position.x, position.y - 1));
                break;
            case ROAD_DOWN:
                possiblePaths.add(new Position(position.x, position.y + 1));
                break;
            case ROAD_LEFT:
                possiblePaths.add(new Position(position.x - 1, position.y));
                break;
            case ROAD_RIGHT:
                possiblePaths.add(new Position(position.x + 1, position.y));
                break;
            case CROSSROAD_UP:
                possiblePaths.add(new Position(position.x, position.y - 1));
                break;
            case CROSSROAD_DOWN:
                possiblePaths.add(new Position(position.x, position.y + 1));
                break;
            case CROSSROAD_LEFT:
                possiblePaths.add(new Position(position.x - 1, position.y));
                break;
            case CROSSROAD_RIGHT:
                possiblePaths.add(new Position(position.x + 1, position.y));
                break;
            case CROSSROAD_UP_RIGHT:
                possiblePaths.add(new Position(position.x, position.y - 1));
                possiblePaths.add(new Position(position.x + 1, position.y));
                break;
            case CROSSROAD_UP_LEFT:
                possiblePaths.add(new Position(position.x, position.y - 1));
                possiblePaths.add(new Position(position.x - 1, position.y));
                break;
            case CROSSROAD_DOWN_RIGHT:
                possiblePaths.add(new Position(position.x, position.y + 1));
                possiblePaths.add(new Position(position.x + 1, position.y));
                break;
            case CROSSROAD_DOWN_LEFT:
                possiblePaths.add(new Position(position.x, position.y + 1));
                possiblePaths.add(new Position(position.x - 1, position.y));
                break;
            default:
                return possiblePaths;
        }

        return possiblePaths;
    }
    
    public Position forceExitCrossroad() {
        if(possiblePaths.size() == 2) {
            for(Position p: possiblePaths) {
                if(!roadNetwork.getRoadSectionAt(p.x, p.y).isCrossroad()) {
                    return p;
                }
            }
        } else {
            return possiblePaths.get(0);
        }
        
        return null;
    }
    
    public List<Position> getPossiblePaths() {
        return this.possiblePaths;
    }

    private String loadImageStrPath() {
        final String arqName;

        if (type.toString().contains("CROSSROAD")) {
            arqName = "chess";
        } else {
            switch (this.type) {
                case NONE ->
                    arqName = "none";
                case ROAD_UP ->
                    arqName = "road-up";
                case ROAD_DOWN ->
                    arqName = "road-down";
                case ROAD_LEFT ->
                    arqName = "road-left";
                case ROAD_RIGHT ->
                    arqName = "road-right";
                default ->
                    throw new AssertionError();
            }
        }

        String path = System.getProperty("user.dir") + "/src/main/resources/custom-network/" + arqName;
        if(isEntryPoint) {
            path = path.concat("-entry");
        }
        
        if(isExitPoint) {
            path = path.concat("-exit");
        }
        return  path.concat(".png");
    }

    public String getImageStrPath() {
        return this.imagePathStr;
    }

    public RoadType getType() {
        return type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        // notifica observadores
        notify(this, (this.vehicle == null));
    }

    public Position getPosition() {
        return position;
    }
    
    public boolean isCrossroad() {
        return isCrossroad;
    }

    @Override
    public void addObserver(RoadSectionObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(RoadSectionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(RoadSection roadSection, boolean hasVehicle) {
        for (RoadSectionObserver observer : observers) {
            observer.onSectionStateChanged(roadSection, hasVehicle);
        }
    }

}
