package entity.notifications;

import entity.event.Event;
import entity.user.User;

// TODO: Auto-generated Javadoc
/**
 * Responsible for notification of events Created by marcos on 11/30/14.
 */
public class Notification {

    /** The user target. */
    private User userTarget;

    /** The event target. */
    private Event eventTarget;

    /** The message. */
    private String message;

    /**
     * Instantiates a new notification.
     *
     * @param userTarget
     *            the user target
     * @param eventTarget
     *            the event target
     * @param message
     *            the message
     */
    public Notification(User userTarget, Event eventTarget, String message) {
        this.userTarget = userTarget;
        this.eventTarget = eventTarget;
        this.message = message;
    }

    /**
     * Gets the user target.
     *
     * @return the user target
     */
    public User getUserTarget() {
        return userTarget;
    }

    /**
     * Sets the user target.
     *
     * @param userTarget
     *            the new user target
     */
    public void setUserTarget(User userTarget) {
        this.userTarget = userTarget;
    }

    /**
     * Gets the event target.
     *
     * @return the event target
     */
    public Event getEventTarget() {
        return eventTarget;
    }

    /**
     * Sets the event target.
     *
     * @param eventTarget
     *            the new event target
     */
    public void setEventTarget(Event eventTarget) {
        this.eventTarget = eventTarget;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
