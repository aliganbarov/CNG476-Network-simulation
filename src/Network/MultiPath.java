package Network;

import Nodes.DestinationNode;
import Nodes.IntermediateNode;
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

        networkTopology.printNetworkMatrix();
        networkTopology.printNetworkNodes();
    }

    public void runSimulation() {
        // reset current time
        Globals.currentTime = 0;

        // set simulation mode
        Globals.mode = Statics.MULTI_PATH;
        // start simulation for all source nodes
        for (int i = 0; i < 1; i++) {
            for (Node sourceNode: sourceNodes) {
                ((SourceNode)sourceNode).startSimulation(destinationNodes);
            }
        }

    }

}
