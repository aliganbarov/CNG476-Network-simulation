package Threads;

import Listeners.DataPacketListener;
import Packets.DataPacket;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class DataPacketSender implements Runnable {

    private DataPacketListener dataPacketListener;
    private DataPacket dataPacket;

    public DataPacketSender(DataPacketListener dataPacketListener, DataPacket dataPacket) {
        this.dataPacketListener = dataPacketListener;
        this.dataPacket = dataPacket;
    }

    @Override
    public void run() {
        if (dataPacketListener != null) {
            dataPacketListener.onNewDataPacket(dataPacket);
        }
    }
}
