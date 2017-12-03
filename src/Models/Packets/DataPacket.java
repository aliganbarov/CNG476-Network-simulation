package Models.Packets;

/**
 * Created by AliPC on 03-Dec-17.
 */
public class DataPacket {
    public static int dataPacketNumb = 0;
    private int dataPacketNo;

    public DataPacket() {
        dataPacketNo = dataPacketNumb;
        dataPacketNumb++;
    }

    public int getDataPacketNumb() {
        return dataPacketNo;
    }

    public void resetDataPacketNumb() {
        dataPacketNumb = 0;
    }
}
