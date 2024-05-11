package dsd.simulator.domain.road;

import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.factory.vehicle.VehicleFactory;
import dsd.simulator.observer.RoadNetworkObservable;
import dsd.simulator.observer.RoadNetworkObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoadNetwork implements RoadNetworkObservable {

    private final List<RoadNetworkObserver> observers = new ArrayList<>();
    protected RoadSection[][] roadSections;

    protected final int lengthX;
    protected final int lengthY;

    protected boolean active;
    protected List<Vehicle> activeVehicles;
    protected Integer maxActiveVechiles;
    protected Integer insertionRange;
    protected VehicleFactory vehicleFactory;

    private final List<RoadSection> entryPoints;

    public RoadNetwork(int lengthX, int lengthY) {
        this.entryPoints = new ArrayList<>();
        this.activeVehicles = new ArrayList<>();
        this.lengthX = lengthX;
        this.lengthY = lengthY;

        this.active = true;
    }

    public void startSimulation() {
        this.vehicleFactory = new VehicleFactory();

        Thread simulationThread = new Thread(() -> {
            // Enquanto estiver ativa irá inserir veículos respeitando o limite definido
            while (isActive()) {
                if (activeVehicles.size() < maxActiveVechiles) {

                    // Insere um novo veículo na malha
                    var v = vehicleFactory.createVehicle(this);
                    addVehicle(v);

                    v.start();
                }

                // Intervalo de tempo de inserção
                try {
                    Thread.sleep(insertionRange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        simulationThread.start();
    }

    public void stopSimulation() {
        this.active = false;
    }

    public void immediatelyStopSimulation() {
        this.active = false;

        for (Vehicle v : activeVehicles) {
            v.setActive(false);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RoadNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < lengthY; i++) {
            for (int j = 0; j < lengthY; j++) {
                RoadSection section = roadSections[i][j];
                section.setVehicle(null);
            }
        }
    }

    public RoadNetwork setMaxActiveVehicles(Integer maxActiveVehicles) {
        this.maxActiveVechiles = maxActiveVehicles;
        return this;
    }

    public Integer getMaxActiveVehicles() {
        return maxActiveVechiles;
    }

    public void addVehicle(Vehicle v) {
        this.activeVehicles.add(v);

        notify(this.activeVehicles.size());
    }

    public void removeVehicle(Vehicle v) {
        if (activeVehicles.indexOf(v) != -1) {
            v.setActive(false);
            activeVehicles.remove(v);
        }

        notify(this.activeVehicles.size());
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public RoadSection[][] getRoadSections() {
        return roadSections;
    }

    public void setRoadSections(RoadSection[][] roadSections) {
        this.roadSections = roadSections;
    }

    public Integer getInsertionRange() {
        return insertionRange;
    }

    public RoadNetwork setInsertionRange(Integer insertionRange) {
        this.insertionRange = insertionRange;
        return this;
    }

    public RoadSection getRoadSectionAt(int x, int y) {
        RoadSection rs;
        try {
            rs = this.roadSections[y][x];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
        return rs;
    }

    public void addEntryPoint(RoadSection entryPoint) {
        this.entryPoints.add(entryPoint);
    }

    public List<RoadSection> getEntryPoints() {
        return this.entryPoints;
    }

    @Override
    public void addObserver(RoadNetworkObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(RoadNetworkObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(int numberOfVehicles) {
        for (RoadNetworkObserver o : this.observers) {
            o.onNumberOfVehiclesChanged(numberOfVehicles, active);
        }
    }

}
