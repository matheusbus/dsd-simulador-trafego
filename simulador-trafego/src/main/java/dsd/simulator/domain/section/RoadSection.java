package dsd.simulator.domain.section;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.road.RoadNetwork;
import dsd.simulator.domain.vehicle.Vehicle;
import dsd.simulator.observer.RoadSectionObservable;
import dsd.simulator.observer.RoadSectionObserver;
import dsd.simulator.strategy.PathStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstração da seção da malha para implementação concreta de seção com semáforos e monitores.
 * Provê métodos para tentar entrar em uma seção e para sair dela, além de notificar observadores sobre mudanças de estado.
 * 
 * @author Matheus
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

    private final PathStrategy pathStrategy;
    private final List<Position> possiblePaths;

    /**
     * Constrói uma nova seção de estrada com os parâmetros especificados.
     * 
     * @param network A rede de estradas à qual a seção pertence.
     * @param type O tipo de estrada.
     * @param vehicle O veículo associado à seção.
     * @param position A posição da seção na malha.
     * @param isEntryPoint Indica se a seção é um ponto de entrada.
     */
    public RoadSection(RoadNetwork network, RoadType type, Vehicle vehicle, Position position, boolean isEntryPoint) {
        this.roadNetwork = network;
        this.type = type;
        this.vehicle = vehicle;
        this.position = position;
        this.isCrossroad = type.toString().contains("CROSSROAD");
        this.isEntryPoint = isEntryPoint;
        this.pathStrategy = getPathStrategy(type);
        this.possiblePaths = loadPossiblePaths();
        this.imagePathStr = loadImageStrPath();
    }
    
    /**
     * Método abstrato para tentar entrar na seção.
     * 
     * @param time O tempo de espera.
     * @return true se o veículo conseguiu entrar na seção, false caso contrário.
     * @throws InterruptedException Se ocorrer uma interrupção enquanto o veículo aguarda para entrar na seção.
     */
    public abstract boolean tryEnter(long time) throws InterruptedException;
    
    /**
     * Método abstrato para sair da seção.
     */
    public abstract void exit();

    private PathStrategy getPathStrategy(RoadType type) {
        return PathStrategy.getPathStrategy(type);
    }
    
    private List<Position> loadPossiblePaths() {
        List<Position> paths = new ArrayList<>();

        if ((position.x == 0 && type.equals(RoadType.ROAD_LEFT))
                || (position.x == roadNetwork.getLengthX() - 1 && type.equals(RoadType.ROAD_RIGHT))) {
            this.isExitPoint = true;
            return paths;
        }

        if ((position.y == 0 && type.equals(RoadType.ROAD_UP))
                || (position.y == roadNetwork.getLengthY() - 1 && type.equals(RoadType.ROAD_DOWN))) {
            this.isExitPoint = true;
            return paths;
        }
        
        paths = pathStrategy.calculatePossiblePaths(position);
        return paths;
    }
    
    /**
     * Força a saída de um cruzamento.
     * 
     * @return A posição para a qual o veículo deve ir após sair do cruzamento.
     */
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
    
    /**
     * Obtém a lista de posições possíveis para seguir a partir desta seção.
     * 
     * @return A lista de posições possíveis.
     */
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

    /**
     * Obtém o caminho da imagem associada a esta seção.
     * 
     * @return O caminho da imagem associada a esta seção.
     */
    public String getImageStrPath() {
        return this.imagePathStr;
    }

    /**
     * Obtém o tipo desta seção.
     * 
     * @return O tipo desta seção.
     */
    public RoadType getType() {
        return type;
    }

    /**
     * Obtém o veículo associado a esta seção.
     * 
     * @return O veículo associado a esta seção.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Define o veículo associado a esta seção.
     * 
     * @param vehicle O veículo a ser associado a esta seção.
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        // notifica observadores
        notify(this, (this.vehicle == null));
    }

    /**
     * Obtém a posição desta seção na malha.
     * 
     * @return A posição desta seção na malha.
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Verifica se esta seção é um cruzamento.
     * 
     * @return true se esta seção é um cruzamento, false caso contrário.
     */
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
