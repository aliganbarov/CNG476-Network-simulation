package Models.Nodes;

import Listeners.TimeIncListener;
import Models.Packets.AckMessage;
import Models.Packets.DataPacket;
import Models.Packets.SetupMessage;
import Parameters.Globals;
import Parameters.Statics;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class SourceNode extends Node {

    private int i = 0;
    private Random rand = new Random();
    ArrayList<Node> channel = new ArrayList<>();

    private TimeIncListener timeIncListener;

    public void setTimeIncListener(TimeIncListener timeIncListener) {
        System.out.println("Time inc listener is set");
        this.timeIncListener = timeIncListener;
    }

    @Override
    public void handleSetupMessage(SetupMessage setupMessage) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // parse setup message to all neighbors
        for (i = 0; i < descendingEdges.size(); i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SetupMessage newSetupMessage = new SetupMessage(SourceNode.this.i);
                    // add source node to setup message
                    newSetupMessage.addNode(SourceNode.this);
                    int weight = descendingEdges.get(SourceNode.this.i).getWeight();
                    newSetupMessage.addPathCost(weight);
                    descendingEdges.get(SourceNode.this.i).getNode().handleSetupMessage(newSetupMessage);
                }
            }).start();
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleAckMessage(AckMessage ackMessage) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // ack is received -> channel is established
        // save path
        channel = ackMessage.getChannel();
    }

    @Override
    public void handleDataPacket(ArrayList<DataPacket> dataPackets) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // if channel is empty -> search for new channel
        if (channel.size() == 0) {
            handleSetupMessage(new SetupMessage(-1));
        }
        // check if node is available
        int rv = rand.nextInt(Statics.NODE_FAILURE_PROBABILITY);
        if (rv == 1) {
            // node is failed
            Globals.FAILED_PACKETS++;
            System.out.println("Failed node from source at " + Globals.CURRENT_TIME + ", dropping packet " + dataPackets.get(0).getDataPacketNumb());
            dataPackets.remove(0);
            channel.get(1).setHasFailed(true);
            channel.get(1).handleLinkBreakageDown(channel.get(1));

            // chance to recover node
            rv = rand.nextInt(Statics.NODE_RECOVER_PROBABILITY);
            if (rv == 1) {
                channel.get(1).setHasFailed(false);
                System.out.println("Node " + channel.get(1).getNodeNumber() + " has recovered at " + Globals.CURRENT_TIME);
            }

            // empty channel
            channel = new ArrayList<>(0);
            return;
        }
        channel.get(1).handleDataPacket(dataPackets);
    }

    @Override
    public void handleLinkBreakageUp(Node brokenNode) {
        // inc global time
        Globals.CURRENT_TIME++;
        if (timeIncListener != null) {
            timeIncListener.onTimeInc();
        }
        // empty channel
        channel = new ArrayList<>(0);
    }

    @Override
    public void handleLinkBreakageDown(Node brokenNode) {

    }

    public boolean isChannelEstablished() {
        return channel.size() == 0 ? false : true;
    }

    @Override
    public int getNodeNumber() {
        return -1;
    }




    // DEBUG STUFF
    public void printChannel() {
        System.out.print("\nChannel ");
        for (int i = 0; i < channel.size(); i++) {
            System.out.print(channel.get(i).getNodeNumber() + " ");
        }
        System.out.println();
    }

}
