
package entity.event;

import entity.notifications.Notification;
import entity.user.User;

/**
 * Created by marcos on 11/30/14.
 */
public class EventCanceled implements EventState {

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#removeParticipant()
     */
    @Override
    public final Notification removeParticipant(Event event, User guest, User host) {
        return getCanceledMessage(event, guest);
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#addParticipant()
     */
    @Override
    public final Notification addParticipant(Event event, User guest, User host) {
        return getCanceledMessage(event, guest);
    }

    /**
     * Returns the message of canceled event
     */
    private Notification getCanceledMessage(Event event, User guest) {
        return new Notification(guest, event, "O evento " + event.getName() + " foi cancelado.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#getName()
     */
    @Override
    public final String getName() {
        return "Canceled";
    }
}
