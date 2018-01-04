package Network;

import Nodes.Node;
import Nodes.SourceNode;
import Settings.Globals;
import Settings.Statics;

import java.util.ArrayList;

public class MultiPath {

    private NetworkTopology networkTopology;

    private ArrayList<Node> sourceNodes;
    private ArrayList<Node> destinationNodes;


    public MultiPath(NetworkTopology networkTopology) {
        this.networkTopology = networkTopology;

        sourceNodes = networkTopology.getSourceNodes();
        destinationNodes = networkTopology.getDestinationNodes();
    }

    public void runSimulation() {
        System.out.println("##############################");
        System.out.println("STARTING MULTI PATH SIMULATION");
    	
        // reset globals
        Globals.currentTime = 0;
        Globals.numberOfPacketsArrived = 0;
        Globals.numberOfPacketsDelivered = 0;
        Globals.currentPacketNo = 0;

        System.out.println("Probability of link failure: " + Globals.probabilityOfLinkFailureSingle);
        
        // set simulation mode
        Globals.mode = Statics.MULTI_PATH;
        // start simulation for all source nodes
        for (int i = 0; i < Globals.totalNumberOfSourceNodes; i++) {
            for (Node sourceNode: sourceNodes) {
                ((SourceNode)sourceNode).startSimulation(destinationNodes);
            }
        }
        
        System.out.println("MULTI PATH SIMULATION FINISHED");
        System.out.println("# of packets delivered: " + Globals.numberOfPacketsDelivered);
        System.out.println("# of packets arrived: " + Globals.numberOfPacketsArrived);
        System.out.println("Throughput: " + (Globals.numberOfPacketsDelivered * 1.0) / Globals.numberOfPacketsArrived);
        System.out.println("##############################");

    }

}
