import Network.MultiPath;
import Network.NetworkTopology;
import Network.SinglePath;
import Settings.Globals;
import Settings.SimulationResults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Simulator {
	
	private SinglePath singlePath;
	private MultiPath multiPath;
	private NetworkTopology networkTopology;
	private int networkTopologyType;

	private ArrayList<SimulationResults> singlePathSimulationResults;
	private ArrayList<SimulationResults> multiPathSimulationResults;

	public Simulator(int n) {
		networkTopologyType = n;
		singlePathSimulationResults = new ArrayList<>();
		multiPathSimulationResults = new ArrayList<>();
	}

	public void simulate(int numberOfIterations) {
		for (int i = 0; i < numberOfIterations; i++) {
			System.out.println("ITERATION " + i);
			networkTopology = new NetworkTopology(networkTopologyType);
	        networkTopology.printNetworkMatrix();
	        networkTopology.printNetworkNodes();
	        
	        // simulate single path
	        singlePath = new SinglePath(networkTopology);
	        singlePath.runSimulation();

	        networkTopology.resetNetwork();
	        
	        
	        if (Globals.currentTime >= Globals.totalTime) {
	        	try {
	        		Thread.sleep(1000);
	        		double totalTime = (Globals.totalTimeOfDeliveredPacketsInSystem * 1000000.0) / Globals.numberOfPacketsDelivered;
	        		double throughput = (Globals.numberOfPacketsDelivered * 1.0) / Globals.numberOfPacketsArrived;
	        		int numberOfPacketsArrived = Globals.numberOfPacketsArrived;
	        		singlePathSimulationResults.add(new SimulationResults(totalTime, throughput, numberOfPacketsArrived));
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
	        	// simulate multi path
	            multiPath = new MultiPath(networkTopology);
	            multiPath.runSimulation();
				try {
					Thread.sleep(1000);
					double totalTime = (Globals.totalTimeOfDeliveredPacketsInSystem * 1000000.0) / Globals.numberOfPacketsDelivered;
					double throughput = (Globals.numberOfPacketsDelivered * 1.0) / Globals.numberOfPacketsArrived;
					int numberOfPacketsArrived = Globals.numberOfPacketsArrived;
					multiPathSimulationResults.add(new SimulationResults(totalTime, throughput, numberOfPacketsArrived));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	        networkTopology.resetNetwork();
		}
		writeToCSV();
	}

	public void writeToCSV() {
		try {
			PrintWriter pw = new PrintWriter(new File("singlePath_Numb_New_Packet_3.csv"));
			StringBuilder sb = new StringBuilder();
			sb.append("Number of packets");
			sb.append(",");
			sb.append("Throughput");
			sb.append(",");
			sb.append("Total Time");
			sb.append("\n");
			for (int i = 0; i < singlePathSimulationResults.size(); i++) {
				sb.append(singlePathSimulationResults.get(i).getNumberOfPacketsArrived());
				sb.append(",");
				sb.append(singlePathSimulationResults.get(i).getThroughput());
				sb.append(",");
				sb.append(singlePathSimulationResults.get(i).getTotalTime());
				sb.append("\n");
			}
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e ) {
			e.printStackTrace();
		}

		try {
			PrintWriter pw = new PrintWriter(new File("multiPath_Numb_New_Packet_3.csv"));
			StringBuilder sb = new StringBuilder();
			sb.append("Number of packets");
			sb.append(",");
			sb.append("Throughput");
			sb.append(",");
			sb.append("Total Time");
			sb.append("\n");
			for (int i = 0; i < multiPathSimulationResults.size(); i++) {
				sb.append(multiPathSimulationResults.get(i).getNumberOfPacketsArrived());
				sb.append(",");
				sb.append(multiPathSimulationResults.get(i).getThroughput());
				sb.append(",");
				sb.append(multiPathSimulationResults.get(i).getTotalTime());
				sb.append("\n");
			}
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e ) {
			e.printStackTrace();
		}
	}

}
