package Listeners;

import Packets.ACKMessage;

/**
 * Created by AliPC on 02-Jan-18.
 */
public interface ACKMessageListener {
    void onNewACKMessage(ACKMessage ackMessage);
}
