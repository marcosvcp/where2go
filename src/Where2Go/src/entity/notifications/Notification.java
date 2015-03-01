package entity.notifications;

import entity.event.Event;
import entity.user.User;

/**
 * Responsible for notification of events Created by marcos on 11/30/14.
 */
public class Notification extends android.app.Notification {

    /** The user target. */
    private User userTarget;

    /** The event target. */
    private Event eventTarget;

    /** The message. */
    private String message;

    /**
     * Instantiates a new notification.
     *
     * @param user
     *            the user target
     * @param event
     *            the event target
     * @param messageTarget
     *            the message
     */
    public Notification(final User user, final Event event,
            final String messageTarget) {
        userTarget = user;
        eventTarget = event;
        message = messageTarget;
    }

    /**
     * Gets the user target.
     *
     * @return the user target
     */
    public final User getUserTarget() {
        return userTarget;
    }

    /**
     * Sets the user target.
     *
     * @param user
     *            the new user target
     */
    public final void setUserTarget(final User user) {
        userTarget = user;
    }

    /**
     * Gets the event target.
     *
     * @return the event target
     */
    public final Event getEventTarget() {
        return eventTarget;
    }

    /**
     * Sets the event target.
     *
     * @param event
     *            the new event target
     */
    public final void setEventTarget(final Event event) {
        eventTarget = event;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param newMessage
     *            the new message
     */
    public final void setMessage(final String newMessage) {
        this.message = newMessage;
    }
}
