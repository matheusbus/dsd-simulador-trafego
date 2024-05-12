package dsd.simulator.domain.road;

import dsd.simulator.domain.section.RoadSection;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.factory.vehicle.VehicleFactory;
import dsd.simulator.observer.RoadNetworkObservable;
import dsd.simulator.observer.RoadNetworkObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma rede de estradas simulada. Esta classe contém métodos para
 * iniciar, interromper e monitorar a simulação, bem como gerenciar veículos e
 * seções de estrada. Implementa a interface RoadNetworkObservable para permitir
 * a observação do número de veículos ativos na rede.
 *
 * @author Matheus
 */
public class RoadNetwork implements RoadNetworkObservable {

    private final List<RoadNetworkObserver> observers = new ArrayList<>();
    protected RoadSection[][] roadSections;

    protected final int lengthX;
    protected final int lengthY;

    protected boolean active;
    protected boolean interruptedImmediately;
    protected List<Vehicle> activeVehicles;
    protected Integer maxActiveVechiles;
    protected Integer insertionRange;
    protected Integer maxPossibleActiveVehicles;
    protected VehicleFactory vehicleFactory;

    private final List<RoadSection> entryPoints;

    /**
     * Constrói uma nova rede de estradas com o comprimento especificado.
     *
     * @param lengthX O comprimento horizontal da rede de estradas.
     * @param lengthY O comprimento vertical da rede de estradas.
     */
    public RoadNetwork(int lengthX, int lengthY) {
        this.entryPoints = new ArrayList<>();
        this.activeVehicles = new ArrayList<>();
        this.lengthX = lengthX;
        this.lengthY = lengthY;

        this.active = true;
        this.interruptedImmediately = false;
    }

    /**
     * Inicia a simulação da rede de estradas. Este método cria e inicia uma
     * thread de simulação que insere veículos na rede enquanto a simulação
     * estiver ativa.
     */
    public void startSimulation() {
        this.vehicleFactory = new VehicleFactory();

        Thread simulationThread = new Thread(() -> {
            // Enquanto estiver ativa irá inserir veículos respeitando o limite definido
            while (isActive()) {
                if (activeVehicles.size() < this.maxActiveVechiles) {

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

    /**
     * Interrompe a simulação da rede de estradas. Este método define o status
     * da simulação como inativo.
     *
     * @throws InterruptedException Se ocorrer um erro durante a interrupção da
     * simulação.
     */
    public void stopSimulation() throws InterruptedException {
        this.active = false;
    }

    /**
     * Interrompe imediatamente a simulação da rede de estradas. Este método
     * define o status da simulação como inativo e sinaliza uma interrupção
     * imediata.
     *
     * @throws InterruptedException Se ocorrer um erro durante a interrupção da
     * simulação.
     */
    public void immediatelyStopSimulation() throws InterruptedException {
        this.active = false;
        this.interruptedImmediately = true;
    }

    /**
     * Reinicia a simulação da rede de estradas. Este método define o status 
     * da rede como ativa novamente e reseta a lista de veículos ativos.
     */
    public void restartSimulation() {
        this.activeVehicles = new ArrayList<>();
        this.active = true;
    }

    /**
     * Adiciona um veículo à lista de veículos ativos na rede de estradas.
     *
     * @param v O veículo a ser adicionado.
     */
    public synchronized void addVehicle(Vehicle v) {
        this.activeVehicles.add(v);

        notify(this.activeVehicles.size());
    }

    /**
     * Remove um veículo da lista de veículos ativos na rede de estradas.
     *
     * @param v O veículo a ser removido.
     */
    public synchronized void removeVehicle(Vehicle v) {
        if (activeVehicles.indexOf(v) != -1) {
            v.setActive(false);
            activeVehicles.remove(v);
        }

        notify(this.activeVehicles.size());
    }

    public List<Vehicle> getActiveVehicles() {
        return activeVehicles;
    }

    public void setActiveVehicles(List<Vehicle> activeVehicles) {
        this.activeVehicles = activeVehicles;
    }

    public RoadNetwork setMaxActiveVehicles(Integer maxActiveVehicles) {
        this.maxActiveVechiles = maxActiveVehicles;
        return this;
    }

    public Integer getMaxActiveVehicles() {
        return maxActiveVechiles;
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public Integer getMaxPossibleActiveVehicles() {
        return maxPossibleActiveVehicles;
    }

    public void setMaxPossibleActiveVehicles(Integer maxPossibleActiveVehicles) {
        this.maxPossibleActiveVehicles = maxPossibleActiveVehicles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public boolean isInterruptedImmediately() {
        return interruptedImmediately;
    }

    public void setInterruptedImmediately(boolean interruptedImmediately) {
        this.interruptedImmediately = interruptedImmediately;
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
