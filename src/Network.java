import Listeners.TimeIncListener;
import Models.Nodes.DestinationNode;
import Models.Nodes.IntermediateNode;
import Models.Nodes.SourceNode;
import Models.Packets.AckMessage;
import Models.Packets.DataPacket;
import Models.Packets.SetupMessage;
import Parameters.Globals;
import Parameters.Statics;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class Network {
    private SourceNode sourceNode;
    private ArrayList<IntermediateNode> intermediateNodes = new ArrayList<>(Statics.NUMBER_OF_INTER_NODES);
    private DestinationNode destinationNode;

    private TimeIncListener timeIncListener;

    public Network() {
        // create nodes
        sourceNode = new SourceNode();
        destinationNode = new DestinationNode();
        for (int i = 0; i < Statics.NUMBER_OF_INTER_NODES; i++) {
            intermediateNodes.add(new IntermediateNode(i));
        }

        // set ascendants and descendants
        sourceNode.addDescendingEdge(intermediateNodes.get(0), 1);
        sourceNode.addDescendingEdge(intermediateNodes.get(1), 1);
        intermediateNodes.get(0).addDescendingEdge(intermediateNodes.get(2), 2);
        intermediateNodes.get(0).addAscendingEdge(sourceNode, 1);
        intermediateNodes.get(1).addDescendingEdge(intermediateNodes.get(3), 3);
        intermediateNodes.get(1).addAscendingEdge(sourceNode, 1);
        intermediateNodes.get(2).addDescendingEdge(destinationNode, 3);
        intermediateNodes.get(2).addAscendingEdge(intermediateNodes.get(0), 2);
        intermediateNodes.get(3).addDescendingEdge(destinationNode, 3);
        intermediateNodes.get(3).addAscendingEdge(intermediateNodes.get(1), 3);
        destinationNode.addAscendingEdge(intermediateNodes.get(2), 3);
        destinationNode.addAscendingEdge(intermediateNodes.get(3), 3);
    }



    public void startRouteDiscovery() {
        System.out.println("starting route discovery...");
        sourceNode.handleSetupMessage(new SetupMessage(-1));
        if (destinationNode.hasSetupMessage()) {
            destinationNode.handleAckMessage(new AckMessage(new SetupMessage(-1)));
        }
    }

    public void startTransmission(ArrayList<DataPacket> dataPackets) {
        sourceNode.handleDataPacket(dataPackets);
    }

    public void setTimeIncListener(TimeIncListener timeIncListener) {
        this.timeIncListener = timeIncListener;
        // pass time inc listener to all nodes
        sourceNode.setTimeIncListener(timeIncListener);
        destinationNode.setTimeIncListener(timeIncListener);
        for (int i = 0; i < intermediateNodes.size(); i++) {
            intermediateNodes.get(i).setTimeIncListener(timeIncListener);
        }
    }

    public boolean isChannelEstablished() {
        return sourceNode.isChannelEstablished();
    }
    // DEBUG STUFF
    public void printMessages() {
        destinationNode.printSetupMessages();
    }

    public void printLowestCostPath(){
        SetupMessage setupMessage = destinationNode.getPathWithLowestCost();
        System.out.print("Lowest cost path: ");
        setupMessage.print();
    }

    public void printChannel() {
        sourceNode.printChannel();
    }

}
