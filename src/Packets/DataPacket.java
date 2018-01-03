package Packets;

import Nodes.Route;
import Settings.Globals;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class DataPacket {
    private Route route;
    private int id;

    public DataPacket(Route route) {
        this.route = route;
        id = Globals.currentPacketNo++;
    }

    public Route getRoute() {
        return route;
    }

    public int getId() {
        return id;
    }
}
