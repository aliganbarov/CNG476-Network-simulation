package Models.Nodes;

import Listeners.*;
import Models.Edge;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public abstract class Node implements SetupMessageListener, AckMessageListener, DataPacketListener, LinkBreakageListener {

    protected boolean hasFailed;
    protected TimeIncListener timeIncListener;

    protected ArrayList<Edge> ascendingEdges = new ArrayList<>();
    protected ArrayList<Edge> descendingEdges = new ArrayList<>();

    public void addAscendingEdge(Node node, int weight) {
        Edge edge = new Edge(node, weight);
        ascendingEdges.add(edge);
    }
    public ArrayList<Edge> getAscendingEdges() {
        return ascendingEdges;
    }
    public void addDescendingEdge(Node node, int weight) {
        Edge edge = new Edge(node, weight);
        descendingEdges.add(edge);
    }
    public ArrayList<Edge> getDescendingEdges() {
        return descendingEdges;
    }

    public abstract int getNodeNumber();

    public void setHasFailed(boolean hasFailed) {
        this.hasFailed = hasFailed;
    }

}
