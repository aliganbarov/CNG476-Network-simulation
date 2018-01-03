package Threads;

import Listeners.BrokenLinkMessageListener;
import Packets.BrokenLinkMessage;

/**
 * Created by AliPC on 02-Jan-18.
 */
public class BrokenLinkMessageSender implements Runnable {
    private BrokenLinkMessageListener brokenLinkMessageListener;
    private BrokenLinkMessage brokenLinkMessage;

    public BrokenLinkMessageSender(BrokenLinkMessageListener brokenLinkMessageListener, BrokenLinkMessage brokenLinkMessage) {
        this.brokenLinkMessageListener = brokenLinkMessageListener;
        this.brokenLinkMessage = brokenLinkMessage;
    }

    @Override
    public void run() {
        if (brokenLinkMessageListener != null) {
            brokenLinkMessageListener.onNewBrokenLinkMessage(brokenLinkMessage);
        }
    }
}
