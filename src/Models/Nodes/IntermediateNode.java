package Models.Nodes;

import Listeners.SetupMessageListener;
import Listeners.TimeIncListener;
import Models.Packets.AckMessage;
import Models.Packets.DataPacket;
import Models.Packets.SetupMessage;
import Parameters.Globals;
import Parameters.Statics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class IntermediateNode extends Node {
    private int nodeNumber;
    private Random rand = new Random();
    private boolean hasFailed;
    private TimeIncListener timeIncListener;

    public IntermediateNode(int nodeNumber) {
        this.nodeNumber = nodeNumber;
        hasFailed = false;
    }

    public void setTimeIncListener(TimeIncListener timeIncListener) {
        this.timeIncListener = timeIncListener;
    }

    @Override
    public void handleSetupMessage(SetupMessage setupMessage) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // if node has failed return
        System.out.println("Node " + nodeNumber + " has failed: " + hasFailed);
        if (hasFailed) {
            // chance to recover node
            // int rv = rand.nextInt(Statics.NODE_RECOVER_PROBABILITY);
            int rv = rand.nextInt(2);
            if (rv == 1) {
                hasFailed = false;
                System.out.println("Node " + nodeNumber + " has recovered.");
            } else {
                return;
            }
        }
        // add current node to message
        setupMessage.addNode(this);
        // pass message to descending neighbors
        for (int i = 0; i < descendingEdges.size(); i++) {
            setupMessage.addPathCost(descendingEdges.get(i).getWeight());
            descendingEdges.get(i).getNode().handleSetupMessage(setupMessage);
        }
    }

    @Override
    public int getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    @Override
    public void handleAckMessage(AckMessage ackMessage) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        ascendingEdges.get(0).getNode().handleAckMessage(ackMessage);
    }

    @Override
    public void handleDataPacket(ArrayList<DataPacket> dataPackets) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // check if next node is failed
        int rv = rand.nextInt(Statics.NODE_FAILURE_PROBABILITY);
        if (rv == 0 && descendingEdges.get(0).getNode().getNodeNumber() != -2) {
            // failed to send
            Globals.FAILED_PACKETS++;
            // notify source node about failed node
            System.out.println("Next node from " + nodeNumber + " has failed at " + Globals.CURRENT_TIME);
            System.out.println("Dropping data packet " + dataPackets.get(0).getDataPacketNumb());
            dataPackets.remove(0);
            descendingEdges.get(0).getNode().setHasFailed(true);
            handleLinkBreakageDown(descendingEdges.get(0).getNode());
            handleLinkBreakageUp(descendingEdges.get(0).getNode());
            // chance for node to recover
            rv = rand.nextInt(Statics.NODE_RECOVER_PROBABILITY);
            if (rv == 0) {
                descendingEdges.get(0).getNode().setHasFailed(false);
                System.out.println("Node " + descendingEdges.get(0).getNode().getNodeNumber() + " has recovered at " + Globals.CURRENT_TIME);
            }
        } else {
            descendingEdges.get(0).getNode().handleDataPacket(dataPackets);
        }
    }

    public void setHasFailed(boolean hasFailed) {
        this.hasFailed = hasFailed;
    }

    @Override
    public void handleLinkBreakageDown(Node brokenNode) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        descendingEdges.get(0).getNode().handleLinkBreakageDown(brokenNode);
    }

    @Override
    public void handleLinkBreakageUp(Node brokenNode) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        ascendingEdges.get(0).getNode().handleLinkBreakageUp(brokenNode);
    }
}
