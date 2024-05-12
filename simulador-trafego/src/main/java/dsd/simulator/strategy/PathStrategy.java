package dsd.simulator.strategy;

import dsd.simulator.domain.section.type.RoadType;
import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.strategy.impl.CrossroadDownLeftPathStrategy;
import dsd.simulator.strategy.impl.CrossroadDownPathStrategy;
import dsd.simulator.strategy.impl.CrossroadDownRightPathStrategy;
import dsd.simulator.strategy.impl.CrossroadLeftPathStrategy;
import dsd.simulator.strategy.impl.CrossroadRightPathStrategy;
import dsd.simulator.strategy.impl.CrossroadUpLeftPathStrategy;
import dsd.simulator.strategy.impl.CrossroadUpPathStrategy;
import dsd.simulator.strategy.impl.CrossroadUpRightPathStrategy;
import dsd.simulator.strategy.impl.DownPathStrategy;
import dsd.simulator.strategy.impl.LeftPathStrategy;
import dsd.simulator.strategy.impl.NonePathStrategy;
import dsd.simulator.strategy.impl.RightPathStrategy;
import dsd.simulator.strategy.impl.UpPathStrategy;
import java.util.List;

/**
 * A interface PathStrategy define o contrato para estratégias de cálculo de possíveis caminhos.
 * Cada estratégia implementa o método calculatePossiblePaths para determinar os caminhos possíveis a partir de uma posição atual.
 * Além disso, fornece um método estático para obter a estratégia de caminho adequada com base no tipo de estrada.
 * 
 * @author Matheus
 */
public interface PathStrategy {

    /**
     * Calcula os possíveis caminhos a partir da posição atual.
     * 
     * @param currentPosition A posição atual no mapa da estrada
     * @return Uma lista de posições representando os caminhos possíveis
     */
    List<Position> calculatePossiblePaths(Position currentPosition);

    /**
     * Obtém a estratégia de caminho apropriada com base no tipo de estrada.
     * 
     * @param roadType O tipo de estrada para o qual se deseja obter a estratégia de caminho
     * @return A estratégia de caminho correspondente ao tipo de estrada
     */
    static PathStrategy getPathStrategy(RoadType roadType) {
        switch (roadType) {
            case NONE -> {
                return new NonePathStrategy();
            }
            case ROAD_UP -> {
                return new UpPathStrategy();
            }
            case ROAD_RIGHT -> {
                return new RightPathStrategy();
            }
            case ROAD_DOWN -> {
                return new DownPathStrategy();
            }
            case ROAD_LEFT -> {
                return new LeftPathStrategy();
            }
            case CROSSROAD_UP -> {
                return new CrossroadUpPathStrategy();
            }
            case CROSSROAD_RIGHT -> {
                return new CrossroadRightPathStrategy();
            }
            case CROSSROAD_DOWN -> {
                return new CrossroadDownPathStrategy();
            }
            case CROSSROAD_LEFT -> {
                return new CrossroadLeftPathStrategy();
            }
            case CROSSROAD_UP_RIGHT -> {
                return new CrossroadUpRightPathStrategy();
            }
            case CROSSROAD_UP_LEFT -> {
                return new CrossroadUpLeftPathStrategy();
            }
            case CROSSROAD_DOWN_RIGHT -> {
                return new CrossroadDownRightPathStrategy();
            }
            case CROSSROAD_DOWN_LEFT -> {
                return new CrossroadDownLeftPathStrategy();
            }
            default ->
                throw new AssertionError();
        }
    }
}
