/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.strategy;

import dsd.simulator.domain.section.utils.Position;
import java.util.List;

/**
 *
 * @author Matheus
 */
public interface PathStrategy {

    List<Position> calculatePossiblePaths(Position currentPosition);
}
