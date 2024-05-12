package dsd.simulator.domain;

/**
 * Enumerador dos mecanismos de controle de exclusão mútua implementados
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
