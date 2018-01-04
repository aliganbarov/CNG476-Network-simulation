package Nodes;

import Packets.ACKMessage;
import Packets.DataPacket;
import Packets.SetupMessage;
import Settings.Globals;
import Settings.Statics;

import java.util.ArrayList;

/**
 * Created by AliPC on 22-Dec-17.
 */
public class DestinationNode extends Node {

    private ArrayList<SetupMessage> setupMessages;
    private ArrayList<Node> minCostPath;
    private SetupMessage minSetupMessage;
    private ArrayList<Route> bestRoutes;


    public DestinationNode(int type, int id) {
        super(type, id);

        setupMessages = new ArrayList<>();
        bestRoutes = new ArrayList<>();
    }

    /*
        PATH DISCOVERY FUNCTIONS
     */

    @Override
    public synchronized void onNewSetupMessage(SetupMessage setupMessage) {

        System.out.println("Destination node got setup message " + setupMessage.getId() + ", at time " + Globals.currentTime);
        setupMessage.addNodeOnPath(this);
        setupMessages.add(setupMessage);
        setupMessage.printPath();
//        routeSelectTimerExpired();
    }

    /*
        if single path mode is selected -> selects only one best cost path
        if multi path mode is selected -> selects K best cost paths
        send ACK message on chosen path/paths
     */
    public void routeSelectTimerExpired() {
        System.out.println("Route select time in destination expired with " + Globals.discoveryTime);
        if (Globals.mode == Statics.SINGLE_PATH) {
            int bestCost = 99999;
            SetupMessage bestSM = null;
            for (SetupMessage setupMessage: setupMessages) {
                int cost = setupMessage.getCost();
                if (bestCost > cost) {
                    bestSM = setupMessage;
                    bestCost = cost;
                }
            }
            if (bestSM != null) {
                bestSM.updateWeights();
                setupMessages.remove(bestSM);
                bestRoutes.add(new Route(bestSM.getNodesOnPath()));
            }
        }

        if (Globals.mode == Statics.MULTI_PATH) {
            int bestCost = 99999;
            SetupMessage bestSM = null;
            // choose K best paths
            for (int i = 0; i < Globals.K; i++) {
                for (int j = 0; j < setupMessages.size(); j++) {
                    int cost = setupMessages.get(j).getCost();
                    if (bestCost > cost) {
                        bestSM = setupMessages.get(j);
                        bestCost = cost;
                    }
                }
                if (bestSM != null) {
                    System.out.print("Best setup message is chosen: ");
                    bestSM.printPath();
                    System.out.println();
                    bestSM.updateWeights();
                    setupMessages.remove(bestSM);
                    bestRoutes.add(new Route(bestSM.getNodesOnPath()));
                    bestSM = null;
                    bestCost = 99999;

                }
            }
        }
        createACKMessages();
    }

    private void createACKMessages() {
        // for each path send ack message
        if (bestRoutes.size() == 0) {
            return;
        }
        for (Route route: bestRoutes) {
            System.out.println("Creating ACK message for route ");
            route.printRoute();
            System.out.println();
            ACKMessage ackMessage = new ACKMessage(route);
            this.onNewACKMessage(ackMessage);
        }
        bestRoutes.clear();
    }

    @Override
    public void onNewDataPacket(DataPacket dataPacket) {
        Globals.numberOfPacketsDelivered++;
        dataPacket.setFinishTime(Globals.currentTime);
        Globals.totalTimeOfDeliveredPacketsInSystem = (dataPacket.getFinishTime() - dataPacket.getArrivalTime()) / 1000000.0;
        System.out.println("Destination got new data packet: " + dataPacket.getId() + ". Total packets delivered: " +
                Globals.numberOfPacketsDelivered);
    }

    /*
        GETTERS & SETTERS
     */
    public ArrayList<SetupMessage> getSetupMessages() {
        return setupMessages;
    }


    /*
        DEBUG FUNCTIONS
     */
    public void printActivePath() {
        if (minCostPath == null)
            return;
        System.out.println("Min cost path");
        for (int i = 0; i < minCostPath.size(); i++) {
            System.out.println(minCostPath.get(i).getId() + " with activity " + minCostPath.get(i).getActivity());
        }
    }

    public void resetDestinationNode() {
        setupMessages.clear();
        bestRoutes.clear();
    }
}
