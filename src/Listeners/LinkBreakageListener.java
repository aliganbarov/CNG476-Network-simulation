package Listeners;

import Models.Nodes.Node;

/**
 * Created by AliPC on 03-Dec-17.
 */
public interface LinkBreakageListener {
    void handleLinkBreakageUp(Node brokenNode);
    void handleLinkBreakageDown(Node brokenNode);
}
