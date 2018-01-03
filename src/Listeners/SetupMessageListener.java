package Listeners;

import Packets.SetupMessage;

public interface SetupMessageListener {
    void onNewSetupMessage(SetupMessage setupMessage);
}
