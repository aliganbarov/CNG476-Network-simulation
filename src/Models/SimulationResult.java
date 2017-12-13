package Models;

import Parameters.Globals;

/**
 * Created by AliPC on 03-Dec-17.
 */
public class SimulationResult {
    private int totalNumberOfPackets;
    private int totalNumberOfFailedPackets;
    private int totalNumberOfDeliveredPackets;
    private int totalTime;
    private int totalNumberOfTransmittedPackets;

    public SimulationResult() {
        totalNumberOfPackets = Globals.TOTAL_NUMBER_OF_PACKETS;
        totalNumberOfDeliveredPackets = Globals.DELIVERED_PACKETS;
        totalNumberOfFailedPackets = Globals.FAILED_PACKETS;
        totalTime = Globals.CURRENT_TIME;
        totalNumberOfTransmittedPackets = Globals.PACKETS_TRANSMITTED;
    }

    public int getTotalNumberOfPackets() {
        return totalNumberOfPackets;
    }

    public int getTotalNumberOfFailedPackets() {
        return totalNumberOfFailedPackets;
    }

    public int getTotalNumberOfDeliveredPackets() {
        return totalNumberOfDeliveredPackets;
    }

    public int getTotalTime() {
        return totalTime;
    }
    
    public int getTotalNumberOfTransmittedPackets() {
    	return totalNumberOfTransmittedPackets;
    }
}
