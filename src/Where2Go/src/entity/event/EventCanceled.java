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
    public final Notification removeParticipant(final Event event,
            final User guest, final User host) {
        return getCanceledMessage(event, guest);
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.EventState#addParticipant()
     */
    @Override
    public final Notification addParticipant(final Event event,
            final User guest, final User host) {
        return getCanceledMessage(event, guest);
    }

    /**
     * Returns the message of canceled event
     */
    private Notification getCanceledMessage(final Event event, final User guest) {
        return new Notification(guest, event, "O evento " + event.getName()
                + " foi cancelado.");
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
