package entity.event;

import entity.notifications.Notification;

/**
 * Created by marcos on 11/30/14.
 */
public interface EventState {

    Notification removeParticipant();

    Notification addParticipant();
    
    String getName();
}
