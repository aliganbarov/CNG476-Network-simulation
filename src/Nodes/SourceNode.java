package Nodes;

import Packets.ACKMessage;
import Packets.BrokenLinkMessage;
import Packets.DataPacket;
import Packets.SetupMessage;
import Settings.Globals;
import Settings.Rand;
import Threads.SetupMessageSender;
import Threads.TimeSimulator;

import java.util.ArrayList;

public class SourceNode extends Node {

    private ArrayList<Node> activePath;
    private ArrayList<Route> routes;
    private int numbOfDataPacketsToSend;
    private boolean routeDiscoveryInProgress = false;
    private ArrayList<Node> destinationNodes;

    public SourceNode(int type, int id) {
        super(type, id);
        routes = new ArrayList<>();
        numbOfDataPacketsToSend = 0;
    }

    public void startSimulation(ArrayList<Node> destinationNodes) {
        // simulate time
        Thread timer = new Thread(new TimeSimulator(this, destinationNodes));
        timer.start();

        // check if is there is new packet to send
        this.destinationNodes = destinationNodes;


        while(true) {
        	if (Globals.currentTime >= Globals.totalTime) {
        		return;
        	}
            if (routes.size() == 0) {
                Globals.discoveryTime = 0;
                createSetupMessages(destinationNodes.get(0));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (Rand.simulate(Globals.probabilityOfHavingNewPacket)) {
                startTransmission(destinationNodes.get(0));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // forward setup message to all neighbors on new thread
    public void createSetupMessages(Node node) {
        // System.out.println("New setup message is sent from source node");
    	Globals.discoveryTime = 0;
        SetupMessage setupMessage = new SetupMessage(0, this, node);
        this.onNewSetupMessage(setupMessage);
    }

    // send message through route
    public synchronized void startTransmission(Node destinationNode) {
        System.out.println("Sending new data packet from source node " + id + " to " + destinationNode.getId() +
         ", # of routes: " + routes.size());
        for (Route route: routes) {
            if (route.getDestinationNodeOfRoute().getId() == destinationNode.getId()) {
                // create Data Packet to be sent
                Globals.numberOfPacketsArrived++;
                DataPacket dataPacket = new DataPacket(route);
                dataPacket.setArrivalTime(Globals.currentTime);
                System.out.println("New packet id is " + dataPacket.getId());
                this.onNewDataPacket(dataPacket);
            }
        }
    }

    @Override
    public synchronized void onNewACKMessage(ACKMessage ackMessage) {
        // System.out.println("Source node got ACK message");
        // save route
        if (routes.size() >= Globals.K) {
            routes.remove(0);
        }
        routes.add(ackMessage.getRoute());
    }

    @Override
    public synchronized void onNewBrokenLinkMessage(BrokenLinkMessage brokenLinkMessage) {
        System.out.println("Source node got notified about broken link");
        // remove all paths containing broken link
        ArrayList<Route> toRemove = new ArrayList<>();
        for (Route route: routes) {
            if (route.containsBrokenLink(brokenLinkMessage)) {
                //System.out.print("Removed route containing broken link: ");
                //route.printRoute();
                //System.out.println();
                toRemove.add(route);
            }
        }
        routes.removeAll(toRemove);
        System.out.println("Routes left: " + routes.size());
    }

    public void routeSelectTimerExpired() {
        System.out.println("Route select time expired with " + Globals.discoveryTime);
        routeDiscoveryInProgress = false;
    }

    public void resetRoutes() {
        routes.clear();
    }
}
