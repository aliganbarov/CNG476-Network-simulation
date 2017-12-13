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

    private Network network;
    
    private ArrayList<DataPacket> dataPackets = new ArrayList<>();

    public SimulationResult simulate() {
        network = new Network();
        network.setTimeIncListener(this);
        while (Globals.CURRENT_TIME < Statics.TOTAL_TIME) {
            if (isTherePacket()) {
                if (network.isChannelEstablished()) {
                	Globals.PACKETS_TRANSMITTED++;
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
        System.out.println("AVERAGE TIME IN SYSTEM: " + network.getAvgInSystemTime());
        System.out.println("DELIVERE PACKAGE RATE: " + Globals.DELIVERED_PACKETS * 1.0 / Globals.TOTAL_NUMBER_OF_PACKETS);

        dataPackets= new ArrayList<>();
        
        return new SimulationResult();

    }

    // simulates new packet arrival on every time increment
    @Override
    public void onTimeInc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	
                int rv = rand.nextInt(Statics.NEW_PACKET_PROBABILITY);
                // int rv = getPoisson(10);
                // return rv == 1 ? true : false;
                if (rv == 0) {
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
    
    public static int getPoisson(double lambda) {
	  double L = Math.exp(-lambda);
	  double p = 1.0;
	  int k = 0;

	  do {
	    k++;
	    p *= Math.random();
	  } while (p > L);

	  return k - 1;
	}
    
    public double getAvgTimeInSystem() {
    	return network.getAvgInSystemTime();
    }

}
