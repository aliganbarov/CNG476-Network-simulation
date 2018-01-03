import Network.MultiPath;
import Network.NetworkTopology;

/**
 * Created by AliPC on 01-Jan-18.
 */
public class Main {

    public static void main(String[] args) {
        NetworkTopology networkTopology = new NetworkTopology();
        MultiPath multiPath = new MultiPath(networkTopology);
        multiPath.runSimulation();
    }
}
