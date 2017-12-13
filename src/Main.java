import Models.Packets.DataPacket;
import Models.SimulationResult;
import Parameters.Globals;
import Parameters.Statics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class Main {

    public static void main(String[] args) {
        int numbOfIterations = 1000;
        SinglePathNetworkManager singlePathNetworkManager = new SinglePathNetworkManager();
        ArrayList<SimulationResult> simulationResults = new ArrayList<>();
//        for (int i = 0; i < numbOfIterations; i++) {
//            SimulationResult simulationResult = singlePathNetworkManager.simulate();
//            simulationResults.add(simulationResult);
//            Globals.PACKETS_TRANSMITTED = 0;
//            Globals.CURRENT_TIME = 0;
//            Globals.TOTAL_NUMBER_OF_PACKETS = 0;
//            Globals.FAILED_PACKETS = 0;
//            Globals.DELIVERED_PACKETS = 0;
//            new DataPacket().resetDataPacketNumb();
//        }
//
//        float totalDeliveryFraction = 0;
//        for (int i = 0; i < simulationResults.size(); i++) {
//            totalDeliveryFraction += simulationResults.get(i).getTotalNumberOfDeliveredPackets() * 1.0 /
//                    simulationResults.get(i).getTotalNumberOfPackets();
//        }
        // System.out.println("Average delivery fraction: " + totalDeliveryFraction / numbOfIterations);

        // write results to file
        try {
        	// writing results for # of packets
        	
            PrintWriter pw = new PrintWriter(new File("PacketsResult.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("Total time");
            sb.append(",");
            sb.append("Total # of packets");
            sb.append(",");
            sb.append("New packet probability");
            sb.append(",");
            sb.append("Node failure rate");
            sb.append(",");
            sb.append("Number of intermediate nodes");
            sb.append(",");
            sb.append("Packet delivery fraction");
            sb.append(",");
            sb.append("Average time in system");
            sb.append(",");
            sb.append("Normalized routing load");
            sb.append("\n");
            
            for (int i = 0; i < numbOfIterations; i++) {
            	// Statics.TOTAL_TIME += 10;
            	// Statics.NODE_FAILURE_PROBABILITY++;
                Statics.NEW_PACKET_PROBABILITY--;
                SimulationResult simulationResult = singlePathNetworkManager.simulate();
                simulationResults.add(simulationResult);
                sb.append(Statics.TOTAL_TIME);
                sb.append(",");
                sb.append(Globals.TOTAL_NUMBER_OF_PACKETS);
                sb.append(",");
                sb.append(1.0 / Statics.NEW_PACKET_PROBABILITY);
                sb.append(",");
                sb.append(1.0 / Statics.NODE_FAILURE_PROBABILITY);
                sb.append(",");
                sb.append(Statics.NUMBER_OF_INTER_NODES);
                sb.append(",");
                sb.append((1.0 * Globals.DELIVERED_PACKETS) / Globals.TOTAL_NUMBER_OF_PACKETS);
                sb.append(",");
                sb.append(singlePathNetworkManager.getAvgTimeInSystem());
                sb.append(",");
                sb.append((1.0 * Globals.PACKETS_TRANSMITTED) / Globals.DELIVERED_PACKETS);
                sb.append("\n");
                Globals.PACKETS_TRANSMITTED = 0;
                Globals.CURRENT_TIME = 0;
                Globals.TOTAL_NUMBER_OF_PACKETS = 0;
                Globals.FAILED_PACKETS = 0;
                Globals.DELIVERED_PACKETS = 0;
                new DataPacket().resetDataPacketNumb();
            }
            
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e ) {
            e.printStackTrace();
        }

    }

}




//sb.append("Total Time");
//sb.append(",");
//sb.append("Total number of packets");
//sb.append(",");
//sb.append("Total number of delivered packets");
//sb.append(",");
//sb.append("Total number of failed packets");
//sb.append("\n");
//for (int i = 0; i < simulationResults.size(); i++) {
//  sb.append(simulationResults.get(i).getTotalTime());
//  sb.append(",");
//  sb.append(simulationResults.get(i).getTotalNumberOfPackets());
//  sb.append(",");
//  sb.append(simulationResults.get(i).getTotalNumberOfDeliveredPackets());
//  sb.append(",");
//  sb.append(simulationResults.get(i).getTotalNumberOfFailedPackets());
//  sb.append("\n");
//}