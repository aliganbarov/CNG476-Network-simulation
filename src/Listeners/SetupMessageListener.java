package Listeners;

import Models.Nodes.Node;
import Models.Packets.SetupMessage;

/**
 * Created by AliPC on 02-Dec-17.
 */
public interface SetupMessageListener {
    void handleSetupMessage(SetupMessage setupMessage);
}
