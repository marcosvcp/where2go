package entity.event;

import entity.notifications.Notification;

/**
 * Created by marcos on 11/30/14.
 */
public class EventFinished implements EventState {
    @Override
    public Notification removeParticipant() {
        return null;
    }

    @Override
    public Notification addParticipant() {
        return null;
    }
}
