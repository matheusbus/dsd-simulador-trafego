/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.view.lixo;

import dsd.simulator.domain.RoadType;

/**
 *
 * @author matheus
 */
public class Testes {
    
    public static void main(String[] args) {
        
        RoadType rt = RoadType.CROSSROAD_DOWN;
        System.out.println(rt.toString());
        System.out.println(rt.toString().contains("CROSSROAD"));
        
    }
    
}
