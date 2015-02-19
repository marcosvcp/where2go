
package entity.event;

import entity.notifications.Notification;
import entity.user.User;

/**
 * Created by marcos on 11/30/14.
 */
public interface EventState {

    /**
     * Removes the participant.
     * 
     * @return the notification
     */
    Notification removeParticipant(Event event, User guest, User host);

    /**
     * Adds the participant.
     * 
     * @return the notification
     */
    Notification addParticipant(Event event, User guest, User host);

    /**
     * Gets the name.
     * 
     * @return the name
     */
    String getName();
}
