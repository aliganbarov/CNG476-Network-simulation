package Network;
/*
    This class holds information about network topology
    At each creation it creates random topology with initial link weights equal to 1
    Topology is represented by NxN matrix of integers where M[i][j] represents weight between i-th and j-th nodes
 */

import Nodes.*;
import Settings.Globals;
import Settings.Statics;

import java.util.ArrayList;
import java.util.Random;

public class NetworkTopology {

    private int[][] matrixTopology;
    private ArrayList<Node> sourceNodes;
    private ArrayList<Node> intermediateNodes;
    private ArrayList<Node> destinationNodes;

    public NetworkTopology(int networkTopologyType) {
        // initialize nodes
        sourceNodes = new ArrayList<>(Globals.totalNumberOfSourceNodes);
        intermediateNodes = new ArrayList<>(Globals.totalNumberOfIntermediateNodes);
        destinationNodes = new ArrayList<>(Globals.totalNumberOfDestinationNodes);

        // initialize topology matrix
        matrixTopology = new int[Globals.totalNumberOfNodes][Globals.totalNumberOfNodes];

        // random network
        // set weights for links between nodes
        if (networkTopologyType == Statics.RANDOM_TOPOLOGY) {
            Random random = new Random();
            for (int i = 0; i < Globals.totalNumberOfNodes; i++) {
                for (int j = i + 1; j < Globals.totalNumberOfNodes; j++) {
                    int probabilityOfHavingNeighbor = random.nextInt(Globals.probabilityOfHavingNeighbor);
                    if (probabilityOfHavingNeighbor == 0) {
                        matrixTopology[i][j] = 1;
                        matrixTopology[j][i] = matrixTopology[i][j];
                    }
                }
            }
        }


        // predefined network
        if (networkTopologyType == Statics.PREDEFINED_TOPOLOGY) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    matrixTopology[i][j] = 0;
                }
            }
            matrixTopology[0][1] = 1;
            matrixTopology[0][2] = 1;
            matrixTopology[1][0] = 1;
            matrixTopology[1][3] = 1;
            matrixTopology[2][0] = 1;
            matrixTopology[2][3] = 1;
            matrixTopology[2][4] = 1;
            matrixTopology[3][1] = 1;
            matrixTopology[3][2] = 1;
            matrixTopology[3][6] = 1;
            matrixTopology[4][2] = 1;
            matrixTopology[4][5] = 1;
            matrixTopology[5][4] = 1;
            matrixTopology[5][6] = 1;
            matrixTopology[6][3] = 1;
            matrixTopology[6][5] = 1;
        }


        // create all nodes with corresponding IDs
        int nodeId = 0;
        for (int i = 0; i < Globals.totalNumberOfSourceNodes; i++) {
            Node newSourceNode = new SourceNode(Globals.sourceNode, nodeId++);
            sourceNodes.add(newSourceNode);
        }
        for (int i = 0; i < Globals.totalNumberOfIntermediateNodes; i++) {
            Node newInterNode = new IntermediateNode(Globals.intermediateNode, nodeId++);
            intermediateNodes.add(newInterNode);
        }
        for (int i = 0; i < Globals.totalNumberOfDestinationNodes; i++) {
            Node newDestNode = new DestinationNode(Globals.destinationNode, nodeId++);
            destinationNodes.add(newDestNode);
        }

        // set neighbors & setupMessageListeners of each node according to topology matrix
        for (int i = 0; i < sourceNodes.size(); i++) {
            setNeighbors(sourceNodes.get(i));
        }
        for (int i = 0; i < intermediateNodes.size(); i++) {
            setNeighbors(intermediateNodes.get(i));
        }
        for (int i = 0; i < destinationNodes.size(); i++) {
            setNeighbors(destinationNodes.get(i));
        }
    }

    public void setNeighbors(Node node) {
        // search through matrix topology for neighbors
        // set all neighbors & setupMessageListeners
        int id = node.getId();
        for (int j = 0; j < Globals.totalNumberOfNodes; j++) {
            if (matrixTopology[id][j] != 0) {
                node.addNeighbor(getNodeWithId(j));
                node.addSetupMessageListener(getNodeWithId(j));
            }
        }
    }

    public Node getNodeWithId(int id) {
        // search in source nodes
        for (int i = 0; i < sourceNodes.size(); i++) {
            if (sourceNodes.get(i).getId() == id) {
                return sourceNodes.get(i);
            }
        }
        // search in intermediate nodes
        for (int i = 0; i < intermediateNodes.size(); i++) {
            if (intermediateNodes.get(i).getId() == id) {
                return intermediateNodes.get(i);
            }
        }
        // search in destination nodes
        for (int i = 0; i < destinationNodes.size(); i++) {
            if (destinationNodes.get(i).getId() == id) {
                return destinationNodes.get(i);
            }
        }
        return null;
    }

    public void printNetworkMatrix() {
        for (int i = 0; i < Globals.totalNumberOfNodes; i++) {
            for (int j = 0; j < Globals.totalNumberOfNodes; j++) {
                System.out.print(matrixTopology[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printNetworkNodes() {
        for (int i = 0; i < sourceNodes.size(); i++) {
            sourceNodes.get(i).printNeighbors();
        }
        for (int i = 0; i < intermediateNodes.size(); i++) {
            intermediateNodes.get(i).printNeighbors();
        }
        for (int i = 0; i < destinationNodes.size(); i++) {
            destinationNodes.get(i).printNeighbors();
        }
    }

    public int[][] getMatrixTopology() {
        return matrixTopology;
    }

    public ArrayList<Node> getSourceNodes() {
        return sourceNodes;
    }

    public ArrayList<Node> getIntermediateNodes() {
        return intermediateNodes;
    }

    public ArrayList<Node> getDestinationNodes() {
        return destinationNodes;
    }

    public void resetNetwork() {
        for (int i = 0; i < sourceNodes.size(); i++) {
            ((SourceNode)sourceNodes.get(i)).resetRoutes();
        }
        for (int i = 0; i < destinationNodes.size(); i++) {
            ((DestinationNode)destinationNodes.get(i)).resetDestinationNode();
        }
    }
}
