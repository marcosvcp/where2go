
package entity.event;

import entity.notifications.Notification;

/**
 * Created by marcos on 11/30/14.
 */
public interface EventState {

    /**
     * Removes the participant.
     * 
     * @return the notification
     */
    Notification removeParticipant();

    /**
     * Adds the participant.
     * 
     * @return the notification
     */
    Notification addParticipant();

    /**
     * Gets the name.
     * 
     * @return the name
     */
    String getName();
}
