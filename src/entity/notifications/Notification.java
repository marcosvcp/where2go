package entity.notifications;

import entity.event.Event;
import entity.user.User;

/**
 * Representa uma notificação
 * Created by marcos on 11/30/14.
 */
public class Notification {
    private User userTarget;
    private Event eventTarget;
    private String message;

    public Notification(User userTarget, Event eventTarget, String message) {
        this.userTarget = userTarget;
        this.eventTarget = eventTarget;
        this.message = message;
    }

    public User getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(User userTarget) {
        this.userTarget = userTarget;
    }

    public Event getEventTarget() {
        return eventTarget;
    }

    public void setEventTarget(Event eventTarget) {
        this.eventTarget = eventTarget;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
