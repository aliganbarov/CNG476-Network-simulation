package Packets;

import Nodes.Node;
import Nodes.Route;

import java.util.ArrayList;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class BrokenLinkMessage {
    private Route route;
    private Node from;
    private Node to;

    public BrokenLinkMessage(Node from, Node to, Route route) {
        this.from = from;
        this.to = to;
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }
}
