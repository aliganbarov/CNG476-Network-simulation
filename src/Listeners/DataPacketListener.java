package Listeners;

import Models.Packets.DataPacket;

import java.util.ArrayList;

/**
 * Created by AliPC on 03-Dec-17.
 */
public interface DataPacketListener {
    public void handleDataPacket(ArrayList<DataPacket> dataPackets);
}
