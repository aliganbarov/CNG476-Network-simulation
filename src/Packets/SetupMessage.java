package Packets;

import Nodes.Node;

import java.util.ArrayList;

/*
    This class represents Setup Message sent by source node for path discovering
    Each setup message is give ID
 */

public class SetupMessage {
    private int id;
    private ArrayList<Node> nodesOnPath;
    private Node sourceNode;
    private Node destinationNode;
    private int cost;

    public SetupMessage(int id, Node sourceNode, Node destinationNode) {
        this.id = id;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        nodesOnPath = new ArrayList<>();
        cost = 0;
    }

    public void addNodeOnPath(Node node){
        nodesOnPath.add(node);
    }

    public boolean isNodeOnPath(Node node) {
        for (int i = 0; i < nodesOnPath.size(); i++) {
            if (nodesOnPath.get(i).getId() == node.getId()) {
                return true;
            }
        }
        return false;
    }

    public void updateWeights() {
        for (int i = 0; i < nodesOnPath.size(); i++) {
            nodesOnPath.get(i).increaseActivity();
        }
    }

    public void calculateCost() {
        // for all nodes on path
        for (int i = 0; i < nodesOnPath.size(); i++) {
            // add activity of current node
            cost += nodesOnPath.get(i).getActivity();
            // add activity of all neighbors
            ArrayList<Node> neighbors = nodesOnPath.get(i).getNeighbors();
            for (int j = 0; j < neighbors.size(); j++) {
                cost += neighbors.get(j).getActivity();
            }
        }
    }

    public SetupMessage copy() {
        SetupMessage setupMessage = new SetupMessage(id, sourceNode, destinationNode);
        setupMessage.setCost(cost);
        setupMessage.setNodesOnPath((ArrayList<Node>)nodesOnPath.clone());
        return setupMessage;
    }

    public void printPath() {
        System.out.print("Nodes on path: ");
        for (Node node: nodesOnPath) {
            System.out.print(node.getId() + " ");
        }
        System.out.println();
    }

    /*
        GETTERS & SETTERS
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
    public int getCost() {
        calculateCost();
        return cost;
    }
    public int getId() {
        return id;
    }
    public int getDestinationId() {
        return destinationNode.getId();
    }
    public int getSourceId() {
        return sourceNode.getId();
    }
    public ArrayList<Node> getNodesOnPath() {
        return nodesOnPath;
    }
    public void setNodesOnPath(ArrayList<Node> nodesOnPath) {
        this.nodesOnPath = nodesOnPath;
    }
    public Node getSourceNode() {
        return sourceNode;
    }
}
