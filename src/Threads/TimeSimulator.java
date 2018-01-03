package Threads;

import Nodes.DestinationNode;
import Nodes.Node;
import Nodes.SourceNode;
import Settings.Globals;

import java.util.ArrayList;

public class TimeSimulator implements Runnable {

    private Node sourceNode;
    private ArrayList<Node> destinationNodes;

    public TimeSimulator(Node sourceNode, ArrayList<Node> destinationNodes) {
        this.sourceNode = sourceNode;
        this.destinationNodes = destinationNodes;
    }

    @Override
    public void run() {
        while(true) {
            final long INTERVAL = Globals.timerInterval;
            long start = System.nanoTime();
            long end = 0;
            do{
                end = System.nanoTime();
            }while(start + INTERVAL >= end);

            Globals.currentTime++;
            Globals.discoveryTime++;

            if (Globals.discoveryTime == Globals.routeSelectTimerLimit) {
                ((SourceNode)sourceNode).routeSelectTimerExpired();
                for (Node destinationNode: destinationNodes) {
                    ((DestinationNode)destinationNode).routeSelectTimerExpired();
                }
            }
        }
    }
}
