package entity.event;

import entity.notifications.Notification;

/**
 * Created by marcos on 11/30/14.
 */
public class EventCanceled implements EventState {
    @Override
    public Notification removeParticipant() {
        return null;
    }

    @Override
    public Notification addParticipant() {
        return null;
    }
    
    @Override
    public String getName() {
    	return "Canceled";
    }
}
