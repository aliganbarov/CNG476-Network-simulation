package Network;

import java.util.ArrayList;

import Nodes.Node;
import Nodes.SourceNode;
import Settings.Globals;
import Settings.Statics;

public class SinglePath {
	 private NetworkTopology networkTopology;

    private ArrayList<Node> sourceNodes;
    private ArrayList<Node> destinationNodes;


    public SinglePath(NetworkTopology networkTopology) {
        this.networkTopology = networkTopology;

        sourceNodes = networkTopology.getSourceNodes();
        destinationNodes = networkTopology.getDestinationNodes();
    }

    public void runSimulation() {
        System.out.println("##############################");
    	System.out.println("STARTING SINGLE PATH SIMULATION");
    	
        // reset current time
    	Globals.currentTime = 0;
        Globals.numberOfPacketsArrived = 0;
        Globals.numberOfPacketsDelivered = 0;
        Globals.currentPacketNo = 0;

        // set simulation mode
        Globals.mode = Statics.SINGLE_PATH;
        // start simulation for all source nodes
        for (int i = 0; i < Globals.totalNumberOfSourceNodes; i++) {
            for (Node sourceNode: sourceNodes) {
                ((SourceNode)sourceNode).startSimulation(destinationNodes);
            }
        }
        
        System.out.println("SINGLE PATH SIMULATION IS FINISHED");
        System.out.println("# of packets delivered: " + Globals.numberOfPacketsDelivered);
        System.out.println("# of packets arrived: " + Globals.numberOfPacketsArrived);
        System.out.println("Throughput: " + Globals.numberOfPacketsDelivered * 1.0 / Globals.numberOfPacketsArrived);
        System.out.println("##############################");

    }
}
