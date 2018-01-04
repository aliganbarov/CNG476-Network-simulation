package Threads;

import Nodes.Node;
import Nodes.SourceNode;
import Settings.Globals;

import java.util.ArrayList;

public class SimulationRunner implements Runnable {

    private SourceNode sourceNode;
    private ArrayList<Node> destinationNodes;

    public SimulationRunner(SourceNode sourceNode, ArrayList<Node> destinationNodes) {
        this.sourceNode = sourceNode;
        this.destinationNodes = destinationNodes;
    }

    @Override
    public void run() {

    }
}
