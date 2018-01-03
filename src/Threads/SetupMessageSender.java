package Threads;

import Listeners.SetupMessageListener;
import Packets.SetupMessage;

public class SetupMessageSender implements Runnable {

    private SetupMessageListener setupMessageListener;
    private SetupMessage setupMessage;

    public SetupMessageSender(SetupMessageListener setupMessageListener, SetupMessage setupMessage) {
        this.setupMessageListener = setupMessageListener;
        this.setupMessage = setupMessage;
    }

    @Override
    public void run() {
        if (setupMessageListener != null) {
            setupMessageListener.onNewSetupMessage(setupMessage);
        }
    }

    public SetupMessageListener getSetupMessageListener() {
        return setupMessageListener;
    }

    public void setSetupMessageListener(SetupMessageListener setupMessageListener) {
        this.setupMessageListener = setupMessageListener;
    }
}
