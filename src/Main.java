import Settings.Globals;

import java.util.Scanner;

/**
 * Created by AliPC on 01-Jan-18.
 */
public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("1) Predefined");
        System.out.println("2) Random");
        System.out.println("Choose network topology (1 or 2): ");
        int n = reader.nextInt();

        System.out.print("Enter probability of having new packet (1/n). n: ");
        int probabilityOfHavingNewPacket = reader.nextInt();
        Globals.probabilityOfHavingNewPacket = probabilityOfHavingNewPacket;

        System.out.print("Enter total simulation time: ");
        int totalTime = reader.nextInt();
        Globals.totalTime = totalTime * 1000000;

        System.out.print("Enter probability of having broken link (1/n) for single path. n: ");
        int brokenLinkRateSingle = reader.nextInt();
        Globals.probabilityOfLinkFailureSingle = brokenLinkRateSingle;

        System.out.print("Enter probability of having broken link (1/n) for multi path. n: ");
        int brokenLinkRateMulti = reader.nextInt();
        Globals.probabilityOfLinkFailureMulti = brokenLinkRateMulti;

        System.out.println("Choose the number of best paths used for multi-path load K: ");
        int K = reader.nextInt();
        Globals.K = K;

        System.out.println("Enter number of simulation iterations: ");
        int iterations = reader.nextInt();


        Simulator simulator = new Simulator(n);
        simulator.simulate(iterations);
    }
}
