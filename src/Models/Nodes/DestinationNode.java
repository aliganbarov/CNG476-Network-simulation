package Models.Nodes;

import Listeners.TimeIncListener;
import Models.Packets.AckMessage;
import Models.Packets.DataPacket;
import Models.Packets.SetupMessage;
import Parameters.Globals;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class DestinationNode extends Node {

    private ArrayList<SetupMessage> setupMessages = new ArrayList<>();

    @Override
    public void handleSetupMessage(SetupMessage setupMessage) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        System.out.print("Destination got new setup message: ");
        setupMessage.print();
        setupMessage.addNode(this);
        setupMessages.add(setupMessage);
    }

    public SetupMessage getPathWithLowestCost() {
        int cost = 999;
        SetupMessage setupMessage = new SetupMessage(-1);
        for (int i = 0; i < setupMessages.size(); i++) {
            if (cost > setupMessages.get(i).getPathCost()) {
                cost = setupMessages.get(i).getPathCost();
                setupMessage = setupMessages.get(i);
            }
        }
        // leave only lower cost setup messages
        setupMessages = new ArrayList<>();
        setupMessages.add(setupMessage);
        return setupMessage;
    }

    public void printSetupMessages() {
        for (int i = 0; i < setupMessages.size(); i++) {
            setupMessages.get(i).print();
        }
        System.out.println();
    }

    @Override
    public int getNodeNumber() {
        return -2;
    }

    @Override
    public void handleAckMessage(AckMessage ackMessage1) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        SetupMessage setupMessage = getPathWithLowestCost();
        AckMessage ackMessage = new AckMessage(setupMessage);
        ArrayList<Node> ascendingNodes = setupMessage.getNodes();
        Node ascendingNode = ascendingNodes.get(ascendingNodes.size() - 2);
        ascendingNode.handleAckMessage(ackMessage);
    }

    @Override
    public void handleDataPacket(ArrayList<DataPacket> dataPackets) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // send ack that received data packet
        Globals.DELIVERED_PACKETS++;
        System.out.println("Packet delivered at " + Globals.CURRENT_TIME + ". Packet No: " + dataPackets.get(0).getDataPacketNumb());
        dataPackets.remove(0);
    }

    @Override
    public void handleLinkBreakageDown(Node brokenNode) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // clear path
        System.out.println("Destination got info about broken node " + brokenNode.getNodeNumber() + " at "  + Globals.CURRENT_TIME);
        setupMessages = new ArrayList<>(0);
    }

    @Override
    public void handleLinkBreakageUp(Node brokenNode) {

    }

    public void setTimeIncListener(TimeIncListener timeIncListener) {
        this.timeIncListener = timeIncListener;
    }

    public boolean hasSetupMessage() {
        return setupMessages.size() == 0 ? false : true;
    }
}
