package Models;

import Models.Nodes.Node;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class Edge {
    private Node node;
    private int weight;

    public Edge(Node node, int weight) {
        this.node = node;
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
