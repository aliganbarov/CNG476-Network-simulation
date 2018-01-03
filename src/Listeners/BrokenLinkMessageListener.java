package Listeners;

import Packets.BrokenLinkMessage;

/**
 * Created by AliPC on 02-Jan-18.
 */
public interface BrokenLinkMessageListener {
    void onNewBrokenLinkMessage(BrokenLinkMessage brokenLinkMessage);
}
