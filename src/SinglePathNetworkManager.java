import Listeners.TimeIncListener;
import Models.Packets.DataPacket;
import Models.SimulationResult;
import Parameters.Globals;
import Parameters.Statics;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AliPC on 02-Dec-17.
 */
public class SinglePathNetworkManager implements TimeIncListener {

    Random rand = new Random();

    private ArrayList<DataPacket> dataPackets = new ArrayList<>();

    public SimulationResult simulate() {
        Network network = new Network();
        network.setTimeIncListener(this);
        while (Globals.CURRENT_TIME < Statics.TOTAL_TIME) {
            if (isTherePacket()) {
                if (network.isChannelEstablished()) {
                    network.startTransmission(dataPackets);
                } else {
                    network.startRouteDiscovery();
                    network.printChannel();
                }
            }
            Globals.CURRENT_TIME++;
            onTimeInc();
        }

        System.out.println("Simulation results");
        System.out.println("TOTAL PACKETS: " + Globals.TOTAL_NUMBER_OF_PACKETS);
        System.out.println("FAILED PACKETS: " + Globals.FAILED_PACKETS);
        System.out.println("DELIVERED PACKETS: " + Globals.DELIVERED_PACKETS);

        return new SimulationResult();

    }

    // simulates new packet arrival on every time increment
    @Override
    public void onTimeInc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int rv = rand.nextInt(Statics.NEW_PACKET_PROBABILITY);
                // return rv == 1 ? true : false;
                if (rv == 1) {
                    Globals.TOTAL_NUMBER_OF_PACKETS++;
                    DataPacket dataPacket = new DataPacket();
                    dataPackets.add(dataPacket);
                    System.out.println("New packet arrived at " + Globals.CURRENT_TIME + ". Packet No: " + dataPacket.getDataPacketNumb());
                }

            }
        }).run();
    }

    private boolean isTherePacket() {
        return dataPackets.size() > 0 ? true : false;
    }

}
