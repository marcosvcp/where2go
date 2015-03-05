package entity.event;

import entity.notifications.Notification;
import entity.user.User;

/**
 * Created by marcos on 11/30/14.
 */
public class EventFinished implements EventState {

    /*
     * (non-Javadoc)
     *
     * @see entity.event.EventState#removeParticipant()
     */
    @Override
    public final Notification removeParticipant(final Event event,
            final User guest, final User host) {
        return new Notification(guest, event, "Você foi removido do evento "
                + event.getName());
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.EventState#addParticipant()
     */
    @Override
    public final Notification addParticipant(final Event event,
            final User guest, final User host) {
        return new Notification(guest, event, "O evento " + event.getName()
                + " já foi concluído");
    }

    /*
     * (non-Javadoc)
     *
     * @see entity.event.EventState#getName()
     */
    @Override
    public final String getName() {
        return "Finished";
    }
}
