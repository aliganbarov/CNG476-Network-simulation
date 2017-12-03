import Models.Packets.DataPacket;
import Models.SimulationResult;
import Parameters.Globals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class Main {

    public static void main(String[] args) {
        int numbOfIterations = 10;
        SinglePathNetworkManager singlePathNetworkManager = new SinglePathNetworkManager();
        ArrayList<SimulationResult> simulationResults = new ArrayList<>();
        for (int i = 0; i < numbOfIterations; i++) {
            SimulationResult simulationResult = singlePathNetworkManager.simulate();
            simulationResults.add(simulationResult);
            Globals.CURRENT_TIME = 0;
            Globals.TOTAL_NUMBER_OF_PACKETS = 0;
            Globals.FAILED_PACKETS = 0;
            Globals.DELIVERED_PACKETS = 0;
            new DataPacket().resetDataPacketNumb();
        }

        float totalDeliveryFraction = 0;
        for (int i = 0; i < simulationResults.size(); i++) {
            totalDeliveryFraction += simulationResults.get(i).getTotalNumberOfDeliveredPackets() * 1.0 /
                    simulationResults.get(i).getTotalNumberOfPackets();
        }
        System.out.println("Average delivery fraction: " + totalDeliveryFraction / numbOfIterations);

        // write results to file
        try {
            PrintWriter pw = new PrintWriter(new File("results.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("Total Time");
            sb.append(",");
            sb.append("Total number of packets");
            sb.append(",");
            sb.append("Total number of delivered packets");
            sb.append(",");
            sb.append("Total number of failed packets");
            sb.append("\n");
            for (int i = 0; i < simulationResults.size(); i++) {
                sb.append(simulationResults.get(i).getTotalTime());
                sb.append(",");
                sb.append(simulationResults.get(i).getTotalNumberOfPackets());
                sb.append(",");
                sb.append(simulationResults.get(i).getTotalNumberOfDeliveredPackets());
                sb.append(",");
                sb.append(simulationResults.get(i).getTotalNumberOfFailedPackets());
                sb.append("\n");
            }
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e ) {
            e.printStackTrace();
        }

    }

}
