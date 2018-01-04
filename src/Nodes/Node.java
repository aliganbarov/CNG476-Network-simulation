package Nodes;
/*
    This class represents Node of the network
    Each type of node is represented with int
    Source Node - 0, Intermediate Node - 1, Destination Node - 2
    Each node has number as ID
 */

import Listeners.ACKMessageListener;
import Listeners.BrokenLinkMessageListener;
import Listeners.DataPacketListener;
import Listeners.SetupMessageListener;
import Packets.ACKMessage;
import Packets.BrokenLinkMessage;
import Packets.DataPacket;
import Packets.SetupMessage;
import Settings.Globals;
import Settings.Rand;
import Settings.Statics;
import Threads.ACKMessageSender;
import Threads.BrokenLinkMessageSender;
import Threads.DataPacketSender;
import Threads.SetupMessageSender;

import java.util.ArrayList;

public abstract class Node implements SetupMessageListener, ACKMessageListener, DataPacketListener, BrokenLinkMessageListener {

    protected int type;
    protected int id;
    protected ArrayList<Node> neighbors;

    protected boolean isActive;
    protected int activity;

    protected ArrayList<SetupMessageListener> setupMessageListeners;

    public Node(int type, int id) {
        this.type = type;
        this.id = id;
        neighbors = new ArrayList<>();
        activity = 1;
        setupMessageListeners = new ArrayList<>();
    }

    public void addNeighbor(Node node) {
        this.neighbors.add(node);
    }

    public int getId() {
        return id;
    }

    public void printNeighbors() {
        System.out.print("Neighbors of " + id + ": ");
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.print(neighbors.get(i).getId() + " ");
        }
        System.out.println();
    }

    public int getActivity() {
        return activity;
    }

    public void increaseActivity() {
        activity++;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public synchronized void onNewSetupMessage(SetupMessage setupMessage) {
        // add current node
//        System.out.println("Adding current node " + id + " to setup message");
        SetupMessage newSetupMessage = setupMessage.copy();
        newSetupMessage.addNodeOnPath(this);

        // pass message to neighbors
        for (int i = 0; i < setupMessageListeners.size(); i++) {
            // check whether next node is already on path
            if (!setupMessage.isNodeOnPath((Node)setupMessageListeners.get(i))) {
                Thread smService = new Thread(new SetupMessageSender(this.setupMessageListeners.get(i), newSetupMessage));
                smService.start();
            }
        }
    }

    @Override
    public synchronized void onNewACKMessage(ACKMessage ackMessage) {
        // find current node in route and pass message to previous one
        ArrayList<Node> nodes = ackMessage.getRoute().getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId() == this.id) {
                // check if previous node is available
//                if (!Rand.simulate(Globals.probabilityOfLinkFailureSingle)) {
//                    Thread ackService = new Thread(new ACKMessageSender(nodes.get(i-1), ackMessage));
//                    ackService.start();
//                } else {
//                    System.out.println("Failed to send ACK at link from " + nodes.get(i).getId() + " to " + nodes.get(i-1).getId());
//                }

                // DEBUG NO FAIL
                Thread ackService = new Thread(new ACKMessageSender(nodes.get(i-1), ackMessage));
                ackService.start();
            }
        }

    }

    @Override
    public void onNewDataPacket(DataPacket dataPacket) {
        // find current node in route and pass message to next one
        ArrayList<Node> nodes = dataPacket.getRoute().getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId() == this.id) {
                // check if next node link is not broken
                int probabilityOfLinkFailure = 10;
                if (Globals.mode == Statics.SINGLE_PATH) {
                    probabilityOfLinkFailure = Globals.probabilityOfLinkFailureSingle;
                }
                if (Globals.mode == Statics.MULTI_PATH) {
                    probabilityOfLinkFailure = Globals.probabilityOfLinkFailureMulti;
                }
                if (!Rand.simulate(probabilityOfLinkFailure)) {
                    // System.out.println("Node " + id + " got data packet, sending to " + nodes.get(i+1).getId());
                    Thread dpService = new Thread(new DataPacketSender(nodes.get(i+1), dataPacket));
                    dpService.start();
                } else {
                    System.out.println("Failed to send data packet at link from " + id + " to " + nodes.get(i+1).getId()
                    + ", packet " + dataPacket.getId() + " is dropped");
                    try {
                        Thread blService = new Thread(new BrokenLinkMessageSender(nodes.get(i),
                                new BrokenLinkMessage(this, nodes.get(i+1), dataPacket.getRoute())));
                        blService.start();
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                // DEBUG MODE
//                System.out.println("Node " + id + " got data packet, sending to " + nodes.get(i+1).getId());
//                Thread dpService = new Thread(new DataPacketSender(nodes.get(i+1), dataPacket));
//                dpService.start();
            }
        }
    }

    @Override
    public void onNewBrokenLinkMessage(BrokenLinkMessage brokenLinkMessage) {
        // find current node in route and pass message to previous node
        ArrayList<Node> nodes = brokenLinkMessage.getRoute().getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId() == this.id) {
                Thread blService = new Thread(new BrokenLinkMessageSender(nodes.get(i-1),
                        brokenLinkMessage));
                blService.start();
            }
        }

    }

    public void addSetupMessageListener(SetupMessageListener setupMessageListener) {
        this.setupMessageListeners.add(setupMessageListener);
    }
}


















