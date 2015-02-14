
package entity.event;

import entity.notifications.Notification;

/**
 * Estado de uma {@link Invitation} Created by marcos on 11/30/14.
 */
public interface InvitationState {

    /**
     * Confirm.
     * 
     * @param invitation the invitation
     * @return the notification
     */
    Notification confirm(Invitation invitation);

    /**
     * Decline.
     * 
     * @param invitation the invitation
     * @return the notification
     */
    Notification decline(Invitation invitation);

    /**
     * Gets the name.
     * 
     * @return the name
     */
    String getName();
}
