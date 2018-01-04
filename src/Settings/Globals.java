package Settings;

public class Globals {

    public static final int totalNumberOfNodes = 7;
    public static final int totalNumberOfSourceNodes = 1;
    public static final int totalNumberOfDestinationNodes = 1;
    public static final int totalNumberOfIntermediateNodes = totalNumberOfNodes - totalNumberOfSourceNodes - totalNumberOfDestinationNodes;

    public static final int sourceNode = 0;
    public static final int intermediateNode = 1;
    public static final int destinationNode = 2;

    public static final int probabilityOfHavingNeighbor = 3;

    public static int probabilityOfLinkFailureSingle = 20;
    public static int probabilityOfLinkFailureMulti = 10;

    public static int currentTime = 0;
    public static int totalTime = 1000000;

    public static int timerInterval = 1000;     // nano
    public static int routeSelectTimerLimit = 10000;
    public static int discoveryTime = 0;

    public static int mode;         // 0 - single path, 1 - multi path

    public static int K = 2;        // K best path

    public static int currentPacketNo = 0;
    
    public static int numberOfPacketsArrived = 0;
    public static int numberOfPacketsDelivered = 0;
    
    public static int totalNumberOfPackets = 10;
    
    public static int probabilityOfHavingNewPacket = 10;

    public static double totalTimeOfDeliveredPacketsInSystem = 0;

}
