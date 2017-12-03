package Models.Packets;

import Models.Nodes.Node;

import java.util.ArrayList;

/**
 * Created by AliPC on 03-Dec-17.
 */
public class AckMessage {

    SetupMessage setupMessage;

    public AckMessage(SetupMessage setupMessage) {
        this.setupMessage = setupMessage;
    }

    public ArrayList<Node> getChannel() {
        return setupMessage.getNodes();
    }

}
