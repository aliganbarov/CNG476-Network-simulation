package Threads;

import Listeners.ACKMessageListener;
import Packets.ACKMessage;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class ACKMessageSender implements Runnable {
    private ACKMessageListener ackMessageListener;
    private ACKMessage ackMessage;

    public ACKMessageSender(ACKMessageListener ackMessageListener, ACKMessage ackMessage) {
        this.ackMessageListener = ackMessageListener;
        this.ackMessage = ackMessage;
    }

    @Override
    public void run() {
        if (ackMessageListener != null) {
            ackMessageListener.onNewACKMessage(ackMessage);
        }
    }
}
