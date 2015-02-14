
package entity.event;

import entity.notifications.Notification;

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
    public final Notification removeParticipant() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see entity.event.EventState#addParticipant()
     */
    @Override
    public final Notification addParticipant() {
        return null;
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
