package Listeners;

import Models.Packets.AckMessage;

/**
 * Created by AliPC on 03-Dec-17.
 */
public interface AckMessageListener {
    void handleAckMessage(AckMessage ackMessage);
}
