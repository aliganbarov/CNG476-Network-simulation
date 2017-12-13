package Models.Nodes;

import Listeners.TimeIncListener;
import Models.Packets.AckMessage;
import Models.Packets.DataPacket;
import Models.Packets.SetupMessage;
import Parameters.Globals;
import Parameters.Statics;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class DestinationNode extends Node {

    private ArrayList<SetupMessage> setupMessages = new ArrayList<>();
    private ArrayList<DataPacket> donePackets = new ArrayList<>();

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
    	if (dataPackets.get(0).getArrivalTime() > Statics.TOTAL_TIME) {
    		return;
    	}
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // send ack that received data packet
        Globals.DELIVERED_PACKETS++;
        System.out.println("Packet delivered at " + Globals.CURRENT_TIME + ". Packet No: " + dataPackets.get(0).getDataPacketNumb() + 
        		". Packet arrived at: " + dataPackets.get(0).getArrivalTime());
        dataPackets.get(0).setFinishedTime(Globals.CURRENT_TIME);
        donePackets.add(dataPackets.get(0));
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
    
    public double getAvgInSystemTime() {
    	double totalTime = 0;
    	for (int i = 0; i < donePackets.size(); i++) {
    		totalTime = totalTime +  donePackets.get(i).getFinishedTime() - donePackets.get(i).getArrivalTime();
    		// System.out.println("Arrival: " + donePackets.get(i).getArrivalTime() + ", Finished: " + donePackets.get(i).getFinishedTime());
    	}
    	return totalTime / (donePackets.size() * 1.0);
    }
}
