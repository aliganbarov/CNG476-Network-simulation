package Listeners;

import Packets.DataPacket;

/**
 * Created by AliPC on 02-Jan-18.
 */
public interface DataPacketListener {
    void onNewDataPacket(DataPacket dataPacket);
}
