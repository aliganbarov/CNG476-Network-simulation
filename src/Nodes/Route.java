package Nodes;

import Packets.BrokenLinkMessage;

import java.util.ArrayList;

/**
 * Created by AliPC on 01-Jan-18.
 */
public class Route {
    private ArrayList<Node> nodes;

    public Route(ArrayList<Node> route) {
        this.nodes = route;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getDestinationNodeOfRoute() {
        return nodes.get(nodes.size() - 1);
    }

    public boolean containsBrokenLink(BrokenLinkMessage brokenLinkMessage) {
        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).getId() == brokenLinkMessage.getFrom().getId()
                    && nodes.get(i+1).getId() == brokenLinkMessage.getTo().getId()) {
                return true;
            }
        }
        return false;
    }

    public void printRoute() {
        for (Node node: nodes) {
            System.out.print(node.getId() + " ");
        }
    }
}
