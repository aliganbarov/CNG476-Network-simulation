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

    public SimulationResult() {
        totalNumberOfPackets = Globals.TOTAL_NUMBER_OF_PACKETS;
        totalNumberOfDeliveredPackets = Globals.DELIVERED_PACKETS;
        totalNumberOfFailedPackets = Globals.FAILED_PACKETS;
        totalTime = Globals.CURRENT_TIME;
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
}
