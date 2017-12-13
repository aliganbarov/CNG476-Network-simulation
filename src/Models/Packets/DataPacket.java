package Models.Packets;

import Parameters.Globals;

/**
 * Created by AliPC on 03-Dec-17.
 */
public class DataPacket {
    public static int dataPacketNumb = 0;
    private int dataPacketNo;
    private int arrivalTime;
    private int finishedTime;

    public DataPacket() {
        dataPacketNo = dataPacketNumb;
        dataPacketNumb++;
        arrivalTime = Globals.CURRENT_TIME;
    }

    public int getDataPacketNumb() {
        return dataPacketNo;
    }

    public void resetDataPacketNumb() {
        dataPacketNumb = 0;
    }

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(int finishedTime) {
		this.finishedTime = finishedTime;
	}
    
    public int totalTimeInSystem() {
    	return finishedTime - arrivalTime;
    }
}
