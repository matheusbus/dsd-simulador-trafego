package dsd.simulator.strategy.impl;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.strategy.PathStrategy;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class RightPathStrategy implements PathStrategy {

    @Override
    public List<Position> calculatePossiblePaths(Position currentPosition) {
        return Collections.singletonList(new Position(currentPosition.x + 1, currentPosition.y));
    }
    
}
