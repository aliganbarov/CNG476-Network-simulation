package Settings;

import java.util.ArrayList;

/**
 * Created by AliPC on 03-Jan-18.
 */
public class SimulationResults {
    private double totalTime;
    private double throughput;
    private int numberOfPacketsArrived;

    public SimulationResults(double totalTime, double throughput, int numberOfPacketsArrived) {
        this.totalTime = totalTime;
        this.throughput = throughput;
        this.numberOfPacketsArrived = numberOfPacketsArrived;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    public int getNumberOfPacketsArrived() {
        return numberOfPacketsArrived;
    }

    public void setNumberOfPacketsArrived(int numberOfPacketsArrived) {
        this.numberOfPacketsArrived = numberOfPacketsArrived;
    }
}
