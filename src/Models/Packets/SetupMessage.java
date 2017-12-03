package Models.Packets;

import Models.Nodes.Node;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class SetupMessage {

    private int setupMessageNumber;
    private int pathCost;

    private ArrayList<Node> nodes = new ArrayList<>();

    public SetupMessage(int setupMessageNumber) {
        pathCost = 0;
        this.setupMessageNumber = setupMessageNumber;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getSetupMessageNumber() {
        return setupMessageNumber;
    }

    public void print() {
        System.out.print("Print setup message: " + setupMessageNumber + " Total Cost: " + pathCost + " Nodes: ");
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print(nodes.get(i).getNodeNumber() + " ");
        }
        System.out.println();
    }

    public void setSetupMessageNumber(int setupMessageNumber) {
        this.setupMessageNumber = setupMessageNumber;
    }

    public void addPathCost(int pathCost) {
        this.pathCost += pathCost;
    }

    public int getPathCost() {
        return pathCost;
    }
}
