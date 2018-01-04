package Packets;

import Nodes.Route;
import Settings.Globals;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class DataPacket {
    private Route route;
    private int id;

    private int arrivalTime;
    private int finishTime;

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

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
