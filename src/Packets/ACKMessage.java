package Packets;

import Nodes.Node;
import Nodes.Route;

import java.util.ArrayList;

/**
 * Created by AliPC on 23-Dec-17.
 */
public class ACKMessage {
    private Route route;

    public ACKMessage(Route route) {
        this.route = route;
    }

    /*
        GETTERS & SETTERS
     */

    public Route getRoute() {
        return route;
    }
}
