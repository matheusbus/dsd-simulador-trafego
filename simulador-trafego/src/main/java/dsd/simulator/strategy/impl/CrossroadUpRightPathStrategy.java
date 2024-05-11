/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.strategy.impl;

import dsd.simulator.domain.section.utils.Position;
import dsd.simulator.strategy.PathStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class CrossroadUpRightPathStrategy implements PathStrategy {

    @Override
    public List<Position> calculatePossiblePaths(Position currentPosition) {
        List<Position> possiblePaths = new ArrayList<>();
        possiblePaths.add(new Position(currentPosition.x, currentPosition.y - 1));
        possiblePaths.add(new Position(currentPosition.x + 1, currentPosition.y));
        return possiblePaths;
    }
    
}
