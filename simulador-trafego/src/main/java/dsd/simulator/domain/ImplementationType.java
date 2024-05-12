/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd.simulator.domain;

/**
 *
 * @author matheus
 */
public enum ImplementationType {

    SEMAPHORE(1),
    MONITOR(2);
    
    private int code;

    private ImplementationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ImplementationType valueOf(int code) {
        for (ImplementationType it : ImplementationType.values()) {
            if (code == it.getCode()) {
                return it;
            }
        }
        throw new IllegalArgumentException("Implementation Type does not exists.");
    }

}
